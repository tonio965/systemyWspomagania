package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import HelperClasses.NormalizedDataComparator;
import HelperClasses.ValueWrapperToSortComparator;
import interfaces.DataSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DataColumn;
import models.ValueWrapperToSort;

public class FXMLResultsPercentageController implements Initializable {

	private DataSender dataSender;
	List<DataColumn> listOfCols;
	
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private Button button;

    @FXML
    private TextField textField2;

    @FXML
    void buttonClicked(ActionEvent event) {
    	 List<ValueWrapperToSort> wrapValues = new ArrayList<>();
    	 String selectedComboBox = comboBox.getValue();
    	 String selectedComboBox2 = comboBox2.getValue();
    	 int [] indexesOfElements;
    	 
    	 
    	 System.out.println("clicked");
	     int columnId= -1;
	     int index =0;
	    	//get id of column with this title
	     for(DataColumn dc : listOfCols) {
	    	 if(dc.getTitle().equals(selectedComboBox)) {
	    		columnId=index;
	    	 }
	    	 index++;
	     }
	     // find min and max value of a set
	     
	     //make the double list of values from a given column
	     List<Double> valuesInAColumn = new ArrayList<>();
	     for(int i=0; i< listOfCols.get(0).getContents().size();i++) {
	    	 
	    	 wrapValues.add(new ValueWrapperToSort(i, Double.valueOf(listOfCols.get(columnId).getContents().get(i))));
	     }
	     //from smallest to biggest
	     wrapValues.sort(new ValueWrapperToSortComparator());
	     
	     //calculate the amount of elements eg 10% from 100 is 10
	     int amountOfElements = findPercentage(wrapValues.size(), 
	    		 Integer.valueOf(textField2.getText().toString()));
	     
	     indexesOfElements = new int[amountOfElements];
	     
	     
	     if(selectedComboBox2.equals("top")) {
	    	 for(int i=0;i<amountOfElements;i++) {
	    		 indexesOfElements[i] = wrapValues.get(wrapValues.size()-1-i).getPosition();
	    	 }
	     }
	    
		 if(selectedComboBox2.equals("bottom")) {
			 for(int i=0;i<amountOfElements;i++) {
		   		 indexesOfElements[i] = wrapValues.get(i).getPosition();
		   	 }	     
	     }
		 
		 //sort the array of indexes from the smallest to the biggest
		 
		 List<DataColumn> dataColumnToReturn = new ArrayList<>();
		 
		 for(int i=0; i<listOfCols.size(); i++) {
			 
			 DataColumn dc = new DataColumn();
			 dc.setTitle(listOfCols.get(i).getTitle());
			 
			 for(int j=0;j<indexesOfElements.length;j++) {
				 
				 dc.addContent(listOfCols.get(i).getContents().get(indexesOfElements[j]));
			 }
			 
			 dataColumnToReturn.add(dc);
		 }
		 
		 dataSender.send(dataColumnToReturn);
	     Stage stage = (Stage) comboBox.getScene().getWindow();
	     stage.close();
		 
	     

    }
	
	private int findPercentage(int size, int percentage) {
		int result = (int)(size*(percentage*0.01));
		return result;
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
		comboBox2.getItems().add("top");
		comboBox2.getItems().add("bottom");
		
	}
	
	public static int[] maxKIndex(double[] array, int top_k) {
		
		
	    double[] max = new double[top_k];
	    int[] maxIndex = new int[top_k];
	    Arrays.fill(max, Double.NEGATIVE_INFINITY);
	    Arrays.fill(maxIndex, -1);

	    top: for(int i = 0; i < array.length; i++) {
	        for(int j = 0; j < top_k; j++) {
	            if(array[i] > max[j]) {
	                for(int x = top_k - 1; x > j; x--) {
	                    maxIndex[x] = maxIndex[x-1]; max[x] = max[x-1];
	                }
	                maxIndex[j] = i; max[j] = array[i];
	                continue top;
	            }
	        }
	    }
	    return maxIndex;
	}
}
