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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DataColumn;

public class FXMLMinMaxFilterController implements Initializable {

	 private DataSender dataSender;
	 
	 private List<DataColumn> listOfCols;
	
	 @FXML
	 private ComboBox<String> comboBox;

	 @FXML
	 private TextField minTextField;

	 @FXML
	 private TextField maxTextField;

	 @FXML
	 private Button filterButton;

	 @FXML
	 void filterButtonClicked(ActionEvent event) {
		 double min = Double.valueOf(minTextField.getText().toString());
		 double max = Double.valueOf(maxTextField.getText().toString());
		 String selectedComboBox = comboBox.getValue();
	     int columnId= -1;
	     int index =0;
	    	//get id of column with this title
	     for(DataColumn dc : listOfCols) {
	    	 if(dc.getTitle().equals(selectedComboBox)) {
	    		columnId=index;
	    	 }
	    	 index++;
	     }

	     int amountOfContent =listOfCols.get(0).getContents().size();
	     //im traversing from the end not to change the index of next elements in a list
		 for(int i=amountOfContent-1;i>=0; i--) {
			 //if doesnt fit between min&max
			 if(Double.valueOf(listOfCols.get(columnId).getContents().get(i))< min ||
					 Double.valueOf(listOfCols.get(columnId).getContents().get(i)) > max) {
				 //remove its occurences from all columns
				 for(DataColumn dc : listOfCols) {
					 dc.getContents().remove(i);
				 }
			 }
		 }

		 
		 //send to main
		 dataSender.send(listOfCols);
	     Stage stage = (Stage) comboBox.getScene().getWindow();
	     stage.close();
	 }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		listOfCols = new ArrayList<>();

	}
	
	public void setSendDataSender(DataSender ds) {
		this.dataSender=ds;
		System.out.println("filter data");
	}

	public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
		for(DataColumn col : listOfCols) {
			comboBox.getItems().add(col.getTitle());
		}
		
	}
	

}
