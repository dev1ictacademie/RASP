package nl.pameijer.ictacademie.rasp.view.tools;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class to ask the user before performing a task.
 * @author hintveld
 *
 */
public class ConfirmationBox
{
	static Stage stage;
	static boolean btnYesClicked;

	/**
	 * The show method accept four String parameters.
	 * @param message
	 * @param title
	 * @param textYes
	 * @param textNo
	 * @return
	 */
	public static boolean show(String message, String title, String textYes, String textNo)
	{
		btnYesClicked = false;
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);

		Label lbl = new Label();
		lbl.setText(message);

		Button btnYes = new Button();
		btnYes.setText(textYes);
		btnYes.setOnAction(e -> btnYes_Clicked());

		Button btnNo = new Button();
		btnNo.setText(textNo);
		btnNo.setOnAction(e -> btnNo_Clicked());

		HBox paneBtn = new HBox(20);
		paneBtn.getChildren().addAll(btnYes, btnNo);
		paneBtn.setAlignment(Pos.CENTER);

		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl, paneBtn);
		pane.setAlignment(Pos.CENTER);

		Scene scene = new Scene(pane, message.length() * 8, 100);
		stage.setScene(scene);
		stage.showAndWait();

		return btnYesClicked;
	}

	public static void btnYes_Clicked()
	{
		stage.close();
		btnYesClicked = true;
	}

	public static void btnNo_Clicked()
	{
		stage.close();
		btnYesClicked = false;
	}
}
