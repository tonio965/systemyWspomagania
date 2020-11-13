package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import interfaces.DataSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import models.DataColumn;

public class FXMLDigitizeDataController implements Initializable {

	private DataSender dataSender;
	List <DataColumn> listOfCols;
	
	
    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private Button digitizeButton;

    @FXML
    void digitize(ActionEvent event) {
    	
    	dataSender.send(listOfCols);
    }
    
    
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		ObservableList<String> options = 
			    FXCollections.observableArrayList();
		for(DataColumn col : listOfCols) {
			options.add(col.getTitle());
			
		}
		
	}
	
	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}

}
