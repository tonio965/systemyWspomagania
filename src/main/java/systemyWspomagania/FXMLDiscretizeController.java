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
import models.DiscretizedColumn;

public class FXMLDiscretizeController implements Initializable {

	private DataSender dataSender;
	private List<DataColumn> listOfCols;
	

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField textField;

    @FXML
    private Button button;

    @FXML
    void buttonClick(ActionEvent event) {

    	String title = comboBox.getValue().toString();
    	int amountOfSectors = Integer.valueOf(textField.getText().toString());
    	int index=0;
    	int columnIndex=0;
    	double min=0, max=0;
    	double sectorSize;
    	//get id of column
    	for(DataColumn dc : listOfCols) {
    		if(dc.getTitle().equals(title)) {
    			columnIndex=index;
    		}
    		index++;
    	}
    	
    	//make double vals list from this column
    	Double [] oneColumn = new Double[listOfCols.get(0).getContents().size()];
    	
    	for(int i =0 ; i <listOfCols.get(0).getContents().size() ; i++) {
    		oneColumn[i] = Double.valueOf(listOfCols.get(columnIndex).getContents().get(i));
    	}
		double [] minAndMax = getMinMax(oneColumn);
		min = minAndMax[0];
		max = minAndMax[1];
		sectorSize=(max-min)/amountOfSectors;
		
		DataColumn dc = new DataColumn();
		dc.setTitle(title+"_discretized");
		
		double tinyFraction = 0.000001d;
		double currentSectorMin=min;
		double currentSectorMax=min+sectorSize;
		
		String [] sectorsColumn = new String[listOfCols.get(0).getContents().size()];
		for(int i=0;i<amountOfSectors; i++) { 

			
			for(int j=0; j<oneColumn.length;j++) {
				if(oneColumn[j]>=currentSectorMin && oneColumn[j]<=currentSectorMax) {
					sectorsColumn[j]= String.valueOf(i);
				}
				
			}
			currentSectorMin=currentSectorMax+tinyFraction;
			currentSectorMax=currentSectorMin+sectorSize;
		}
		for(int k=0; k<oneColumn.length;k++) {
			dc.addContent(sectorsColumn[k]);
		}
		
//		listOfCols.add(dc);
		listOfCols.set(columnIndex, dc);
		dataSender.send(listOfCols);
	    Stage stage = (Stage) comboBox.getScene().getWindow();
	    stage.close();

    }
    
    
	private double[] getMinMax(Double [] column) {
		double [] minAndMax = new double [2];
		double max = Integer.MIN_VALUE;
		double min = Integer.MAX_VALUE;
		for(int i=0 ; i< column.length; i++) {
			if(column[i]> max)
				max = column[i];
			if(column[i]<min)
				min=column[i];
		}
		minAndMax[0]=min;
		minAndMax[1]=max;
		
		return minAndMax;
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
