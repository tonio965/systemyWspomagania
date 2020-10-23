package models;

import java.util.ArrayList;
import java.util.List;

public class DiscretizedColumn {  //this class has a purpose to hold discretized data
	
	private int columnId;
	private double sectorSize;
	private double sectorMin;
	private double sectorMax;
	private int sectorElements;
	private List<Double> elementsInSector;
	private List<Integer> indexInRawDataset;
	private List<Double> normalizedValues;
	private double normalizedValue;
	
	public DiscretizedColumn() {
		super();
		elementsInSector = new ArrayList<>();
		indexInRawDataset = new ArrayList<>();
		normalizedValues = new ArrayList<>();
	}
	
	public int getColumnId() {
		return columnId;
	}
	
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	
	public double getSectorSize() {
		return sectorSize;
	}
	
	public void setSectorSize(double sectorSize) {
		this.sectorSize = sectorSize;
	}
	
	public double getSectorMin() {
		return sectorMin;
	}
	
	public void setSectorMin(double sectorMin) {
		this.sectorMin = sectorMin;
	}
	
	public double getSectorMax() {
		return sectorMax;
	}
	
	public void setSectorMax(double sectorMax) {
		this.sectorMax = sectorMax;
	}
	
	public int getSectorElements() {
		return sectorElements;
	}
	
	public void setSectorElements(int sectorElements) {
		this.sectorElements = sectorElements;
	}

	public List<Double> getElementsInSector() {
		return elementsInSector;
	}

	public void setElementsInSector(List<Double> elementsInSector) {
		this.elementsInSector = elementsInSector;
	}
	
	public void addElementToSector(Double data) {
		this.elementsInSector.add(data);
	}

	public List<Integer> getIndexInRawDataset() {
		return indexInRawDataset;
	}

	public void setIndexInRawDataset(List<Integer> indexInRawDataset) {
		this.indexInRawDataset = indexInRawDataset;
	}
	
	public void addToIndexInRawDataset(Integer i) {
		this.indexInRawDataset.add(i);
	}

	public List<Double> getNormalizedValues() {
		return normalizedValues;
	}

	public void setNormalizedValues(List<Double> normalizedValues) {
		this.normalizedValues = normalizedValues;
	}
	
	public void addNormalizedValue(double normalized) {
		normalizedValues.add(normalized);
	}

	@Override
	public String toString() {
		return "DiscretizedColumn [sectorMin=" + sectorMin + ", sectorMax=" + sectorMax + ", sectorElements="
				+ sectorElements + ", elementsInSector=" + elementsInSector + ", indexInRawDataset=" + indexInRawDataset
				+ ", normalizedValues=" + normalizedValues + ", normalizedValue=" + normalizedValue + "]";
	}
	
	
	
	
	
	
	
	
	

}
