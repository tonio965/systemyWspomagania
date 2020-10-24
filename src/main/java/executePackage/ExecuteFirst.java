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
	
	public ExecuteFirst() {
		Scanner userInput = new Scanner(System.in);
//		System.out.println("pass the filepath");
//		String input = userInput.nextLine();
		String input = "/Users/tomaszkoltun/eclipse-workspace/systemyWspomagania/src/main/resources/INCOME.txt";
		System.out.println("does it have headers? y/n");
		String decision = userInput.nextLine();
		double min = 0;
		double max =0;
		List<Data> readFromFile;
		switch(decision) {
			case "y":
				System.out.println("do you wanna keep the original min and max values? y/n");
				decision = userInput.nextLine();
				switch(decision) {
				
					case "y":
					break;
						
					case "n":
						System.out.println("pass min value: ");
						min=userInput.nextDouble();
						System.out.println("pass max value: ");
						max=userInput.nextDouble();
					break;
						
				}
				
				ReadFromFileWithHeaders fwh = new ReadFromFileWithHeaders(true);
				readFromFile=fwh.returnFromFile(input);
				Discretization d= new Discretization(readFromFile, true);
				List<DiscretizedColumn> discretized =d.discreteColumn(1, 3, min, max); //retruns a set of records to further consideration eg counting standard deviation
				StandardDeviation sd1 = new StandardDeviation(discretized); //second parameter is a column to discretize - the same value as 1st parameter in discretized
				List<DiscretizedColumn> normalized = sd1.addNormalizedValues(discretized);
				
				
				System.out.println("do you wanna see all the data or the % of the data? a/%");
				decision=userInput.nextLine();
				switch(decision) {
				case "a":
					printResults(filterResults(true, 100, normalized));
				break;
				
				case "%":
					System.out.println("% of the smallest or the biggest? s/b");
					decision = userInput.nextLine();
					int percent = 0;
					List<Double> filteredResults;
					switch(decision) {
						case "s":
							System.out.println("provide % of the smallest results");
							percent=userInput.nextInt();
							filteredResults = filterResults(false, percent, normalized);
							printResults(filteredResults);
						break;
						
						case "b":
							System.out.println("provide % of the smallest results");
							percent=userInput.nextInt();
							filteredResults = filterResults(true, percent, normalized);
							printResults(filteredResults);
							break;
					}
				
				}
				break;
				
			
			case "n":
				ReadFromFileWithHeaders fwh2 = new ReadFromFileWithHeaders(false);
				readFromFile=fwh2.returnFromFile(input);
				Discretization d2= new Discretization(readFromFile, false);
				List<DiscretizedColumn> dc2 =d2.discreteColumn(2, 3, min, max);
				break;
				
			default:
				System.out.println("it has to be n or y");
				break;
				
		}
	}



	public ExecuteFirst(String path, boolean headers, boolean minMaxCheckbox, Double minValue, Double maxValue,
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
		Discretization d= new Discretization(readFromFile, headers);
		List<DiscretizedColumn> discretized =d.discreteColumn(columnId, sectorAmount, min, max); //retruns a set of records to further consideration eg counting standard deviation
		StandardDeviation sd1 = new StandardDeviation(discretized); //second parameter is a column to discretize - the same value as 1st parameter in discretized
		List<DiscretizedColumn> normalized = sd1.addNormalizedValues(discretized);
		if(showPercentOfResults) { //original checkbox is selected
			printResults(filterResults(true, 100, normalized));
		}
		else { // not original value not checked
			List<Double> filteredResults;
			if(topOrBottomPercentOfResults) { //top n% of results
				filteredResults = filterResults(true, percentValue, normalized);
				printResults(filteredResults);
			}
			else { //bottom n% of results
				filteredResults = filterResults(false, percentValue, normalized);
				printResults(filteredResults);
			}
		}
			

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
