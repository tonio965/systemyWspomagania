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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.DataColumn;

public class FXMLknnController implements Initializable{

	
	private DataSender dataSender;
	private List<DataColumn> listOfCols;
	
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBox2;
    
    @FXML
    private TextField neighboursTextField;

    @FXML
    private TextField dataTextfield;

    @FXML
    private Button button;

    @FXML
    private TextArea textArea1;
    
    private List<ComboBox> dynamicComboBoxes;
    @FXML
    void buttonClick(ActionEvent event) {

    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		dynamicComboBoxes = new ArrayList<>();
		
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
		comboBox2.getItems().add("Euklides");
		comboBox2.getItems().add("Manhattan");
		comboBox2.getItems().add("Chebyshew");
		comboBox2.getItems().add("Mahalanobis");
		for(int i=0; i<listOfCols.size()-1;i++) {
			ComboBox cb = new ComboBox();
			cb.setLayoutX(50);
			cb.setLayoutY(25 * (i+1));
			for(DataColumn col : listOfCols) {
				cb.getItems().add(col.getTitle());
			}
			dynamicComboBoxes.add(cb);
		}
		
		anchorPane.getChildren().addAll(dynamicComboBoxes);
		
	}
}
