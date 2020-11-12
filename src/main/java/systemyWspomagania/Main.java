package systemyWspomagania;

import executePackage.ExecuteFirst;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import reading.ReadFromFileWithHeaders;

public class Main extends Application{

	public static void main(String[] args) {
//		ExecuteFirst executeFirst = new ExecuteFirst();
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Main window");
        stage.setScene(scene);
        stage.show();
	}

}
