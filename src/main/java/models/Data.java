package models;

public class Data {
	
	private String [] data;
	private int sectorId;
	private double normalizedDataValue;
	private String combinedData;
	
	public Data(String [] data ) {
		this.data=data;
		this.combinedData=setCombinedData(data);
		System.out.println(this.getCombinedData());
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
	
	public String getDataFromPosition(int pos) {
		return data[pos];
	}
	
	public String setCombinedData(String [] data) {
		StringBuilder sb = new StringBuilder("");
		for(int i=0; i < data.length;i++) {
			sb.append(data[i]).append(", ");
		}
		sb.deleteCharAt(sb.length()-2);
		return sb.toString();
	}

	public String getCombinedData() {
		return combinedData;
	}
	
	
	
	
	
	
	
	

}
