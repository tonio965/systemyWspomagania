package reading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Data;

public class ReadFromFileWithHeaders {

	List<Data> readData;
	boolean hasHeader;
	
	public ReadFromFileWithHeaders(boolean hasHeader) {
		readData = new ArrayList<Data>();
		this.hasHeader = hasHeader;
	}
	public List<Data> returnFromFile(String path) {
	    try {
	        File myFile = new File(path);
	        boolean hasLineWithHeaderHappened = false;
	        Scanner myReader = new Scanner(myFile);
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          if(data.length()>0) {
		          char first = data.charAt(0);
		          if(first =='#') //if is commented line or empty 
		        	  continue;
		          else {
		        	  if(!hasLineWithHeaderHappened && !hasHeader) { //will happen only once if header option is off - it skips the fist notnull nor commented line
			        	  hasLineWithHeaderHappened=true;
			        	  continue;
		        	  }
		        	  else {
			        	  String[] strArray = data.split(" |\\\t|\\;");
			        	  strArray = removeCharsFromNumericString(strArray); //it will remove chars from eg klasa1 klasa2 klasa3 to 1 2 3 
			        	  readData.add(new Data(strArray));
		        	  }
		          }
	        	  
	          }
	          
	        }
	        myReader.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("something wrong with file.");
	        e.printStackTrace();
	      }
	    return readData;
		
	}
	private String[] removeCharsFromNumericString(String[] strArray) {
		for(int i=0; i<strArray.length; i++) {
			if(strArray[i].matches(".*\\d"));
				strArray[i].replaceAll("[^0-9,-]", "");
		}
		return strArray;
	}
}
