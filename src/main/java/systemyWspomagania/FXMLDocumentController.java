package systemyWspomagania;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import executePackage.ExecuteFirst;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class FXMLDocumentController implements Initializable{
	
	//fields generated manually
	File selectedFile;
	
	
	//annotated fields generated automatically

    @FXML
    private AnchorPane ParentPane;

    @FXML
    private Button SelectFileBtn;

    @FXML
    private CheckBox headerCheckbox;

    @FXML
    private CheckBox minAndMaxValueCheckbox;

    @FXML
    private TextField maxValueTextField;

    @FXML
    private CheckBox percentCheckbox;

    @FXML
    private TextField resultsPercentTextField;

    @FXML
    private Button firstExcerciseButton;

    @FXML
    private CheckBox topPercentCheckbox;

    @FXML
    private TextField minValueTextField;
    
    @FXML
    private TextField columnIdTextField;

    @FXML
    private TextField sectorsTextField;
    
    //onclick on select etc
    

    @FXML
    void executeFirstExcercise(ActionEvent event) {
    	System.out.println("execute first exc");
    	String path = selectedFile.getAbsolutePath();
    	boolean headers = headerCheckbox.isSelected();
    	boolean minMaxCheckbox = minAndMaxValueCheckbox.isSelected(); //ticked original else - min and max
    	Double minValue= 0d;
    	Double maxValue= 0d;
    	boolean showPercentOfResults = percentCheckbox.isSelected(); //selected - original not selected - use topBottomValue
    	boolean topOrBottomPercentOfResults = topPercentCheckbox.isSelected(); //selected - top not selected - bottom
    	int percentValue=0;
    	if(!resultsPercentTextField.getText().equals("")) {
    		percentValue = Integer.valueOf(resultsPercentTextField.getText());
    	}
    	int columnId=0;
    	if(!columnIdTextField.getText().equals("")) {
    		columnId = Integer.valueOf(columnIdTextField.getText());
    	}
    	int sectorAmount =0;
    	if(!sectorsTextField.getText().equals("")) {
    		sectorAmount=Integer.valueOf(sectorsTextField.getText());
    	}
    	
    	
    	
    	if(!minValueTextField.getText().equals("")) {
    		minValue= Double.valueOf(minValueTextField.getText());
    	}
    	if(!maxValueTextField.getText().equals("")) {
    		minValue= Double.valueOf(maxValueTextField.getText());
    	}
    	
    	
    	ExecuteFirst executeFirst = new ExecuteFirst(path,headers,minMaxCheckbox,minValue,maxValue,
    			showPercentOfResults,topOrBottomPercentOfResults, percentValue, columnId, sectorAmount);
    }

    @FXML
    void headerCheckboxSelected(ActionEvent event) {

    }

    @FXML
    void minAndMaxValueCheckboxSelected(ActionEvent event) {

    }
    
    @FXML
    void selectFileButtonPressed(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	FileChooser.ExtensionFilter extFilterTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.TXT");
    	fileChooser.getExtensionFilters().addAll(extFilterTXT); 
    	selectedFile = fileChooser.showOpenDialog(null);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	

}
