package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import HelperClasses.PointComparator;
import interfaces.DataSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.Cut;
import models.DataColumn;
import models.DimensionCut;
import models.Point;

public class FXMLCutsController implements Initializable{

	private DataSender dataSender;
	List <DataColumn> listOfCols;
	ObservableList<String> options;
	List<Point> points;
	List<Cut> cuts;
	
	List<Cut> performedCuts;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		options = FXCollections.observableArrayList();
		points = new ArrayList<>();
		cuts = new ArrayList<>();
		performedCuts = new ArrayList<>();
		
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
    	performCut();
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
		
		for(int i=0;i<points.get(0).getCoordinates().size(); i++) {
			
			System.out.println("performing cut for "+i+" dimension");
			dimensionCuts.add(performDimensionCut(i));
			
			
		}
		
		cutOutElements(dimensionCuts);

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
		
		
		//check which cut was the biggest
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
				for(int i = 0 ; i< dimensionCuts.get(index).getBigCut().getCutPoints().size() ; i++) {
					
					performedCuts.add(dimensionCuts.get(index).getBigCut());
					int id =dimensionCuts.get(index).getBigCut().getCutPoints().get(i).getId();
					for(Point p : points) {
						if(p.getId()==id) {
							p.setCutOut(true);
							System.out.println("cut out point: "+p.toString());
						}
							
					}
				}
			}
			
			
			if(whichCut==-1) {
				for(int i = 0 ; i< dimensionCuts.get(index).getSmallCut().getCutPoints().size() ; i++) {
					performedCuts.add(dimensionCuts.get(index).getSmallCut());
					int id =dimensionCuts.get(index).getSmallCut().getCutPoints().get(i).getId();
					for(Point p : points) {
						if(p.getId()==id) {
							p.setCutOut(true);
							System.out.println("cut out point: "+p.toString());
						}
							
					}
				}
			}
			
			
			
		}
		
		System.out.println("cutting fin");
		System.out.println("============");
		
	}

	private DimensionCut performDimensionCut(int dimension) {
		
		points.sort(new PointComparator(dimension));
		
		int smallestFirstClass = smallestAvailablePoint().getDecision();
		int biggestFirstClass = biggestAvailablePoint().getDecision();
		
		
		//from smallest looking for the points to cut
		Cut smallCut = new Cut();
		smallCut.setDimension(dimension);
		
		Cut bigCut = new Cut();
		bigCut.setDimension(dimension);
		
		
		for(int i=0; i<points.size(); i++) {
			
			Point currentPoint = points.get(i);
			
			if(!points.get(i).isCutOut()) {
				
				//if the same colour as the first one - add it to potential cut
				if(smallestFirstClass==currentPoint.getDecision()) {
					System.out.println("added point to small cut: "+ currentPoint.toString());
					smallCut.addPoint(currentPoint);
				}
				
				//if met another color HOL UP
				else {
					smallCut.setPosition(((currentPoint.getCoordinates().get(dimension))+
							(smallestAvailablePoint().getCoordinates().get(dimension))) /2);
					System.out.println("met another color for small cut == cut line : "+smallCut.getPosition());
					break;
				}
			}

			else {
				continue;
			}
		}
		
		//from biggest looking for the points to cut
		
		for(int i=points.size()-1; i>=0; i--) {
			
			Point currentPoint = points.get(i);
			
			if(!points.get(i).isCutOut()) {
				
				//if the same colour as the first one - add it to potential cut, ja tez chcialbym cut myself
				if(biggestFirstClass==currentPoint.getDecision()) {
					bigCut.addPoint(currentPoint);
					System.out.println("added point to big cut: "+ currentPoint.toString());
				}
				
				//if met another color HOL UP
				else {
					bigCut.setPosition(((currentPoint.getCoordinates().get(dimension))+
							(biggestAvailablePoint().getCoordinates().get(dimension))) /2);
					System.out.println("met another color for big cut == cut line : "+bigCut.getPosition());
					break;
				}
			}

			else {
				continue;
			}
		}
		
		DimensionCut dc = new DimensionCut(smallCut,bigCut);
		
		return dc;
		
		
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
