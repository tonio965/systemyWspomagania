package HelperClasses;

import java.util.Comparator;

import models.Data;
import models.ValueWrapperToSort;

public class ValueWrapperToSortComparator implements Comparator<ValueWrapperToSort>{

	@Override
	public int compare(ValueWrapperToSort arg0, ValueWrapperToSort arg1) {
		if(arg0.getValue() > arg1.getValue()) {
			return 1;
		}
		
		else if(arg0.getValue() < arg1.getValue()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
