package models;

import java.util.ArrayList;
import java.util.List;

public class DataColumnRow {
	
	private String decision;
	private List<String> data;
	
	public DataColumnRow() {
		super();
		data = new ArrayList<>();
	}

	public DataColumnRow(String decision, List<String> data) {
		super();
		this.decision = decision;
		this.data = data;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
	
	public void addData(String s) {
		data.add(s);
	}


	
	
	

}
