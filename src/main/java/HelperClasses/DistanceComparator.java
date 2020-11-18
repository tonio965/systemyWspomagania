package HelperClasses;

import java.util.Comparator;

import models.Distance;

public class DistanceComparator implements Comparator<Distance>{

	@Override
	public int compare(Distance o1, Distance o2) {
		
		if(o1.getDistance() > o2.getDistance()) {
			return 1;
		}
		
		else if(o1.getDistance() < o2.getDistance()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
