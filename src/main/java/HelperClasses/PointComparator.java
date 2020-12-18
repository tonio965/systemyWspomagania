package HelperClasses;

import java.util.Comparator;

import models.Point;

public class PointComparator implements Comparator<Point>{
	
	int dimension;
	
	

	public PointComparator(int dimension) {
		super();
		this.dimension = dimension;
	}



	@Override
	public int compare(Point o1, Point o2) {
		
		if(o1.getCoordinates().get(dimension) > o2.getCoordinates().get(dimension)) {
			return 1;
		}
		
		else if(o1.getCoordinates().get(dimension) < o2.getCoordinates().get(dimension)) {
			return -1;
		}
		else {
			return 0;
		}
	}

}