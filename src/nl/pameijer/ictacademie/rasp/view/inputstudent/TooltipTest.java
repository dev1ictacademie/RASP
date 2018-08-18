package nl.pameijer.ictacademie.rasp.view.inputstudent;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TooltipTest extends Application {
	
	//working on ubuntu with gnome desktop

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox root =  new VBox();
		
		Button  btn = new Button();
		btn.setText("Hover Me!");
		Tooltip tt = new Tooltip();
		tt.setText("Text on Hover");
		tt.setStyle("-fx-font: normal bold 14 Langdon; "
		    + "-fx-base: #AE3522; "
		    + "-fx-text-fill: orange;");

		btn.setTooltip(tt);
		
		TextField textField = new TextField();
		textField.setTooltip(new Tooltip("add some text"));
		root.getChildren().addAll(textField, btn);
		Scene scene = new Scene(root,300,300);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);

	}

}
