package models;

import java.util.ArrayList;
import java.util.List;

public class Cut {
	
	int dimension;
	double position;
	List<Point> cutPoints;
	
	
	public Cut() {
		this.cutPoints = new ArrayList<>();
	}


	public Cut(int dimension, double position, List<Point> cutPoints) {
		super();
		this.dimension = dimension;
		this.position = position;
		this.cutPoints = cutPoints;
	}


	public int getDimension() {
		return dimension;
	}


	public void setDimension(int dimension) {
		this.dimension = dimension;
	}


	public double getPosition() {
		return position;
	}


	public void setPosition(double position) {
		this.position = position;
	}


	public List<Point> getCutPoints() {
		return cutPoints;
	}


	public void setCutPoints(List<Point> cutPoints) {
		this.cutPoints = cutPoints;
	}
	
	
	public void addPoint(Point p ) {
		cutPoints.add(p);
	}
	
	
	
	

}
