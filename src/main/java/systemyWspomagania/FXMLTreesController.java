package systemyWspomagania;

import java.util.ArrayList;
import java.util.List;

import interfaces.DataSender;
import models.DataColumn;
import models.DataColumnRow;
import models.DataRowWithCentroidID;
import models.Node;

public class FXMLTreesController {
	
	private DataSender dataSender;
	private List<DataColumn> listOfCols;
	List<DataColumnRow> rows;
	
	public void setSendDataSender(DataSender ds){
	    this.dataSender=ds;
	}
	
	
    //receive list from main screen
    public void sendCurrentList(List<DataColumn> list) {
    	this.listOfCols = list;
    	rows = new ArrayList<>();
    	System.out.println("loc "+listOfCols.size());
    	
    	for(int i=0; i<listOfCols.get(0).getContents().size(); i++) {
    		
    		List<String> values = new ArrayList<>();
    		for(int j=0 ; j<listOfCols.size()-1; j++) {
    			values.add(listOfCols.get(j).getContents().get(i));
    		}
    		DataColumnRow dc = new DataColumnRow();
    		dc.setData(values);
    		dc.setDecision(listOfCols.get(listOfCols.size()-1).getContents().get(i));
    		rows.add(dc);
    	}
    	buildTree();
    }
    
    public void buildTree() {
    	
    	int success=0;
    	int size=rows.size();
    	
    	for(int y=0;y<rows.size();y++) {
        	Node<String> rootNode = new Node<String>("root");
        	Node<String> currentNode = rootNode;
        	
        	//iterate through every row (150 times)
        	for(int i=0;i<rows.size();i++) {
        		
        		if(i==y)
        			continue;
        		
        		boolean isNotFinished = true;
        		List<String> data = rows.get(i).getData();
        		
        		for(int j=0;j<rows.get(0).getData().size();j++) {
        			
        			List<Node<String>>children = currentNode.getChildren();
        			int childIndex = -1;
        			String currentValue = data.get(j);
        			
        			for(int x=0;x<children.size();x++) {
        				if(children.get(x).getData().equals(currentValue)) {
        					childIndex=x;
        				}
        			}
        			//if exists in current node - current = this child
        			if(childIndex!=-1) {
        				currentNode=currentNode.getChildren().get(childIndex);
        			}
        			//doesnt exist in the node
        			else {
        				currentNode=currentNode.addChild(new Node<String>(currentValue));
        			}
        			
        		}
    			List<Node<String>>children = currentNode.getChildren();
    			int childIndex = -1;
    			String currentValue = rows.get(i).getDecision();
    			
    			for(int x=0;x<children.size();x++) {
    				if(children.get(x).getData().equals(currentValue)) {
    					childIndex=x;
    				}
    			}
    			//if doesnt exist 
    			if(childIndex==-1) {
    				currentNode.addChild(new Node<String>(currentValue));
    			}
    			
        		//here iterate to the root (get parent get parent ...)
        		//and assign it to the currentNode
    			boolean hasParent=true;
    			while(hasParent) {
    				if(currentNode.getParent()!=null) {
    					
    					currentNode=currentNode.getParent();
    				}
    					
    				else {
    					hasParent=false;
    				}
    				
    			}	
        		
        	}
        	rootNode = currentNode;
        	
        	
        	success += checkIfPredictsCorrectly(rootNode, rows.get(y));
        	
        	
        	
        	
    		
    	}

    	//when we are here we have a complete tree done 
    	//so we can assign current node to the root
    	System.out.println("success: "+success +" size: "+rows.size());
    }


	private int checkIfPredictsCorrectly(Node<String> rootNode, DataColumnRow dcr) {
		
		List<String> data = dcr.getData();
		
		
		Node<String> currentNode = rootNode;
		for(int j=0;j<rows.get(0).getData().size();j++) {
			
			List<Node<String>>children = currentNode.getChildren();
			int childIndex = -1;
			String currentValue = data.get(j);
			
			for(int x=0;x<children.size();x++) {
				if(children.get(x).getData().equals(currentValue)) {
					childIndex=x;
				}
			}
			//if exists in current node - current = this child
			if(childIndex!=-1) {
				currentNode=currentNode.getChildren().get(childIndex);
			}
			//doesnt exist in the node
			else {
				currentNode=currentNode.addChild(new Node<String>(currentValue));
				return 0;
			}
			
		}
		List<Node<String>>children = currentNode.getChildren();
		int childIndex = -1;
		String currentValue = dcr.getDecision();
		
		for(int x=0;x<children.size();x++) {
			if(children.get(x).getData().equals(currentValue)) {
				childIndex=x;
			}
		}
		//if doesnt exist 
		if(childIndex==-1) {
			currentNode.addChild(new Node<String>(currentValue));
			return 0;
		}
		
		return 1;
	}
    

}
