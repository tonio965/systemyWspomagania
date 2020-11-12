package models;

import java.util.ArrayList;
import java.util.List;

public class DataColumn {
	
	String title;
	List<String> contents;
	
	public DataColumn(String title, List<String> contents) {
		super();
		this.title = title;
		this.contents = contents;
	}

	public DataColumn() {
		super();
		contents= new ArrayList<>();
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	
	public void addContent(String s) {
		contents.add(s);
	}
	
	
	
	
	

}
