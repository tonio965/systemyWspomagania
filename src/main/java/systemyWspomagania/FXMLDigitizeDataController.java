package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import interfaces.DataSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import models.DataColumn;

public class FXMLDigitizeDataController implements Initializable {

	private DataSender dataSender;
	List <DataColumn> listOfCols;
	ObservableList<String> options;
	
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button digitizeButton;

    @FXML
    void digitize(ActionEvent event) {
    	String selectedComboBox = comboBox.getValue();
    	System.out.println("val from cbbox: "+ selectedComboBox);
    	
    	int columnId= -1;
    	int index =0;
    	//get id of column with this title
    	for(DataColumn dc : listOfCols) {
    		if(dc.getTitle().equals(selectedComboBox)) {
    			columnId=index;
    		}
    		index++;
    	}
    	DataColumn numerizedColumn = new DataColumn();
    	numerizedColumn.setTitle(selectedComboBox+"_numerized");
    	Set<String> set = new HashSet(listOfCols.get(columnId).getContents());
    	
    	//iterate through the set to change strings into 0 1 2 3 etc in desc order
    	for(int i=0; i<listOfCols.get(0).getContents().size();i++) {
    		
    		int indexInSet=0;
    		for(String name: set) {
    			if(listOfCols.get(columnId).getContents().get(i).equals(name)) {
    				numerizedColumn.addContent(String.valueOf(indexInSet));
    			}
    			indexInSet++;
    		}
    	}
    	listOfCols.add(numerizedColumn);
    	dataSender.send(listOfCols);
        Stage stage = (Stage) comboBox.getScene().getWindow();
        stage.close();
    }
    
    //receive list from main screen
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
		for(DataColumn col : listOfCols) {
			comboBox.getItems().add(col.getTitle());
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		options = FXCollections.observableArrayList();

		
	}
	
	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}

}
