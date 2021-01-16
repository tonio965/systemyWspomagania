package systemyWspomagania;

import java.util.*;

import interfaces.DataSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.DataColumn;
import models.DataColumnRow;
import models.DataRowWithCentroidID;

public class FXMLKmeansController {

	private DataSender dataSender;
	List <DataColumn> listOfCols;
	List <DataRowWithCentroidID> rows;
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
		centroidIndex = findRandomIndexes(amountOfDecisionClasses, listOfCols.get(0).getContents().size());
		
		assignDistanceFromCentroids();
		
		System.out.println("twoj stary");
		
	}
	
	
	
	private void assignDistanceFromCentroids() {
		
		for(int i=0; i<rows.size(); i++) {
			
			boolean isNotCentroid = true;
			
			for(int j=0 ; j<=amountOfDecisionClasses; j++) {
				if(i==centroidIndex[j]) {
					isNotCentroid =false;
				}
			}
			
			if(isNotCentroid) {
				int minDistance = -1;
				DataRowWithCentroidID currentRow = rows.get(i);
				double [] distances = new double[amountOfDecisionClasses+1];
				for(int j=0; j<=amountOfDecisionClasses; j++) {
					DataRowWithCentroidID currentCentroid = rows.get(centroidIndex[j]);
					distances[j] = euklideanDistance(currentRow, currentCentroid);
				}
				int minimalIndex = findMinimalDistanceIndex(distances);
				rows.get(i).setDistanceFromCentroid(distances[minimalIndex]);
				rows.get(i).setCentroidId(centroidIndex[minimalIndex]);
			}
			if(!isNotCentroid) {
				rows.get(i).setCentroidId(i);
				rows.get(i).setDistanceFromCentroid(0);
			}
			
		}
		
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
		return distance;
	}
	
    //receive list from main screen
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
    	rows = new ArrayList<>();
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
