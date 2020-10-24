package systemyWspomagania;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class FXMLDocumentController implements Initializable{

    @FXML
    private Button Button1;

    @FXML
    private void button1pressed(ActionEvent event) {
    	System.out.println("button pressed");

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
