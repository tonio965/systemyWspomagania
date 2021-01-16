package systemyWspomagania;

import java.util.*;

import interfaces.DataSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.DataColumn;
import models.DataColumnRow;
import models.DataRowWithCentroidID;
import models.Dot;

public class FXMLKmeansController {

	private DataSender dataSender;
	List <DataColumn> listOfCols;
	List <DataRowWithCentroidID> rows;
	List<DataRowWithCentroidID> centroids;
	int amountOfDecisionClasses;
	int [] centroidIndex;
	
    @FXML
    private Button performKmeansButton;

    @FXML
    void performKmeansButtonClick(ActionEvent event) {
    	System.out.println("button click ");
    	performCounting();
    }
    
	private void performCounting() {
		
		amountOfDecisionClasses = findAmountOfClasses();
		createCentroids();
//		centroidIndex = findRandomIndexes(amountOfDecisionClasses, listOfCols.get(0).getContents().size());
		
		boolean isChanging = true;
		int counter = 0;
		
		while(isChanging) {
			System.out.println("relocate "+counter);
			assignDistanceFromCentroids();
			isChanging =relocateCentroid();
			counter++;
			
		}

		System.out.println("twoj stary");
		DataColumn dc = new DataColumn();
		dc.setTitle("kmeansVal");
		List<String> values = new ArrayList<>();
		for(int i=0;i<rows.size();i++) {
			values.add(String.valueOf(rows.get(i).getCentroidId()));
		}
		dc.setContents(values);
		listOfCols.add(dc);
		matchValuesToInput();
		
	}
	
	
	
	private void matchValuesToInput() {
		DataColumn classColumn= listOfCols.get(listOfCols.size()-2);
		DataColumn predictedColumn= listOfCols.get(listOfCols.size()-1);
//		List<Double> coverage = new ArrayList<>();
//		for(int i=0;i<amountOfDecisionClasses+1;i++) {
//			
//			int matches=0;
//			for(int j=0; j<rows.size();j++) {
//				if(classColumn.getContents().get(j).equals(predictedColumn.getContents().get(j)))
//					matches++;
//			}
//			int rowsSize=rows.size();
//			double cover=(double)matches/(double)rowsSize;
//			coverage.add(cover);
//			
//			List<String> newPreds = new ArrayList<>();
//			for(int j=0; j<rows.size();j++) {
//				int changedVal =(Integer.parseInt(predictedColumn.getContents().get(j))+1)%(amountOfDecisionClasses+1);
//				newPreds.add(String.valueOf(changedVal));
//			}
//			predictedColumn.setContents(newPreds);
//		}
//		for(Double d: coverage) {
//			System.out.println("ccoverage: "+d);
//		}
		List<Integer>indexes = new ArrayList<>();
		for(int k=0;k<amountOfDecisionClasses+1;k++) {
			
			int []occurences = new int[amountOfDecisionClasses+1];
			for(int j=0;j<rows.size();j++) {
				if(classColumn.getContents().get(j).equals(String.valueOf(k))) {
					occurences[Integer.parseInt(predictedColumn.getContents().get(j))]++;
				}
			}
			int max=0;
			int index=-1;
			for(int i=0;i<occurences.length;i++) {
				if(occurences[i]>max) {
					max=occurences[i];
					index=i;
				}
			}
			indexes.add(index);
		}
		System.out.println("test");
		
		int correctHits=0;
		for(int i=0;i<rows.size();i++) {
			
			for(int j=0;j<indexes.size();j++) {
				
				if(classColumn.getContents().get(i).equals(String.valueOf(j))) {
					if(predictedColumn.getContents().get(i).equals(String.valueOf(indexes.get(j)))) {
						correctHits++;
					}
				}
			}
		
		}
		List<String> newContents = new ArrayList<>();
		for(int i=0;i<rows.size();i++) {
			
			for(int j=0;j<indexes.size();j++) {
				
				if(classColumn.getContents().get(i).equals(String.valueOf(j))) {
					if(predictedColumn.getContents().get(i).equals(String.valueOf(indexes.get(j)))) {
						newContents.add(classColumn.getContents().get(i));
					}
					else {
						newContents.add(predictedColumn.getContents().get(i));
					}
				}
			}
		
		}
		System.out.println("correctHits: "+correctHits);
		System.out.println("correctHits percentage: "+((double)correctHits/(double)rows.size())*100);
		System.out.println("incorrectHits percentage: "+(100-(((double)correctHits/(double)rows.size())*100)));
		predictedColumn.setContents(newContents);
		for(int i=0;i<listOfCols.size();i++) {
			if(listOfCols.get(i).getTitle().equals("kmeansVal")) {
				listOfCols.get(i).setContents(newContents);
			}
		}
		dataSender.send(listOfCols);
	    Stage stage = (Stage) performKmeansButton.getScene().getWindow();
	    stage.close();
		
		
	}

	private void createCentroids() {
		List<Dot> minMaxValuesInEachDimension = new ArrayList<>();
		
		for(int j=0;j<listOfCols.size()-1;j++)  {
			
			double min=Double.MAX_VALUE;
			double max=Double.MIN_VALUE;
			
			for(int i=0;i<listOfCols.get(0).getContents().size();i++) {
				
				if(Double.parseDouble(listOfCols.get(j).getContents().get(i))<min) {
					min=Double.parseDouble(listOfCols.get(j).getContents().get(i));
				}
				if(Double.parseDouble(listOfCols.get(j).getContents().get(i))>max) {
					max=Double.parseDouble(listOfCols.get(j).getContents().get(i));
				}
			}
			minMaxValuesInEachDimension.add(new Dot(min,max));
			
		}
		
		//create n centroids;
		
		for(int i=0; i<amountOfDecisionClasses+1;i++) {
			
			DataRowWithCentroidID newCentroid = new DataRowWithCentroidID();
			for(int j=0;j<minMaxValuesInEachDimension.size();j++) {
				
				Random r = new Random();
				double randomValue = minMaxValuesInEachDimension.get(j).getMin()
						 + (minMaxValuesInEachDimension.get(j).getMax() - 
								 minMaxValuesInEachDimension.get(j).getMin())*r.nextDouble();
				newCentroid.addData(randomValue);
			}
			centroids.add(newCentroid);
			

		}
		
		
	}

	private boolean relocateCentroid() {
		boolean hasAnythingChanged=false;
		for(int k=0;k<centroids.size();k++) {
		
			List<Double> relocatedCentroidData = new ArrayList<>();
			for(int i=0; i<rows.get(0).getData().size(); i++) {
				
				double sum=0;
				int elementsInsideCentroid=0;
				for(int j=0;j<rows.size();j++) {
					DataRowWithCentroidID temp = rows.get(j);
					if(rows.get(j).getCentroidId()==k) {
						sum+=rows.get(j).getData().get(i);
						elementsInsideCentroid++;
					}
					
				}
				double newPosition = sum/elementsInsideCentroid;
				relocatedCentroidData.add(newPosition);
			}
			
			for(int x=0;x<centroids.get(k).getData().size();x++) {
				
				double change = Math.abs(centroids.get(k).getData().get(x)-relocatedCentroidData.get(x));
				if(change>0.01d) {
					hasAnythingChanged=true;
				}
			}
			centroids.get(k).setData(relocatedCentroidData);
		}
		
		return hasAnythingChanged;
		
	}

	private void assignDistanceFromCentroids() {
		int changes=0;
		for(int i=0; i<rows.size(); i++) {
			
			
				int minDistance = -1;
				DataRowWithCentroidID currentRow = rows.get(i);
				double [] distances = new double[amountOfDecisionClasses+1];
				for(int j=0; j<=amountOfDecisionClasses; j++) {
					DataRowWithCentroidID currentCentroid = centroids.get(j);
					distances[j] = euklideanDistance(currentRow, currentCentroid);
				}
				int minimalIndex = findMinimalDistanceIndex(distances);
				rows.get(i).setDistanceFromCentroid(distances[minimalIndex]);
				if(rows.get(i).getCentroidId()==-1 || rows.get(i).getCentroidId()!=minimalIndex)
					changes++;
				rows.get(i).setCentroidId(minimalIndex);

			
		}
		System.out.println("amountOfChanges: "+changes);
		
	}

	private int findMinimalDistanceIndex(double[] distances) {
		double minimum = Double.MAX_VALUE;
		int index = -1;
		for(int i=0;i<distances.length; i++) {
			if(distances[i]<minimum) {
				minimum = distances[i];
				index=i;
			}
		}
		return index;
	}

	private int[] findRandomIndexes(int amountOfDecisionClasses, int size) {
		int[] toReturn = new int [amountOfDecisionClasses+1];
	      Random rand = new Random(); //instance of random class
	      int upperbound = size+1;
	        //generate random values from 0-24
	      for(int i=0;i<=amountOfDecisionClasses;i++) {
	    	
	    	  int int_random = rand.nextInt(upperbound); 
	    	  toReturn[i] = int_random;
	    	  rows.get(int_random).setCentroidId(int_random);
	      }
	      return toReturn;
		
	}

	private int findAmountOfClasses() {
		int amountOfRows = listOfCols.get(0).getContents().size();
		int lastColumnIndex = listOfCols.size()-1;
		int max=0;
		
		//im looking for the max element - amount of decission calsses
		for(int i=0;i<amountOfRows;i++) {
			int currentValue = Integer.valueOf(listOfCols.get(lastColumnIndex).getContents().get(i));
			if(currentValue>max)
				max=currentValue;
		}
		
		
		
		
		return max;
	}

	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}
	
	private double euklideanDistance(DataRowWithCentroidID currentRow, DataRowWithCentroidID currentCentroid) {
		double distance =0;
		
		
		for(int x=0; x<currentRow.getData().size(); x++) {
			distance+= Math.pow((currentRow.getData().get(x)-currentCentroid.getData().get(x)), 2);
		}
		distance=Math.sqrt(distance);
//		System.out.println("distance");
		return distance;
	}
	
    //receive list from main screen
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
    	rows = new ArrayList<>();
    	centroids = new ArrayList<>();
    	System.out.println("list size "+listOfCols.size());
    	System.out.println("list elems: "+ listOfCols.get(0).getContents().size());
    	for(int i=0; i<listOfCols.get(0).getContents().size(); i++) {
    		
    		List<String> values = new ArrayList<>();
    		for(int j=0 ; j<listOfCols.size()-1; j++) {
    			values.add(listOfCols.get(j).getContents().get(i));
    		}
    		rows.add(new DataRowWithCentroidID(values));
    	}
    }
    
    

}
