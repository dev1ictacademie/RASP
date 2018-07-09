package nl.pameijer.ictacademie.rasp.view.tools;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Display information or warning message, the show method accept the message and or
 * the title to be displayed.
 * @author hintveld
 *
 */
public class MessageBox
{
	/**
	 * The show method accept a Sting message and or
	 * a String title to be displayed.
	 * @param message
	 * @param title
	 */
	public static void show(String message, String title)
	{
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);

		Label lbl = new Label();
		lbl.setText(message);

		Button btnOk = new Button();
		btnOk.setText("OK");
		btnOk.setOnAction(e -> stage.close());

		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl, btnOk);
		pane.setAlignment(Pos.CENTER);

		Scene scene = new Scene(pane, message.length() * 10, 100);
		stage.setScene(scene);
		stage.showAndWait();
	}
}
