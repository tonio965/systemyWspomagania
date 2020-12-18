package models;

import java.util.List;

public class Point {
	
	int id;
	List<Double> coordinates;
	int decision;
	boolean isCutOut;

	public Point(List<Double> coordinates, int id,int decision) {
		super();
		this.id=id;
		this.decision=decision;
		this.coordinates = coordinates;
		this.isCutOut = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}

	public int getDecision() {
		return decision;
	}

	public void setDecision(int decision) {
		this.decision = decision;
	}

	public boolean isCutOut() {
		return isCutOut;
	}

	public void setCutOut(boolean isCutOut) {
		this.isCutOut = isCutOut;
	}

	@Override
	public String toString() {
		return "Point [id=" + id + ", coordinates=" + coordinates + ", decision=" + decision + ", isCutOut=" + isCutOut
				+ "]";
	}
	
	
	
	
	
	
	
	

}
