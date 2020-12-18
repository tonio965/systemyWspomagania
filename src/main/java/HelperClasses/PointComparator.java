package HelperClasses;

import java.util.Comparator;

import models.Point;

public class PointComparator implements Comparator<Point>{

	@Override
	public int compare(Point o1, Point o2) {
		
		if(o1.getCoordinates().get(0) > o2.getCoordinates().get(0)) {
			return 1;
		}
		
		else if(o1.getCoordinates().get(0) < o2.getCoordinates().get(0)) {
			return -1;
		}
		else {
			return 0;
		}
	}

}