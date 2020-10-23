package modifyingData;

import java.util.ArrayList;
import java.util.List;

import models.DiscretizedColumn;

public class StandardDeviation {
	
	double standardDeviation;
	double mean;
	
	public StandardDeviation(List<DiscretizedColumn> discretized) {
		mean = calculateMean(discretized);
		standardDeviation=calculateDeviation(discretized);
		
	}

	private double calculateDeviation(List<DiscretizedColumn> discretized) {
		double sumOfSquares=0;
		double size=0;
		for(DiscretizedColumn d : discretized) { //from every sector 
			for(Double value : d.getElementsInSector()) { //get values of elements 
				double square = Math.pow((value-mean), 2);
				size++;
				sumOfSquares+=square;
			}
		}
		double result = Math.sqrt(sumOfSquares/size);
		return result;
	}

	private double calculateMean(List<DiscretizedColumn> discretized) {
		double sum=0;
		double size=0;
		for(DiscretizedColumn d : discretized) { //from every sector 
			for(Double value : d.getElementsInSector()) { //get values of elements 
				sum+=value;
				size++;
			}
		}
		return sum/size;
	}

	public List<DiscretizedColumn> addNormalizedValues(List<DiscretizedColumn> discretized) {
		for(DiscretizedColumn d : discretized) { //from every sector 
			for(Double value : d.getElementsInSector()) { //get values of elements 
				d.addNormalizedValue(calculateNormalizedValue(value));
			}
		}
		return discretized;
	}
	
	private double calculateNormalizedValue(double value) {
		double result = (value-this.mean)/this.standardDeviation; //as in cez2 pdf 
		return result;
	}

}
