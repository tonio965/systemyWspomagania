package models;

public class Data {
	
	private String [] data;
	private int sectorId;
	private double normalizedDataValue;
	
	public Data(String [] data ) {
		this.data=data;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public double getNormalizedDataValue() {
		return normalizedDataValue;
	}

	public void setNormalizedDataValue(double normalizedDataValue) {
		this.normalizedDataValue = normalizedDataValue;
	}
	
	
	

}
