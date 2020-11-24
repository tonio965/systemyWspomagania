package systemyWspomagania;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import interfaces.DataSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.DataColumn;

public class FXMLLoadDataController  implements Initializable{
	

	private DataSender dataSender; 
	List<DataColumn> listOfCols;
	
	File selectedFile;
	
    @FXML
    private Label label;
    
    @FXML
    private TextField tf1;

    @FXML
    private Button btn1;
    
    @FXML
    private CheckBox headerCheckbox;

    @FXML
    private Button SelectFileBtn;

    @FXML
    private CheckBox minAndMaxValueCheckbox;

    @FXML
    private CheckBox percentCheckbox;

    @FXML
    private CheckBox topPercentCheckbox;

    @FXML
    private TextField resultsPercentTextField;

    @FXML
    private Button sendDataButton;
    
    @FXML
    void headerCheckboxSelected(ActionEvent event) {

    }

    @FXML
    void minAndMaxValueCheckboxSelected(ActionEvent event) {
    }
    
    
    @FXML
    void selectFileButtonPressed(ActionEvent event) throws FileNotFoundException {
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilterTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.TXT");
    	fileChooser.getExtensionFilters().addAll(extFilterTXT); 
    	selectedFile = fileChooser.showOpenDialog(null);
    	readFromFile();
    }
    
    
    private void readFromFile() throws FileNotFoundException {
    	
    	//getting data from checkboxes etc
    	boolean headers = headerCheckbox.isSelected();
    	String path = selectedFile.getAbsolutePath();
    	int linesRead=0;
    	File myFile = new File(path);
    	boolean hasLineWithHeaderHappened = false;
    	Scanner myReader = new Scanner(myFile);
    	while (myReader.hasNextLine()) {
    		
    	
    		String data = myReader.nextLine();
    		if(data.length()>0) {
    			char first = data.charAt(0);
		          if(first =='#') //if is commented line or empty 
		        	  continue;
		          if(!hasLineWithHeaderHappened && headers) { //if file has headers but not yet found any
		        	  hasLineWithHeaderHappened=true;
		        	  String[] strArray = data.split(" |\\\t|\\;"); //now i know how many columns the dataset has so i can initialize an array
		        	  
		        	  //adding items to the list
		        	  for(int i=0; i<strArray.length;i++) {
		        		  DataColumn dc = new DataColumn();
		        		  dc.setTitle(strArray[i]);
		        		  listOfCols.add(dc);
		        	  }
		          } //if its not a header line and no headers
//		          if(!hasLineWithHeaderHappened && !headers) { //if file has headers but not yet found any
//		        	  hasLineWithHeaderHappened=true;
//		        	  Scanner titleScanner = new Scanner(myFile);
//		        	  String myData = null;
//		        	  int lines =0;
//		        	  while(lines<linesRead) {
//		        		   myData = titleScanner.nextLine();
//		        		   lines ++;
//		        	  }
//		        	  String[] strArray = data.split(" |\\\t|\\;"); //now i know how many columns the dataset has so i can initialize an array
//		        	  int size = strArray.length;
//		        	  
//		        	  //adding items to the list
//		        	  for(int i=0; i<strArray.length;i++) {
//		        		  DataColumn dc = new DataColumn();
//		        		  dc.setTitle("param"+i);
//		        		  listOfCols.add(dc);
//		        	  }
//		          }
		          else {
		        	  String[] strArray = data.split(" |\\\t|\\;");
		        	  strArray = removeCharsFromNumericString(strArray);
		        	  for(int i=0; i<strArray.length;i++) {
		        		  listOfCols.get(i).addContent(strArray[i]);
		        	  }
		          }
    			
    		}
    		linesRead++;
    	
    	}
    	myReader.close();
    	dataSender.send(listOfCols);
        Stage stage = (Stage) SelectFileBtn.getScene().getWindow();
        stage.close();


       	
    	
    }
    	

    @FXML
    void sendDataFromFile(ActionEvent event) throws FileNotFoundException {
    	
    }
    
	private String[] removeCharsFromNumericString(String[] strArray) {
		for(int i=0; i<strArray.length; i++) {
			if(strArray[i].matches(".*\\d"))
				strArray[i]=strArray[i].replaceAll("[^0-9,-]", ""); //replace all except numbers with a comma
			strArray[i]=strArray[i].replaceAll(",", "."); //relpace comma with dot - stupid java decimal conversion
		}
		return strArray;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    

    @FXML
    void bttclick(ActionEvent event) {
    	String s = tf1.getText().toString();
    	dataSender.send(listOfCols);
    }




	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		
	}
	
	public void myFunction(String test) {
		System.out.println("received data in 2nd screen:"+test);
	}

	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}

}
