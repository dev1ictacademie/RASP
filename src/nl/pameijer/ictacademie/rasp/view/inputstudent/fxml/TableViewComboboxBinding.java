package nl.pameijer.ictacademie.rasp.view.inputstudent.fxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TableViewComboboxBinding extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {

		Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("TableViewComboboxBinding.fxml"));
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
