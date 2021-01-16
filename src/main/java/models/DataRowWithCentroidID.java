package models;

import java.util.ArrayList;
import java.util.List;

public class DataRowWithCentroidID {
	
	private List<Double> data;
	private int centroidId;
	private double distanceFromCentroid;
	
	
	public double getDistanceFromCentroid() {
		return distanceFromCentroid;
	}
	public void setDistanceFromCentroid(double distanceFromCentroid) {
		this.distanceFromCentroid = distanceFromCentroid;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	public int getCentroidId() {
		return centroidId;
	}
	public void setCentroidId(int centroidId) {
		this.centroidId = centroidId;
	}
	public DataRowWithCentroidID(List<String> data) {
		
		this.data = new ArrayList<>();
		
		for(String input : data) {
			this.data.add(Double.parseDouble(input));
		}
		this.centroidId=-1;
		
		this.distanceFromCentroid=-1;
	}
	
	public DataRowWithCentroidID() {
		
		this.data = new ArrayList<>();
		this.centroidId=-1;
		this.distanceFromCentroid=-1;
	}
	
	public void addData(double d) {
		data.add(d);
	}
	
	
	
	

}
