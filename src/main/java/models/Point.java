package models;

import java.util.List;

public class Point {
	
	int id;
	List<Double> coordinates;
	int decision;

	public Point(List<Double> coordinates, int id,int decision) {
		super();
		this.id=id;
		this.decision=decision;
		this.coordinates = coordinates;
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
	
	
	
	
	
	

}
