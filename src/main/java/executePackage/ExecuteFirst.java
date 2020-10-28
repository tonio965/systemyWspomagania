package executePackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import HelperClasses.NormalizedDataComparator;
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
	
	public List<Data>  returnDiscretizedAndNormalizedData(String path, boolean headers, boolean minMaxCheckbox, Double minValue, Double maxValue,
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
		Discretization d= new Discretization(rawDataset, headers);
		List<DiscretizedColumn> discretized =d.discreteColumn(columnId, sectorAmount, min, max); //retruns a set of records to further consideration eg counting standard deviation
		StandardDeviation sd1 = new StandardDeviation(discretized); //second parameter is a column to discretize - the same value as 1st parameter in discretized
		List<DiscretizedColumn> normalized = sd1.addNormalizedValues(discretized);
		exportNormalizedDataToDataset(normalized); //adding normalized values to rawDataset
		if(showPercentOfResults) { //original checkbox is selected
			rawDataset=filterResults(true, 100, rawDataset);
			printResults(rawDataset);
		}
		else { // not original value not checked
			List<Data> filteredResults;
			List<DiscretizedColumn> filteredNormalized;
			if(topOrBottomPercentOfResults) { //top n% of results
				filteredResults = filterResults(true, percentValue, rawDataset);
				printResults(filteredResults);
			}
			else { //bottom n% of results
				filteredResults = filterResults(false, percentValue, rawDataset);
				printResults(filteredResults);
			}
		}
		
		return rawDataset;
		
	}

	private void exportNormalizedDataToDataset(List<DiscretizedColumn> input){ //add normalized vals to basic dataset
		//do zmiany bo sie wypierdala
		List<Data> newDataset = new ArrayList<>();
		
		for(DiscretizedColumn dc: input) {
			for(int i=0; i<dc.getElementsInSector().size();i++) {
				int indexInRawDataset=dc.getIndexInRawDataset().get(i);
				Data d = rawDataset.get(indexInRawDataset);
				double normalizedValue = dc.getNormalizedValues().get(i);
				d.setNormalizedDataValue(normalizedValue);
				d.setSectorId(dc.getColumnId());
				newDataset.add(d);
			}
		}
		rawDataset = newDataset;
//		for(DiscretizedColumn dc : input) {
//			for(int i=0;i<dc.getElementsInSector().size();i++) { //here to mark everything to dATA set
//				//get index from raw dataset and put data on this index in raw dataset
//				int indexInRawDataset=dc.getIndexInRawDataset().get(i); //put normalized value on this index
//				double normalizedValue = dc.getNormalizedValues().get(i); //put this data in rawdataset
//				rawDataset.get(indexInRawDataset).setNormalizedDataValue(normalizedValue);
//				rawDataset.get(indexInRawDataset).setSectorId(dc.getColumnId());
//			}
//		}
		
	}



	private void printResults(List<Data> normalized) {
		for(Data dc : normalized) {
			System.out.println(dc.getNormalizedDataValue());
		}
		
	}

	private List<Data> filterResults(boolean b, int percent, List<Data> raw) {
		List<Data> filteredList = new ArrayList<>();
		raw.sort(new NormalizedDataComparator());
		if(b ==true) { // we need N% of the smallest results
			Collections.reverse(raw);
		}

		else {
			
		}
		//after sorting we just need to pick n% of first results (size*0,n%)
		double howManyResultsToReturn=raw.size()*(0.01*percent);
		for(int i=0; i<howManyResultsToReturn; i++) {
			filteredList.add(raw.get(i));
		}
		
		return filteredList;
	}

}
