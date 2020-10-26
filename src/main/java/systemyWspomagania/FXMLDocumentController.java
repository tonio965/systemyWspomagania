package systemyWspomagania;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



import executePackage.ExecuteFirst;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import models.Data;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Scatter3DPlot;
import tech.tablesaw.plotly.api.ScatterPlot;

public class FXMLDocumentController implements Initializable{
	
	//fields generated manually
	File selectedFile;
	
	List<Data> dataToPopulate;
	
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
    
    @FXML
    private TableView<Data> TableView1;
    

    @FXML
    private ScatterChart<?, ?> scatterChart1;
    
    @FXML
    private TextField scatterplotXtextField;

    @FXML
    private TextField scatterplotYextField;

    @FXML
    private TextField scatterplotZextField;

    @FXML
    private Button draw2dPlotButton;

    @FXML
    private Button draw3dPlotButton;
    
    @FXML
    void draw2dplot(ActionEvent event) {

    	String [] first = new String [dataToPopulate.size()];
    	String [] second = new String [dataToPopulate.size()];
    	String xVal = scatterplotXtextField.getText().toString();
    	String yVal = scatterplotYextField.getText().toString();
    	System.out.println("breakpoint");
		for(int i=0;i<dataToPopulate.size(); i++) {
			
			
	    	if(xVal.equals("n")) {
	    		first[i]=String.valueOf(dataToPopulate.get(i).getNormalizedDataValue());
	    	}
	    	else {
	    		int option=Integer.valueOf(xVal);
	    		second[i]=String.valueOf(dataToPopulate.get(i).getData()[option]);
	    	}
	    	if(yVal.equals("n")) {
	    		second[i]=String.valueOf(dataToPopulate.get(i).getNormalizedDataValue());
	    	}
	    	else {
	    		int option=Integer.valueOf(yVal);
	    		second[i]=String.valueOf(dataToPopulate.get(i).getData()[option]);
	    	}
		}
		
    	Table myData =
    		    Table.create("Cute data")
    		        .addColumns(
    		            StringColumn.create("first", first),
    		            StringColumn.create("second", second));

    	Plot.show(
    			ScatterPlot.create("2D Plot", 
    		                       myData, "first", "second")); //2dplot
    	
    	
    }

    @FXML
    void draw3dplot(ActionEvent event) {

    	String [] first = new String [dataToPopulate.size()];
    	String [] second = new String [dataToPopulate.size()];
    	String [] third = new String [dataToPopulate.size()];
    	String xVal = scatterplotXtextField.getText().toString();
    	String yVal = scatterplotYextField.getText().toString();
    	String zVal = scatterplotZextField.getText().toString();
		for(int i=0;i<dataToPopulate.size(); i++) {
			
	    	if(xVal.equals("n")) {
	    		first[i]=String.valueOf(dataToPopulate.get(i).getNormalizedDataValue());
	    	}
	    	else {
	    		int option=Integer.valueOf(xVal);
	    		second[i]=String.valueOf(dataToPopulate.get(i).getData()[option]);
	    	}
	    	
	    	
	    	if(yVal.equals("n")) {
	    		second[i]=String.valueOf(dataToPopulate.get(i).getNormalizedDataValue());
	    	}
	    	else {
	    		int option=Integer.valueOf(yVal);
	    		second[i]=String.valueOf(dataToPopulate.get(i).getData()[option]);
	    	}
	    	
	    	
	    	if(zVal.equals("n")) {
	    		third[i]=String.valueOf(dataToPopulate.get(i).getNormalizedDataValue());
	    	}
	    	else {
	    		int option=Integer.valueOf(zVal);
	    		third[i]=String.valueOf(dataToPopulate.get(i).getData()[option]);
	    	}
	    	
		}
    	Table myData =
    		    Table.create("Cute data")
    		        .addColumns(
    		            StringColumn.create("first", first),
    		            StringColumn.create("second", second),
    		            StringColumn.create("third", third));
    	Plot.show(
    			Scatter3DPlot.create("3d Plot", 
    		                       myData, "first", "second", "third")); //3dplot
    }
    
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
    	
    	
    	ExecuteFirst executeFirst = new ExecuteFirst();
    	dataToPopulate=executeFirst.returnDiscretizedAndNormalizedData(path, headers, minMaxCheckbox, minValue, maxValue, 
    			showPercentOfResults, topOrBottomPercentOfResults, percentValue, columnId, sectorAmount);
    	
    	populateTableViewWithData(dataToPopulate);
//    	createScatterChart(executeFirst.getRawDataset(),0,1);
    }

	private void createScatterChart(List<Data> rawDataset, int i, int j) {
		XYChart.Series dataSeries1 = new XYChart.Series();
		dataSeries1.setName("Scattered data");
		int counter=0;
		for(Data d: rawDataset) {
			if(counter ==0 && headerCheckbox.isSelected()) {
				counter++;
				continue;
			}
			else {
				dataSeries1.getData().add(new XYChart.Data( d.getData()[i], Double.valueOf(d.getData()[j])));
				counter++;
			}
		}
		scatterChart1.getData().add(dataSeries1);
		
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
		dataToPopulate = new ArrayList<>();
	}
	
    private void populateTableViewWithData(List<Data> rawDataset) {
    	ObservableList<Data> allData = FXCollections.observableArrayList();

    	
    	
    	int counter = 0;
    	for(Data d : rawDataset) {
    		if(headerCheckbox.isSelected() && counter ==0 ) {
    			counter++;
    			continue;
    		}
    		else {
        		allData.add(d); 
        		counter++;
    		}

    	}
    	System.out.println("breakpoint");
    	TableColumn sectorIdColumn = new TableColumn("sectorid");
    	sectorIdColumn.setCellValueFactory(new PropertyValueFactory<>("sectorId"));
    	
    	TableColumn normalizedColumnData = new TableColumn("normalizedCol");
    	normalizedColumnData.setCellValueFactory(new PropertyValueFactory<>("normalizedDataValue"));
    	
    	TableColumn  dataColumn = new TableColumn("combinedData");
    	dataColumn.setCellValueFactory(new PropertyValueFactory<>("combinedData"));
    	
    	TableView1.setItems(allData);
    	TableView1.getColumns().addAll(sectorIdColumn,dataColumn,normalizedColumnData);
    	
    	
    	
//    	Table myData =
//    		    Table.create("Cute data")
//    		        .addColumns(
//    		            StringColumn.create("zero", zeroColumn),
//    		            StringColumn.create("firsr", first),
//    		            StringColumn.create("third", mean));
//    	Plot.show(
//    			Scatter3DPlot.create("3d Plot", 
//    		                       myData, "zero", "firsr", "third")); //3dplot
//    	Plot.show(
//    			ScatterPlot.create("2D Plot", 
//    		                       myData, "zero", "firsr")); //2dplot
    }


	
	

}
