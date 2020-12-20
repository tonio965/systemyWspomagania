package models;

import java.util.ArrayList;
import java.util.List;

public class CutPoint {
	
	List<Integer>conditionMet;
	Point point;
	
	
	public CutPoint(Point point) {
		super();
		this.conditionMet = new ArrayList<>();
		this.point = point;
	}


	public List<Integer> getConditionMet() {
		return conditionMet;
	}


	public void setConditionMet(List<Integer> conditionMet) {
		this.conditionMet = conditionMet;
	}


	public Point getPoint() {
		return point;
	}


	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void addConditon(int i) {
		conditionMet.add(i);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id: ").append(point.getId()).append("  ").append("cuts: ");
		
		for(int i=0;i<conditionMet.size();i++) {
			sb.append(conditionMet.get(i)).append(" ");
		}
		
		sb.append("  class: ").append(point.getDecision());
		
		return sb.toString();
	}
	
	
	

}
