package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.view.month.Controller;

public class StartApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Model model = new Model();
		Controller controller = new Controller(model);
        
		Scene scene = new Scene(controller.getView().asParent(), 1000, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Aanwezigheidsoverzicht ICT Academie per maand" );
	    primaryStage.setScene(scene);
	    primaryStage.setResizable(true);
	    primaryStage.show();

	}

}
