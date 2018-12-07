package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

		Scene scene = new Scene(controller.getView().asParent(), 1600, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setTitle("Aanwezigheidsoverzicht ICT Academie per maand" );
	    primaryStage.setScene(scene);
	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent t) {
	            Platform.exit();
	            System.exit(0);
	        }
	    });
	    primaryStage.setResizable(true);
	    primaryStage.show();

	}

}
