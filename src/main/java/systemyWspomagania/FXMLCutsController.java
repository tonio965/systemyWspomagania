package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import HelperClasses.PointComparator;
import interfaces.DataSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import models.Cut;
import models.CutPoint;
import models.DataColumn;
import models.DimensionCut;
import models.Point;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;

public class FXMLCutsController implements Initializable{

	private DataSender dataSender;
	List <DataColumn> listOfCols;
	ObservableList<String> options;
	List<Point> points;
	List<Cut> cuts;
	List<CutPoint> cutPoints;
	List<Cut> performedCuts;
	
	List<Point> pointToCompareList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		options = FXCollections.observableArrayList();
		points = new ArrayList<>();
		cuts = new ArrayList<>();
		performedCuts = new ArrayList<>();
		cutPoints = new ArrayList<>();
		pointToCompareList=new ArrayList<>();
		
	}
	

    @FXML
    private Button cutButton;

    @FXML
    void cutButtonClick(ActionEvent event) {
    	
    	performCut();

    }
	
	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}
	
    //receive list from main screen
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
    	System.out.println("list size "+listOfCols.size());
    	System.out.println("list elems: "+ listOfCols.get(0).getContents().size());
    	createPoints();
    	drawSomething();
    }

	private void createPoints() {
		
		//create points with id in basic entry data
		for(int i=0; i<listOfCols.get(0).getContents().size(); i++) {
			int column =0;
			List<Double> coords = new ArrayList<>();
			do {
				double d =Double.parseDouble(listOfCols.get(column).getContents().get(i));
				coords.add(d);
				column++;
			}
			while(column<listOfCols.size()-1);
			int decision = Integer.valueOf(listOfCols.get(listOfCols.size()-1).getContents().get(i));
			points.add(new Point(coords, i,decision));
			pointToCompareList.add(new Point(coords, i,decision));
		}
		
		//sort points using X axis
		
		System.out.println("breakpoint");
		
		
		
		
		
		
	}

	private void performCut() {
		
		/**
		 * dla kazdego wymiaru poza klasa decyzyjna wiec size()-1
		 */
		
		// chce zrobic tyle ciec ile jest wymiarow czyli tyle ile wspolrzednych ma punkt
		
		
		List<DimensionCut> dimensionCuts = new ArrayList<>();
		boolean isNull = false;
		//leci tyle ile jest wymiarow np x-y = 2 razy
		for(int i=0;i<points.get(0).getCoordinates().size(); i++) {
			
			DimensionCut dc =performDimensionCut(i);
			if(dc!=null)
				dimensionCuts.add(dc);
			else {
				isNull=true;
				break;
			}
		}
		
		if(!isNull)
			cutOutElements(dimensionCuts);
		else {
			summaryFunction();
		}

	}
	
	
	private void cutOutElements(List<DimensionCut> dimensionCuts) {
		
		System.out.println("=============");
		System.out.println("performing cut");
		/**
		 * 
		 * whichCut:
		 *  	-1 == smallcut
		 *  	1 == bigcut
		 *  	0 == no cuts performed
		 * 
		 */
		int max=0;
		int whichCut=0;
		int index=0;
		
		
		//check which cut was the biggest ammount of cut out points
		for(int i=0; i< dimensionCuts.size(); i++) {
			
			if(dimensionCuts.get(i).getSmallCut().getCutPoints().size()>max) {
				whichCut=-1;
				max=dimensionCuts.get(i).getSmallCut().getCutPoints().size();
				index=i;
			}
			if(dimensionCuts.get(i).getBigCut().getCutPoints().size()>max) {
				whichCut=1;
				max=dimensionCuts.get(i).getBigCut().getCutPoints().size();
				index=i;
			}
		}
		
		//time to cut this shit prosze zabijcie mnie
		
		
		if(whichCut!=0) {
			
			if(whichCut==1) {
				performedCuts.add(dimensionCuts.get(index).getBigCut());
				for(int i = 0 ; i< dimensionCuts.get(index).getBigCut().getCutPoints().size() ; i++) {
					
					
					int id =dimensionCuts.get(index).getBigCut().getCutPoints().get(i).getId();
					for(Point p : points) {
						if(p.getId()==id) {
							p.setCutOut(true);
							p.setDecision(1000);
//							System.out.println("cut out point: "+p.toString());
						}
							
					}
				}
				System.out.println("cut did: "+dimensionCuts.get(index).getBigCut().getPosition());
			}
			
			
			if(whichCut==-1) {
				performedCuts.add(dimensionCuts.get(index).getSmallCut());
				for(int i = 0 ; i< dimensionCuts.get(index).getSmallCut().getCutPoints().size() ; i++) {
					int id =dimensionCuts.get(index).getSmallCut().getCutPoints().get(i).getId();
					for(Point p : points) {
						if(p.getId()==id) {
							p.setCutOut(true);
							p.setDecision(1000);
//							System.out.println("cut out point: "+p.toString());
						}
							
					}
				}
				System.out.println("cut did: "+dimensionCuts.get(index).getBigCut().getPosition());
			}
			
			
		}
		
		System.out.println("cutting fin");
		System.out.println("============");
		
		drawSomething();
		
	}

	private void drawSomething() {
    	String columnTitle = "tit1";
    	String column2Title = "tit2";
    	
    	Double [] first = new Double [points.size()];
    	Double [] second = new Double [points.size()];
    	Integer [] classCol = new Integer [points.size()];
    	
    	
    	for(int i=0; i<points.size();i++) {
    		
    		first[i]=points.get(i).getCoordinates().get(0); 
    		second[i]=points.get(i).getCoordinates().get(1); 
    		classCol[i]=points.get(i).getDecision();
    	}
    	
    	
    	Table myData =
    		    Table.create("Cute data")
    		        .addColumns(
    		            DoubleColumn.create(columnTitle, first),
    		            DoubleColumn.create(column2Title, second),
    					IntColumn.create("class", classCol));

    	Plot.show(
    			ScatterPlot.create("2D Plot", 
    		                       myData, columnTitle, column2Title, "class")); //2dplot
		
	}

	private DimensionCut performDimensionCut(int dimension) {
		
		boolean metAnotherColor=false;
		
		points.sort(new PointComparator(dimension));
		
		
	
		
		
		//from smallest looking for the points to cut
		Cut smallCut = new Cut();
		//ide od najmniejszej - odcinam wszystkie < X
		smallCut.setDirection(true);
		smallCut.setDimension(dimension);
		
		Cut bigCut = new Cut();
		
		//ide od najwiekszej - odcinam wszystkie > X
		bigCut.setDirection(false);
		bigCut.setDimension(dimension);
		
		//set first point
		int smallestFirstClass = smallestAvailablePoint().getDecision();
		Point previousPoint=smallestAvailablePoint();
		
		smallCut.addPoint(previousPoint);
		List<Integer> pointsToDelete = new ArrayList<>();
		for(int i=1; i<points.size(); i++) {
			
			Point currentPoint = points.get(i);
			
			if(!points.get(i).isCutOut()) {
				
				//if the same colour as the first one - add it to potential cut
				if(smallestFirstClass==currentPoint.getDecision()) {
//					System.out.println("added point to small cut: "+ currentPoint.toString());
					smallCut.addPoint(currentPoint);
					previousPoint=currentPoint;
				}
				
				//if met another color HOL UP
				else {
					//check if they are not in the exact same spot in the current axis
					metAnotherColor=true;
					if(currentPoint.getCoordinates().get(dimension)==(previousPoint.getCoordinates().get(dimension))) {
						//if they are in the same spot it means that we have a problem
						//solution - delete a point which is another color than previous
						pointsToDelete.add(currentPoint.getId());
						continue;
					}
					
					smallCut.setPosition(((currentPoint.getCoordinates().get(dimension))+
							(previousPoint.getCoordinates().get(dimension))) /2);
					System.out.println("=====");
					System.out.println("amount of points to cut: "+smallCut.getCutPoints().size());
					System.out.println("current: "+currentPoint.getCoordinates().get(dimension).toString());
					System.out.println("smallest: "+previousPoint.getCoordinates().get(dimension));
					System.out.println("met another color for small cut == cut line : "+smallCut.getPosition());
					break;
				}
			}

			else {
				continue;
			}
		}
		removeDoubledPoints(pointsToDelete);
		pointsToDelete= new ArrayList<>();
		//set to null not to create another var
		previousPoint=null;
		//from biggest looking for the points to cut
		int biggestFirstClass = biggestAvailablePoint().getDecision();
		previousPoint=biggestAvailablePoint();
		bigCut.addPoint(previousPoint);
		for(int i=points.size()-2; i>=0; i--) {
			
			Point currentPoint = points.get(i);
			
			if(!points.get(i).isCutOut()) {
				
				//if the same colour as the first one - add it to potential cut, ja tez chcialbym cut myself
				if(biggestFirstClass==currentPoint.getDecision()) {
					bigCut.addPoint(currentPoint);
					previousPoint=currentPoint;
//					System.out.println("added point to big cut: "+ currentPoint.toString());
				}
				
				//if met another color HOL UP
				else {
					metAnotherColor=true;
					if(currentPoint.getCoordinates().get(dimension)==(previousPoint.getCoordinates().get(dimension))) {
						//if they are in the same spot it means that we have a problem
						//solution - delete a point which is another color than previous
						pointsToDelete.add(currentPoint.getId());
						continue;
					}
					bigCut.setPosition(((currentPoint.getCoordinates().get(dimension))+
							(previousPoint.getCoordinates().get(dimension))) /2);
					System.out.println("=====");
					System.out.println("amount of points to cut: "+bigCut.getCutPoints().size());
					System.out.println("current: "+currentPoint.getCoordinates().get(dimension).toString());
					System.out.println("biggest: "+previousPoint.getCoordinates().get(dimension));
					System.out.println("met another color for big cut == cut line : "+bigCut.getPosition());
					break;
				}
			}

			else {
				continue;
			}
		}
		
		if(metAnotherColor) {
			//there is another color
			DimensionCut dc = new DimensionCut(smallCut,bigCut);
			return dc;
		}
		
		else {
			return null;
		}
		
		

		
		
	}
	
	private void removeDoubledPoints(List<Integer> pointsToDelete) {
		List<Point> pointsToRemove = new ArrayList<>();
		for(Integer id: pointsToDelete) {
			
			for(int i=0; i<points.size(); i++) {
				if(points.get(i).getId()==id) {
					pointsToRemove.add(points.get(i));
				}
			}
		}
		for(int i=0; i<pointsToRemove.size(); i++) {
			points.remove(pointsToRemove.get(i));
		}
		
	}

	private void summaryFunction() {
		System.out.println("twoj stary");
		//create cuts array 
		
		/**
		 * 
		 * 		|cutID | cutDimen | <=2 or sth
		 * 		
		 * 		w sumie moja lista juz jest taka lista wiec wyprintowac ladnie oponenta
		 * 
		 * 
		 */
		
		System.out.println("==============cuts==============");
		for(int i=0; i<performedCuts.size(); i++) {
			StringBuilder sb = new StringBuilder();
			
			String direction = new String("");
			if(performedCuts.get(i).isDirection())
				direction="<=";
			if(!performedCuts.get(i).isDirection()) {
				direction=">=";
			}
			
			sb.append("= id:").append(i).append(" axis: ").append(performedCuts.get(i).getDimension()).append(" condition:")
				.append(direction).append(performedCuts.get(i).getPosition());
			System.out.println(sb.toString());
			System.out.println("----------------------------");
		}
		System.out.println("================================ \n \n");
		
		
		System.out.println("===========cuts vector==========");
		
		
		//create cut vector for every point
		
		for(int i=0; i<pointToCompareList.size(); i++) {
			
			Point p = pointToCompareList.get(i);
			CutPoint cp = new CutPoint(p);
			
			for(int j=0; j<performedCuts.size(); j++) {
				
				//get this cut's cutting dimension
				
				int decision = checkIfThisPointMeetsCuttingCondition(cp, performedCuts.get(j));
				cp.addConditon(decision);

				
				//check if in that dimension current cut meets the requirement if so 1 otherwise 0
				
			}
			cutPoints.add(cp);
			
		}
		
		
		for(int x=0; x<cutPoints.size();x++) {
			System.out.println(cutPoints.get(x).toString());
		}
		
		System.out.println("==============================/n");
		
		checkNewPoint();
		
		
		
		
	}

	private void checkNewPoint() {
		System.out.println("=========checkNewPoint=========");
		Scanner sc = new Scanner(System.in);
		String userIn = new String();
		System.out.println("provide data separated by semicolon eg. -1;5 \n");
		userIn=sc.nextLine();
		String[] input = userIn.split(";");
		double [] valuesArr = new double [input.length];
		List<Double> coords = new ArrayList<>();
		for(int i =0; i<input.length; i++) {
			coords.add(Double.valueOf(input[i]));
		}
		
		Point p = new Point(coords,1000000,1000);
		
		CutPoint cp = new CutPoint(p);
		
		for(int j=0; j<performedCuts.size(); j++) {
			
			//get this cut's cutting dimension
			
			int decision = checkIfThisPointMeetsCuttingCondition(cp, performedCuts.get(j));
			cp.addConditon(decision);

			
			//check if in that dimension current cut meets the requirement if so 1 otherwise 0
			
		}
		
		System.out.println("result" + cp.toString()+"\n");
		System.out.println("===============================");
		
		checkNewPoint();
		
		
		
		
	}

	private int checkIfThisPointMeetsCuttingCondition(CutPoint cp, Cut cut) {
		int returnValue=-1;
		int currentDimension=cut.getDimension();
		double currentPosition = cut.getPosition();
		boolean direction = cut.isDirection();
		
		//for example im looking for x value
		double pointValue = cp.getPoint().getCoordinates().get(currentDimension);
		
		//now i need to check if this point meets condition
		
		if(cut.isDirection()) {
			
			if(pointValue<=currentPosition)
				returnValue=1;
			else {
				returnValue=0;
			}
			
		}
		if(!cut.isDirection()) {
			
			if(pointValue>currentPosition)
				returnValue=1;
			else {
				returnValue=0;
			}
		}
		
		return returnValue;
		
	}

	private Point smallestAvailablePoint() {
		for(int i=0; i< points.size(); i++) {
			
			if(!points.get(i).isCutOut()) {
				System.out.println("smallest avail: "+ points.get(i).toString());
				return points.get(i);
			}
		
		}
		return null;
	}
	
	private Point biggestAvailablePoint() {
		for(int i=points.size()-1; i>=0; i--) {
			
			if(!points.get(i).isCutOut()) {
				System.out.println("biggest avail: "+ points.get(i).toString());
				return points.get(i);
			}
		
		}
		return null;
	}
	
}
