package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import interfaces.DataSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import models.DataColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.VerticalBarPlot;

public class FXMLCreateHistogramPlotController implements Initializable{
	
	DataSender dataSender;
	List<DataColumn> listOfCols;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button button;

    @FXML
    void buttonClick(ActionEvent event) {
    	String columnTitle = comboBox.getValue().toString();
    	int columnId = findColumnId(columnTitle);
    	
    	Set<String> uniqueValues = new HashSet<>(listOfCols.get(columnId).getContents());
    	int [] occurencesCounter = new int [uniqueValues.size()];
    	int ctr=0;
		//now i have unique values set and now i will create a list sized as this set and count occurences in the columnlist
		for(String value : uniqueValues) { //iterate thorugh a set
			
			for(int i=0;i<listOfCols.get(columnId).getContents().size();i++) { //iterating through a full set (a column)
				if(value.equals(listOfCols.get(columnId).getContents().get(i))){ //if a value from set equals with the raw column i add 1 to the corresponding space in array
					occurencesCounter[ctr]++; //added one
				}
			}
			ctr++;
		//here set is full of unique vals and occurences counter has amount of occurences on the same index as in the set
		}
		String[] setArray = new String[uniqueValues.size()]; //string array from set
		setArray = uniqueValues.toArray(setArray);
		
		
    	Table myData =
    		    Table.create("barchart data")
    		        .addColumns(
    		            StringColumn.create(columnTitle, setArray),
    		            IntColumn.create("occurences", occurencesCounter));
    	
    	  Plot.show(
    		        VerticalBarPlot.create(
    		            "histogram", // plot title
    		            myData, // table
    		            columnTitle, // grouping column name
    		            "occurences")); // numeric column name
    }

	private int findColumnId(String columnTitle) {
		for(int i=0;i<listOfCols.size();i++) {
			if(listOfCols.get(i).getTitle().equals(columnTitle))
				return i;
		}
		return 0;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();		
	}
	
    //receive list from main screen
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
		for(DataColumn col : listOfCols) {
			comboBox.getItems().add(col.getTitle());
		}
    }
    
	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}
	
	

}
