package systemyWspomagania;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;






import executePackage.ExecuteFirst;
import interfaces.DataSender;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Data;
import models.DataColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Scatter3DPlot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.api.VerticalBarPlot;

public class FXMLDocumentController implements Initializable, DataSender{
	
	//fields generated manually
	File selectedFile;
	
	String testVal;
	
	List<Data> dataToPopulate;
	
	
	
	List<DataColumn> dataInSeparateColumns;
	//annotated fields generated automatically
	
    @FXML
    private MenuItem digitizeStringColumnBtn;
	
    @FXML
    private MenuItem minMaxMenuButton;
	
	

    @FXML
    private AnchorPane ParentPane;
    
    @FXML
    private TableView TableView1;
    
    @FXML
    private MenuItem percentMenuButton;
    

    @FXML
    private ScatterChart<?, ?> scatterChart1;
    
    
    
    ///zostaje
    @FXML
    private TextField scatterplotXtextField;
    ///zostaje
    @FXML
    private TextField scatterplotYextField;
    ///zostaje
    @FXML
    private TextField scatterplotZextField;
    ///zostaje
    @FXML
    private Button draw2dPlotButton;
    ///zostaje
    @FXML
    private Button draw3dPlotButton;
    ///zostaje
    @FXML
    private TextField histogramTextField;
    ///zostaje
    @FXML
    private Button histogramButton;
    
    @FXML
    private MenuItem LoadDataButton;
    
    @FXML
    private MenuItem discretizeColumnBtn;
    
    @FXML
    private MenuItem normalizeColumnButton;
    
    @FXML
    void normalizeColumnButtonClick(ActionEvent event) throws IOException {
    	System.out.println("click");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLNormalize.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLNormalizeController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Normalize data");
    	stage.setScene(new Scene(pane));
    	stage.show();
    }
    

    @FXML
    void discretizeColumnBtnClicked(ActionEvent event) throws IOException {

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLDiscretize.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLDiscretizeController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Discretize data");
    	stage.setScene(new Scene(pane));
    	stage.show();
    }
    
    
    @FXML
    void percentMenuButtonClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLResultsPercentage.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLResultsPercentageController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Data filter");
    	stage.setScene(new Scene(pane));
    	stage.show();
    }
    
    @FXML
    void minMaxMenuButtonClicked(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLMinMaxFilter.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLMinMaxFilterController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Data filter");
    	stage.setScene(new Scene(pane));
    	stage.show();
    }
    
    
    //this one loads a popup window allowing to set file to display in a tableview
    @FXML
    void loadDataScreen(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLLoadData.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLLoadDataController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.myFunction("test send");
    	Stage stage = new Stage();
    	stage.setTitle("Data select");
    	stage.setScene(new Scene(pane));
    	stage.show();
    }
    
    @FXML
    void digitizeStringColumn(ActionEvent event) throws IOException {
    	
    	FXMLLoader secondloader = new FXMLLoader(getClass().getResource("/FXMLDigitizeData.fxml"));
    	Parent pane2 = (Parent) secondloader.load();
    	FXMLDigitizeDataController thirdController = secondloader.getController();
    	thirdController.setSendDataSender(this);
    	thirdController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("digitize string column");
    	stage.setScene(new Scene(pane2));
    	stage.show();

    }

    @FXML
    void createHistogram(ActionEvent event) {

    	String columnId = histogramTextField.getText().toString();
    	System.out.println("breakpoint");
    	List<String> dataFromColumn = new ArrayList<>();
    	int counter = 0;
    	boolean isNum =isDouble(columnId);
    	for(Data d : dataToPopulate) { //im taking only the column selected from filtered/%tented dataset
//    		if(counter == 0 && headerCheckbox.isSelected()) {
//    			counter++;
//    			continue;
//    		}
    		if(columnId.equals("n")) { //user selected a normalized column 
    			dataFromColumn.add(String.valueOf(d.getNormalizedDataValue()));
    		}
    		else { //user provided a column id from dataset
    			dataFromColumn.add(String.valueOf(d.getData()[Integer.valueOf(columnId)]));
    			
    		}
    	}
    		
    		//now im creating a unique values set from the list 
    		Set<String> uniqueValues = new HashSet<>(dataFromColumn);
    		int [] occurencesCounter = new int [uniqueValues.size()];
    		int ctr=0;
    		//now i have unique values set and now i will create a list sized as this set and count occurences in the columnlist
    		for(String value : uniqueValues) { //iterate thorugh a set
    			
    			for(int i=0;i<dataFromColumn.size();i++) { //iterating through a full set (a column)
    				if(value.equals(dataFromColumn.get(i))){ //if a value from set equals with the raw column i add 1 to the corresponding space in array
    					occurencesCounter[ctr]++; //added one
    				}
    			}
    			ctr++;
    		//here set is full of unique vals and occurences counter has amount of occurences on the same index as in the set
    		}
    		String[] setArray = new String[uniqueValues.size()]; //string array from set
    		setArray = uniqueValues.toArray(setArray);
    		
    		
        	Table myData =
        		    Table.create("barchart data")
        		        .addColumns(
        		            StringColumn.create("first", setArray),
        		            IntColumn.create("second", occurencesCounter));
        	
        	  Plot.show(
        		        VerticalBarPlot.create(
        		            "histogram", // plot title
        		            myData, // table
        		            "first", // grouping column name
        		            "second")); // numeric column name

    		
    		
    	
    }
    
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
	    		first[i]=String.valueOf(dataToPopulate.get(i).getData()[option]);
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
	    		first[i]=String.valueOf(dataToPopulate.get(i).getData()[option]);
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
    

	private void createScatterChart(List<Data> rawDataset, int i, int j) {
		XYChart.Series dataSeries1 = new XYChart.Series();
		dataSeries1.setName("Scattered data");
		int counter=0;
		for(Data d: rawDataset) {
//			if(counter ==0 && headerCheckbox.isSelected()) {
//				counter++;
//				continue;
//			}
//			else {
				dataSeries1.getData().add(new XYChart.Data( d.getData()[i], Double.valueOf(d.getData()[j])));
				counter++;
//			}
		}
		scatterChart1.getData().add(dataSeries1);
		
	}

    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dataToPopulate = new ArrayList<>();
		testVal = new String();
		dataInSeparateColumns = new ArrayList<>();
	}
	
	public void setTestVal(String s) {
		testVal=s;
	}
	
	public void printtest() {
		System.out.println("test");
		
	}
	
//    private void populateTableViewWithData(List<Data> rawDataset) {
//    	ObservableList<Data> allData = FXCollections.observableArrayList();
//
//    	
//    	int counter = 0;
//    	for(Data d : rawDataset) {
//    		if(headerCheckbox.isSelected() && counter ==0 ) {
//    			counter++;
//    			continue;
//    		}
//    		else {
//        		allData.add(d); 
//        		counter++;
//    		}
//
//    	}
//    	System.out.println("breakpoint");
//    	TableColumn sectorIdColumn = new TableColumn("sectorid");
//    	sectorIdColumn.setCellValueFactory(new PropertyValueFactory<>("sectorId"));
//    	
//    	TableColumn normalizedColumnData = new TableColumn("normalizedCol");
//    	normalizedColumnData.setCellValueFactory(new PropertyValueFactory<>("normalizedDataValue"));
//    	
//    	TableColumn  dataColumn = new TableColumn("combinedData");
//    	dataColumn.setCellValueFactory(new PropertyValueFactory<>("combinedData"));
//    	
//    	TableView1.setItems(allData);
//    	TableView1.getColumns().addAll(sectorIdColumn,dataColumn,normalizedColumnData);
    	
    	
    	
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
    //}
    
	public boolean isDouble( String input ) {
	    try {
	        Double.parseDouble( input );
	        return true;
	    }
	    catch( NumberFormatException e ) {
	    	System.err.println("column can't be casted to decimal number");
	        return false;
	    }
	}

	@Override
	public void send(List<DataColumn> data) {
		// TODO Auto-generated method stub
		dataInSeparateColumns=data;
		populateTableViewWithData2(dataInSeparateColumns);
		System.out.println("retreived data cols: "+ data.size());
		
	}

	private void populateTableViewWithData2(List<DataColumn> dataToPopulate2) {
		ObservableList<ObservableList> myData;
		myData = FXCollections.observableArrayList();
		TableView1.getColumns().clear();
		TableView1.getItems().clear();
        for (int i = 0; i < dataToPopulate2.size(); i++) {
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(dataToPopulate2.get(i).getTitle());
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
            	
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            TableView1.getColumns().addAll(col);
            System.out.println("Column [" + i + "] ");
        }
            
            
            
            for(int x =0;x<dataToPopulate2.get(0).getContents().size(); x++) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int y=0; y<dataToPopulate2.size();y++) {
                    row.add(dataToPopulate2.get(y).getContents().get(x));
                }
                System.out.println("Row [1] added " + row);
                myData.add(row);
 
            }
 
            //FINALLY ADDED TO TableView
            TableView1.setItems(myData);
        
		
	}


	
	

}
