package systemyWspomagania;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    private MenuItem saveToFileButton;
    
    @FXML
    private AnchorPane ParentPane;
    
    @FXML
    private TableView TableView1;
    
    @FXML
    private MenuItem percentMenuButton;
    
    @FXML
    private MenuItem twoDgraphButton;
 
    @FXML
    private MenuItem threeDgraphButton;

    @FXML
    private MenuItem histogramGraphDrawButton;
    
    @FXML
    private MenuItem LoadDataButton;
    
    @FXML
    private MenuItem discretizeColumnBtn;
    
    @FXML
    private MenuItem normalizeColumnButton;
    
    @FXML
    private MenuItem KNNButton;
    
    @FXML
    private MenuItem CutsButton;

    @FXML
    void CutsButtonClick(ActionEvent event) throws IOException {
    	
    	System.out.println("click");
    	//zmien
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLCuts.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLCutsController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Cuts");
    	stage.setScene(new Scene(pane));
    	stage.show();

    }

    @FXML
    void KNNButtonClick(ActionEvent event) throws IOException {

    	System.out.println("click");
    	//zmien
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLknn.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLknnController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("KNN");
    	stage.setScene(new Scene(pane));
    	stage.show();
    }
    
    

    @FXML
    void threeDgraphButtonClick(ActionEvent event) throws IOException {
    	
    	System.out.println("click");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLCreate3DPlot.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLCreate3DPlotController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Create histogram");
    	stage.setScene(new Scene(pane));
    	stage.show();

    }

    @FXML
    void twoDgraphButtonClick(ActionEvent event) throws IOException {
    	
    	System.out.println("click");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLCreate2DPlot.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLCreate2DPlotController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Create histogram");
    	stage.setScene(new Scene(pane));
    	stage.show();

    }
    
    @FXML
    void histogramGraphDrawButtonClick(ActionEvent event) throws IOException {
    	System.out.println("click");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLCreateHistogramPlot.fxml"));
    	Parent pane = (Parent) loader.load();
    	FXMLCreateHistogramPlotController secondController = loader.getController();
    	secondController.setSendDataSender(this);
    	secondController.sendCurrentList(dataInSeparateColumns);
    	Stage stage = new Stage();
    	stage.setTitle("Create histogram");
    	stage.setScene(new Scene(pane));
    	stage.show();
    }
    
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
    void saveToFileButtonClick(ActionEvent event) {
    	System.out.println("saving to file");
        String str = "Hello";
        BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("savedData.txt"));
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<dataInSeparateColumns.size(); i++) {
				sb.append(dataInSeparateColumns.get(i).getTitle()).append(" ");
			}
			sb.append("\n");
	        writer.write(sb.toString());
	        
			StringBuffer sb2 = new StringBuffer();
			for(int i=0;i<dataInSeparateColumns.size(); i++) {
				
				for(int j =0 ; j<dataInSeparateColumns.get(i).getContents().size() ; j++) {
					sb2.append(dataInSeparateColumns.get(i).getContents().get(j)).append(" ");
				}
				sb2.append("\n");
				writer.write(sb2.toString());
			}
	        
	        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
//            System.out.println("Column [" + i + "] ");
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
