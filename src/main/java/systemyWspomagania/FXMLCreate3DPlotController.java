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
