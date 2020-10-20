package executePackage;

import java.util.List;
import java.util.Scanner;

import reading.ReadFromFileWithHeaders;
import models.Data;
import models.DiscretizedColumn;
import modifyingData.Discretization;

public class ExecuteFirst {
	
	public ExecuteFirst() {
		Scanner userInput = new Scanner(System.in);
//		System.out.println("pass the filepath");
//		String input = userInput.nextLine();
		String input = "/Users/tomaszkoltun/eclipse-workspace/systemyWspomagania/src/main/resources/INCOME.txt";
		System.out.println("does it have headers? y/n");
		String decision = userInput.nextLine();
		List<Data> readFromFile;
		switch(decision) {
			case "y":
				ReadFromFileWithHeaders fwh = new ReadFromFileWithHeaders(true);
				readFromFile=fwh.returnFromFile(input);
				Discretization d= new Discretization(readFromFile, true);
				List<DiscretizedColumn> dc =d.discreteColumn(2, 3);
				break;
			
			case "n":
				ReadFromFileWithHeaders fwh2 = new ReadFromFileWithHeaders(false);
				readFromFile=fwh2.returnFromFile(input);
				Discretization d2= new Discretization(readFromFile, false);
				List<DiscretizedColumn> dc2 =d2.discreteColumn(2, 3);
				break;
				
			default:
				System.out.println("it has to be n or y");
				break;
				
		}
	}

}
