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
import javafx.stage.Stage;
import models.DataColumn;
import models.DiscretizedColumn;

public class FXMLNormalizeController implements Initializable{

	
	private DataSender dataSender;
	private List<DataColumn> listOfCols;
	
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button normalizeButton;

    @FXML
    void normalizeButtonClicked(ActionEvent event) {
    	
    	double deviation =0;
    	double mean = 0 ;
    	double size=listOfCols.get(0).getContents().size();
    	List<Double> values = new ArrayList<>();
    	String columnName = comboBox.getValue().toString();
    	int columnId=-1;
    	
    	//get columnId
    	int iterator = 0;
    	for(DataColumn dc : listOfCols) {
    		if(dc.getTitle().equals(columnName))
    			columnId=iterator;
    		iterator++;
    	}
    	
    	//change string vals to doubles
    	for(int i=0 ;i<size ; i++) {
    		values.add(Double.valueOf(listOfCols.get(columnId).getContents().get(i)));
    	}
    	
    	mean = calculateMean(values);
    	
    	//calculateSumOfSquares
    	
    	deviation = calculateDeviation(values, mean);
    	
    	//calculate normalized values
    	
    	DataColumn dc = new DataColumn();
    	dc.setTitle(columnName+"_normalized");
    	for(int i=0;i<size;i++) {
    		double curr = Double.valueOf(listOfCols.get(columnId).getContents().get(i));
    		double result = (curr-mean)/deviation;
    		dc.addContent(String.valueOf(result));
    	}
    	
    	listOfCols.add(dc);
		dataSender.send(listOfCols);
	    Stage stage = (Stage) comboBox.getScene().getWindow();
	    stage.close();
    	
    	
    }
    
    private double calculateDeviation(List<Double> values, double mean) {
		double sumOfSquares = 0;
		double size = values.size();
		for(Double val: values) {
			double square = Math.pow((val-mean), 2);
			sumOfSquares+=square;
		}
		double result = Math.sqrt(sumOfSquares/size);
		return result;
	}

	private double calculateMean(List<Double> values) {
		double sum=0;
		double size=0;
		for(Double d : values) { 
			sum+=sum;
			size++;
		}
		return sum/size;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
