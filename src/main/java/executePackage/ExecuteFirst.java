package executePackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import reading.ReadFromFileWithHeaders;
import models.Data;
import models.DiscretizedColumn;
import modifyingData.Discretization;
import modifyingData.StandardDeviation;

public class ExecuteFirst {
	
	List<Data> rawDataset;
	public ExecuteFirst() {
		rawDataset=new ArrayList<>();
	}


	

	public List<Data> getRawDataset() {
		return rawDataset;
	}




	public void setRawDataset(List<Data> rawDataset) {
		this.rawDataset = rawDataset;
	}




	public ExecuteFirst(String path, boolean headers, boolean minMaxCheckbox, Double minValue, Double maxValue,
			boolean showPercentOfResults, boolean topOrBottomPercentOfResults, int percentValue,
			int columnId, int sectorAmount) {
		

			

	}
	
	public List<DiscretizedColumn>  returnDiscretizedAndNormalizedData(String path, boolean headers, boolean minMaxCheckbox, Double minValue, Double maxValue,
			boolean showPercentOfResults, boolean topOrBottomPercentOfResults, int percentValue,
			int columnId, int sectorAmount) {
		List<Data> readFromFile;
		double min = 0;
		double max = 0;
		if(!minMaxCheckbox) { //if user doesnt want to keep original max and min values - change to provided
			min = minValue;
			max = maxValue;
		}
			
		ReadFromFileWithHeaders fwh = new ReadFromFileWithHeaders(headers);
		readFromFile=fwh.returnFromFile(path);
		rawDataset=readFromFile;
		Discretization d= new Discretization(readFromFile, headers);
		List<DiscretizedColumn> discretized =d.discreteColumn(columnId, sectorAmount, min, max); //retruns a set of records to further consideration eg counting standard deviation
		StandardDeviation sd1 = new StandardDeviation(discretized); //second parameter is a column to discretize - the same value as 1st parameter in discretized
		List<DiscretizedColumn> normalized = sd1.addNormalizedValues(discretized);
		addNormalizedDatatoRawDataset(normalized);
		if(showPercentOfResults) { //original checkbox is selected
			printResults(filterResults(true, 100, normalized));
		}
		else { // not original value not checked
			List<Double> filteredResults;
			List<DiscretizedColumn> filteredNormalized;
			if(topOrBottomPercentOfResults) { //top n% of results
				filteredResults = filterResults(true, percentValue, normalized);
				printResults(filteredResults);
			}
			else { //bottom n% of results
				filteredResults = filterResults(false, percentValue, normalized);
				printResults(filteredResults);
			}
		}
		
		return normalized;
		
	}

	private void addNormalizedDatatoRawDataset(List<DiscretizedColumn> normalized) {
		for(DiscretizedColumn dc : normalized) {
			for(int i=0 ;i<dc.getIndexInRawDataset().size(); i++) {
				rawDataset.get(dc.getIndexInRawDataset().get(i)).setNormalizedDataValue(dc.getNormalizedValues().get(i));
				rawDataset.get(dc.getIndexInRawDataset().get(i)).setSectorId(dc.getColumnId());
			}
		}
		System.out.println("");
	}



	private void printResults(List<Double> normalized) {
		for(Double dc : normalized) {
			System.out.println(dc.toString());
		}
		
	}

	private List<Double> filterResults(boolean b, int percent, List<DiscretizedColumn> normalized) {
		List<Double> allValues = new ArrayList<>();
		for(DiscretizedColumn dc: normalized) {
			for(Double value : dc.getNormalizedValues()) {
				allValues.add(value);
			}
		}
		if(b ==true) // we need N% of the smallest results
			Collections.reverse(allValues);
		else {
			Collections.sort(allValues);
		}
		//after sorting we just need to pick n% of first results (size*0,n%)
		double howManyResultsToReturn=allValues.size()*(0.01*percent);
		List<Double> toReturn = new ArrayList<>();
		for(int i=0; i<howManyResultsToReturn; i++) {
			toReturn.add(allValues.get(i));
		}
		
		return toReturn;
	}

}
