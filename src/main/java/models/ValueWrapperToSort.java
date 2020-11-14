package models;

public class ValueWrapperToSort {
	
	int position;
	double value;
	
	public ValueWrapperToSort(int position, double value) {
		super();
		this.position = position;
		this.value = value;
	}

	public ValueWrapperToSort() {
		super();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public int compareTo(ValueWrapperToSort wps) {
		return position;
		
	}
	
	
	
	

}
