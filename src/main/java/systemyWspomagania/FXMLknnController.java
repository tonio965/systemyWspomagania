package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

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
import models.DataColumnRow;
import models.Distance;

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
    
    private List<DataColumnRow> rows;
    
    private List<Distance> distances;
    
    
    @FXML
    void buttonClick(ActionEvent event) {

    	populateRows();
    	calculateDistance();
    }
	
	private void calculateDistance() {
		String method = comboBox2.getValue().toString();

		
		String[] givenValues = dataTextfield.getText().split(";");
		if(!comboBox2.getValue().toString().equals("Mahalanobis")) {
			for(int i=0; i< rows.size() ;i++) {
				Distance d = new Distance();
				d.setId(i);
				double distance=0;
				if(comboBox2.getValue().toString().equals("Euklides"))
					distance+=euklideanDistance(rows.get(i), givenValues, i);
				if(comboBox2.getValue().toString().equals("Manhattan"))
					distance+=manhattanDistance(rows.get(i), givenValues, i);
				if(comboBox2.getValue().toString().equals("Chebyshew"))
					distance+=chebyshewDistance(rows.get(i), givenValues, i);
				d.setDistance(distance);
				distances.add(d);
			}
			
			//sort and provide closest neighbours
		}
		else {
			//calculate mean for each column of data
			double distanceFromMeanOfGivenValues=mahalanobisDistance(givenValues);
			List<Distance> distancesFromMean = new ArrayList<>();
			for(int i=0;i<rows.size(); i++) {
				String [] values = new String[rows.get(i).getData().size()];
				rows.get(i).getData().toArray(values);
				Distance d = new Distance();
				d.setId(i);
				d.setDistance(mahalanobisDistance(values));
				distancesFromMean.add(d);
			}

			
			
		}

		
		
	}
	
	private double mahalanobisDistance(String[] givenValues) {
		List<Double> meanColumnValues = new ArrayList<>();
		List<Double> providedValues = new ArrayList<>();
		List<Double> givenMinusMeanValues = new ArrayList<>();
		
		double[][] valuesMatrix = new double[rows.get(0).getData().size()][rows.size()];
		
		for(int x=0; x< givenValues.length ; x++) {
			providedValues.add(Double.valueOf(givenValues[x]));
		}
		
		//calculate mean for each column
		for(int i=0; i< rows.get(0).getData().size(); i++) {
			double sumInColumn =0;
			for(int j=0; j<rows.size(); j++) {
				sumInColumn+=Double.valueOf(rows.get(j).getData().get(i));
				valuesMatrix[i][j]=Double.valueOf(rows.get(j).getData().get(i));
			}
			meanColumnValues.add(sumInColumn/rows.size());
		}
		
		// calculate (x-m) given array minus mean array
		for(int x = 0 ; x< meanColumnValues.size() ; x++) {
			givenMinusMeanValues.add(providedValues.get(x) - meanColumnValues.get(x));
		}
		
		//pionowa macierz 
		double[][] verticalMatrixGiven = new double [1][givenMinusMeanValues.size()];
		for(int i=0;i<givenMinusMeanValues.size(); i++) {
			verticalMatrixGiven[0][i] = givenMinusMeanValues.get(i);
		}
		
		RealMatrix verticalMatrix = MatrixUtils.createRealMatrix(verticalMatrixGiven);
		
		//pozioma macierz
		double[][] horizontalMatrixGiven = new double [givenMinusMeanValues.size()][1];
		for(int i=0;i<givenMinusMeanValues.size(); i++) {
			horizontalMatrixGiven[i][0] = givenMinusMeanValues.get(i);
		}
		
		RealMatrix horizontalMatrix = MatrixUtils.createRealMatrix(horizontalMatrixGiven);
		
		//obliczam maciez kowariancji z apache commons
		RealMatrix mx = MatrixUtils.createRealMatrix(valuesMatrix);
		RealMatrix cov = new Covariance(mx).getCovarianceMatrix();
		RealMatrix inversed = MatrixUtils.inverse(cov);
		double [][] covarianceMatrix = cov.getData();
		double [][] inversedCovarianceMatrix = inversed.getData();
		
		RealMatrix p1 = horizontalMatrix.multiply(inversed);
		RealMatrix p2 = p1.multiply(verticalMatrix);
		
		double [][] distance = p2.getData();
		double dist=0;
		for(int i=0;i<distance.length; i++) {
			for(int j=0; j<distance[i].length; j++) {
				dist+=distance[i][j];
			}
		}

		return dist;
	}

	private double chebyshewDistance(DataColumnRow dataColumnRow, String[] givenValues, int i) {
		List<Double> rowValues = new ArrayList<>();
		List<Double> providedValues = new ArrayList<>();
		double maxDistance =0;
		
		for(int x=0; x< givenValues.length ; x++) {
			rowValues.add(Double.valueOf(dataColumnRow.getData().get(x)));
			providedValues.add(Double.valueOf(givenValues[x]));
		}
		
		for(int x=0; x<givenValues.length; x++) {
			double distance= Math.abs(rowValues.get(x)-providedValues.get(x));
			
			if(distance > maxDistance)
				maxDistance = distance;
		}
		return maxDistance;
	}

	private double manhattanDistance(DataColumnRow dataColumnRow, String[] givenValues, int i) {
		List<Double> rowValues = new ArrayList<>();
		List<Double> providedValues = new ArrayList<>();
		double distance =0;
		
		for(int x=0; x< givenValues.length ; x++) {
			rowValues.add(Double.valueOf(dataColumnRow.getData().get(x)));
			providedValues.add(Double.valueOf(givenValues[x]));
		}
		
		for(int x=0; x<givenValues.length; x++) {
			distance+= Math.abs(rowValues.get(x)-providedValues.get(x));
		}
		return distance;
		
	}

	private double euklideanDistance(DataColumnRow dataColumnRow, String[] givenValues, int i) {
		List<Double> rowValues = new ArrayList<>();
		List<Double> providedValues = new ArrayList<>();
		double distance =0;
		
		for(int x=0; x< givenValues.length ; x++) {
			rowValues.add(Double.valueOf(dataColumnRow.getData().get(x)));
			providedValues.add(Double.valueOf(givenValues[x]));
		}
		
		for(int x=0; x<givenValues.length; x++) {
			distance+= Math.pow((rowValues.get(x)-providedValues.get(x)), 2);
		}
		distance=Math.sqrt(distance);
		return distance;
	}

	
	
	private void populateRows() {
		List<String> columnNames = new ArrayList<>();
		int decissionClassColumnId=0;
		int [] columnIds;
		
		
		//get names from not empty comboBoxes
		for(int i=0;i<dynamicComboBoxes.size();i++) {
			
			if(!dynamicComboBoxes.get(i).getValue().toString().equals("")) {
				columnNames.add(dynamicComboBoxes.get(i).getValue().toString());
			}

		}
		
		//get id of decission class value
		for(int i=0; i <listOfCols.size();i++) {
			if(listOfCols.get(i).getTitle().equals(comboBox.getValue().toString())) {
				decissionClassColumnId=i;
			}
		}
		
		//now when i know how many not empty columns there is
		columnIds = new int[columnNames.size()];
		
		//get ids of corresponding columns
		for(int i=0;i<columnNames.size();i++) {
			
			for(int j=0; j<listOfCols.size(); j++) {
				if(columnNames.get(i).equals(listOfCols.get(j).getTitle())) {
					columnIds[i]=j;
				}
				
			}
		}
		
		//now i can start making rows and fill it with the data
		for(int i=0 ; i<listOfCols.get(0).getContents().size(); i++) {
			
			DataColumnRow dcr = new DataColumnRow();
			for(int j=0;j<columnIds.length; j++) {
				dcr.addData(listOfCols.get(columnIds[j]).getContents().get(i));
			}
			//dodac decyzyjna klase tutaj do dcr
			dcr.setDecision(listOfCols.get(decissionClassColumnId).getContents().get(i));
			rows.add(dcr);
			
		}
		
		
	System.out.println("breakpoint");	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		dynamicComboBoxes = new ArrayList<>();
		rows = new ArrayList<>();
		distances = new ArrayList<>();
		
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
