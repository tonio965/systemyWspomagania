package models;

public class DimensionCut {
	
	private Cut smallCut;
	
	private Cut bigCut;

	public DimensionCut(Cut smallCut, Cut bigCut) {
		super();
		this.smallCut = smallCut;
		this.bigCut = bigCut;
	}

	public Cut getSmallCut() {
		return smallCut;
	}

	public void setSmallCut(Cut smallCut) {
		this.smallCut = smallCut;
	}

	public Cut getBigCut() {
		return bigCut;
	}

	public void setBigCut(Cut bigCut) {
		this.bigCut = bigCut;
	}
	
	
	
	
	

}
