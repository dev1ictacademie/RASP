package nl.pameijer.ictacademie.rasp.view.inputstudent;





import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Displays form to input or select email address.
 * Then you can send it to that email address.
 *
 * @author hintveld
 *
 */
public class Print
{
	private static Stage stage;


	/**
	 * Show form to add or select an email address by user
	 */
	public static void show()
	{
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Print overzicht");
		stage.setMinWidth(400.0);
		stage.setMinHeight(200.0);

		// positioning the window
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX( screenBounds.getMaxX() / 2 - screenBounds.getMaxX() / 6 );
		stage.setY( screenBounds.getMaxY() / 2 - screenBounds.getMaxY() / 6 );

		// add closing event handler for frame
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
	          public void handle(WindowEvent we)
	          {
	        	  System.out.println("sending and close Print class");
	        	  send();
	          }
		});

		ObservableList<String> options = FXCollections.observableArrayList(
				"option 1",
				"option 2",
				"option 3"
				);

		ComboBox<String> emailSelection = new ComboBox<String>();
		emailSelection.setMinWidth(300.0);
		emailSelection.setItems(options);
		emailSelection.getSelectionModel().selectFirst();

	    TextField txtEmail = new TextField();
	    txtEmail.setPromptText("Plaats hier email adres");
	    txtEmail.setMaxWidth(300.0);

		Button btnSend = new Button("Verzenden");
		btnSend.setOnAction(e -> { send(); } );

		VBox pane = new VBox(20);
		pane.getChildren().addAll(emailSelection, txtEmail, btnSend);
		pane.setAlignment(Pos.CENTER);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();

	}// end method show

	/**
	 * Send pdf to an email address and close this form
	 */
	public static void send()
	{
		// Create PDF files in Java : iText Tutorial
		//https://howtodoinjava.com/apache-commons/create-pdf-files-in-java-itext-tutorial/





		System.out.println();
		stage.close();

	}// end method send

}// end class Print
