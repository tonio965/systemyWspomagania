package HelperClasses;

import java.util.Comparator;

import models.Data;

public class NormalizedDataComparator implements Comparator<Data>{

	@Override
	public int compare(Data arg0, Data arg1) {
		
		if(arg0.getNormalizedDataValue() > arg1.getNormalizedDataValue()) {
			return 1;
		}
		
		else if(arg0.getNormalizedDataValue() < arg1.getNormalizedDataValue()) {
			return -1;
		}
		else {
			return 0;
		}
		
	}

}
