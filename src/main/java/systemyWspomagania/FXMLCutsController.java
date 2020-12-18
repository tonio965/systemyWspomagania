package systemyWspomagania;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import HelperClasses.PointComparator;
import interfaces.DataSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import models.DataColumn;
import models.Point;

public class FXMLCutsController implements Initializable{

	private DataSender dataSender;
	List <DataColumn> listOfCols;
	ObservableList<String> options;
	List<Point> points;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listOfCols = new ArrayList<>();
		options = FXCollections.observableArrayList();
		points = new ArrayList<>();
		
	}
	
	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}
	
    //receive list from main screen
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
    	System.out.println("list size "+listOfCols.size());
    	System.out.println("list elems: "+ listOfCols.get(0).getContents().size());
    	createPoints();
    	performCuts();
    }

	private void createPoints() {
		
		//create points with id in basic entry data
		for(int i=0; i<listOfCols.get(0).getContents().size(); i++) {
			int column =0;
			List<Double> coords = new ArrayList<>();
			do {
				double d =Double.parseDouble(listOfCols.get(column).getContents().get(i));
				coords.add(d);
				column++;
			}
			while(column<listOfCols.size()-1);
			int decision = Integer.valueOf(listOfCols.get(listOfCols.size()-1).getContents().get(i));
			points.add(new Point(coords, i,decision));
		}
		
		//sort points using X axis
		points.sort(new PointComparator());
		
		System.out.println("breakpoint");
		
		
		
		
		
		
	}

	private void performCuts() {
		
		/**
		 * dla kazdego wymiaru poza klasa decyzyjna wiec size()-1
		 */
		
		for(int i=0;i<listOfCols.size()-1;i++) {
			
		}
	}
}
