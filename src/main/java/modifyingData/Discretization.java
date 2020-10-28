package modifyingData;

import java.util.ArrayList;
import java.util.List;

import models.Data;
import models.DiscretizedColumn;

public class Discretization {
	
	List<Data> data;
	boolean hasHeader;

	public Discretization(List<Data> readFromFile, boolean hasHeader) {
		data = readFromFile;
		this.hasHeader=hasHeader;
	}
	
	
	public List<DiscretizedColumn> discreteColumn(int columnId, int amountOfSectors, double minimum, double maximum) {
		String toCheck = data.get(1).getData()[columnId]; // 1 is given to avoid trying to parse header
		boolean isDouble = isDouble(toCheck);
		double min=minimum;
		double max=maximum;
		double sectorSize;
		
		if(isDouble) { //if can be casted to double
			Double [] oneColumn = getOneColumn(columnId);
			if(min ==0 && max == 0) {
				double [] minAndMax = getMinMax(oneColumn);
				min = minAndMax[0];
				max = minAndMax[1];
			}
			sectorSize=(max-min)/amountOfSectors;
			
			List<DiscretizedColumn> discretizedColumns = new ArrayList<>();
			double tinyFraction = 0.000001d;
			double currentSectorMin=min;
			double currentSectorMax=min+sectorSize;
			
			for(int i=0;i<amountOfSectors; i++) { //i'm creating sector max and mins to iterate only once through a big set of data
				//each DiscretizedColumn will have a min and max which i will be checking in the Data set iterating loop - it will be quicker unless the amount of sectors will be bigger than amount of data
				DiscretizedColumn dc = new DiscretizedColumn();
				dc.setSectorMin(currentSectorMin);
				dc.setSectorMax(currentSectorMax);
				dc.setSectorSize(dc.getSectorMax()-dc.getSectorMin());
				dc.setColumnId(i);
				
				currentSectorMin=currentSectorMax+tinyFraction;
				currentSectorMax=currentSectorMin+sectorSize;
				discretizedColumns.add(dc);
				
			}
			//dotad dziala wszystko dalej cos debuger wypierdala ///
			///////////////////////////////////////////////////////
			int counter=0;
			for(Double value : oneColumn) { //here im looping inside the data set and i will be adding data to separate DiscretizedColumn set regarding the value in the column
				
					for(DiscretizedColumn dc : discretizedColumns) {	
						if(value >= dc.getSectorMin() && value<= dc.getSectorMax()) {
							dc.addToIndexInRawDataset(counter);
							dc.addElementToSector(value);
							//if between max and min add the value and index
						}
					}
						
				
				
				counter++;
			}
			
			return discretizedColumns; //change that
		}
		else {
			return null;
		}
	}
	
	private Double[] getOneColumn(int columnId) {
		List<Double> oneColumn = new ArrayList<>();
		int iterator = 0;
		for(Data d : data) {
			oneColumn.add(Double.parseDouble(d.getData()[columnId]));
		}
		Double [] myArray = new Double[oneColumn.size()];
		return oneColumn.toArray(myArray);
	}

	private double[] getMinMax(Double [] column) {
		double [] minAndMax = new double [2];
		double max = Integer.MIN_VALUE;
		double min = Integer.MAX_VALUE;
		for(int i=0 ; i< column.length; i++) {
			if(column[i]> max)
				max = column[i];
			if(column[i]<min)
				min=column[i];
		}
		minAndMax[0]=min;
		minAndMax[1]=max;
		
		return minAndMax;
	}

	public boolean isDouble( String input ) {
	    try {
	        Double.parseDouble( input );
	        return true;
	    }
	    catch( NumberFormatException e ) {
	    	System.err.println("column can't be casted to decimal number");
	        return false;
	    }
	}

}
