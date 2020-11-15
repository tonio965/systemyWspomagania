package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.DataSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import models.DataColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Scatter3DPlot;
import tech.tablesaw.plotly.api.ScatterPlot;

public class FXMLCreate3DPlotController implements Initializable {
	
	DataSender dataSender;
	List<DataColumn> listOfCols;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBox2;
    
    @FXML
    private ComboBox<String> comboBox3;

    @FXML
    private Button button;

    @FXML
    void buttonClick(ActionEvent event) {
    	String columnTitle = comboBox.getValue().toString();
    	String column2Title = comboBox2.getValue().toString();
    	String column3Title = comboBox3.getValue().toString();
    	
    	String [] first = new String [listOfCols.get(0).getContents().size()];
    	String [] second = new String [listOfCols.get(0).getContents().size()];
    	String [] third = new String [listOfCols.get(0).getContents().size()];
    	
    	int columnId = findColumnId(columnTitle);
    	int column2Id = findColumnId(column2Title);
    	int column3Id = findColumnId(column3Title);
    	
    	for(int i=0; i<listOfCols.get(0).getContents().size();i++) {
    		
    		first[i]=String.valueOf(listOfCols.get(columnId).getContents().get(i)); 
    		second[i]=String.valueOf(listOfCols.get(column2Id).getContents().get(i)); 
    		third[i]=String.valueOf(listOfCols.get(column3Id).getContents().get(i)); 
    	}
    	
    	Table myData =
    		    Table.create("Cute data")
    		        .addColumns(
    		            StringColumn.create(columnTitle, first),
    		            StringColumn.create(column2Title, second),
    		            StringColumn.create(column3Title, third));

    	Plot.show(
    			Scatter3DPlot.create("3D Plot", 
    		                       myData, columnTitle, column2Title,column3Title )); //3dplot
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
			comboBox2.getItems().add(col.getTitle());
			comboBox3.getItems().add(col.getTitle());
		}
    }
    
	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}

}
