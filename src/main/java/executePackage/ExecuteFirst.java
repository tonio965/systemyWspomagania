package executePackage;

import java.util.Scanner;

import reading.ReadFromFileWithHeaders;

public class ExecuteFirst {
	
	public ExecuteFirst() {
		Scanner userInput = new Scanner(System.in);
		System.out.println("pass the filepath");
//		String input = userInput.nextLine();
		String input = "/Users/tomaszkoltun/eclipse-workspace/systemyWspomagania/src/main/resources/IRISDAT.txt";
		System.out.println("does it have headers? y/n");
		String decision = userInput.nextLine();
		switch(decision) {
			case "y":
				ReadFromFileWithHeaders fwh = new ReadFromFileWithHeaders(true);
				fwh.readFromFile(input);
				break;
			
			case "n":
				ReadFromFileWithHeaders fwh2 = new ReadFromFileWithHeaders(false);
				fwh2.readFromFile(input);
				break;
				
			default:
				System.out.println("it has to be n or y");
				break;
				
		}
	}

}
