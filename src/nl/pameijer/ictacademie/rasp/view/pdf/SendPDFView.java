package nl.pameijer.ictacademie.rasp.view.pdf;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SendPDFView
{
	private Stage stage;
	ObservableList<String> eMailList;
	private ComboBox<String> emailSelection;
	private Button btnSaveEMailAdress;
	private TextField txtEmail;
	private Button btnSend;

	// constructor
	public SendPDFView()
	{
		stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Selecteer E-mail adres");
		stage.setMinWidth(400.0);
		stage.setMinHeight(200.0);
		stage.setMaxWidth(400.0);
		stage.setMaxHeight(200.0);

		// make text field and buttons
		txtEmail = new TextField();
		btnSaveEMailAdress = new Button("E-Mail adres opslaan");
		btnSaveEMailAdress.setDisable(true);
		btnSend = new Button("Verzenden");
		btnSend.setDisable(true);

		// positioning the window
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX( screenBounds.getMaxX() / 2 - screenBounds.getMaxX() / 6 );
		stage.setY( screenBounds.getMaxY() / 2 - screenBounds.getMaxY() / 6 );

		// add closing event handler for frame
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
	          public void handle(WindowEvent we)
	          {
	        	  send();
	        	  System.exit(0);
	          }
		});

		// Observable list that contains E-mail addresses
		eMailList = FXCollections.observableArrayList(
				"kies hier een e-mail adres",
				"Ben@pameijer.nl",
				"Wim@pameijer.nl"
				);

		// create combobox that contains email addresses
		emailSelection = new ComboBox<String>();
		emailSelection.setMinWidth(300.0);
		emailSelection.setItems(eMailList);
		emailSelection.getSelectionModel().selectFirst();
		emailSelection.setOnAction(e -> btnSend.setDisable(
				emailSelection.getSelectionModel().getSelectedIndex() != 0 ? false : true) );

		btnSaveEMailAdress.setOnAction(e -> saveEMail() );

		txtEmail.setPromptText("Plaats hier email adres");
	    txtEmail.setOnKeyReleased(e -> setTxtEmail());
	    txtEmail.setMaxWidth(300.0);

		btnSend.setOnAction(e -> { send(); } );

		HBox hPane = new HBox(30);
		hPane.setPadding(new Insets(0, 0, 0, 65));
		hPane.getChildren().addAll(btnSaveEMailAdress, btnSend);

		VBox pane = new VBox(20);
		pane.getChildren().addAll(emailSelection, txtEmail, hPane);
		pane.setAlignment(Pos.CENTER);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();

	} // end constructor SendPDFView

	// Checking txtEmail contains string and one @
	public void setTxtEmail()
	{
		btnSaveEMailAdress.setDisable(false);
		int quantityAtSign = 0;
		String email = txtEmail.getText();

		for(int i = 0; i < email.length(); i++)
		{
			if(email.charAt(i) == '@')
			{
				quantityAtSign++; // count @ in txtEmail
			}
		}

    	if(txtEmail.getText().equals("") || txtEmail.getText().equals(" ") ||
    			quantityAtSign != 1 )
    	{
    		btnSaveEMailAdress.setDisable(true); // set button save e-mail enabled or disabled
    	}

	}// end method setTxtEmail

	// save new email
	public void saveEMail()
	{
		eMailList.add(txtEmail.getText());
		btnSaveEMailAdress.setDisable(true);
		btnSend.setDisable(false);
	}// end method saveEMail

	// send PDF to an e-mail address
	public void send()
	{
		if(emailSelection.getSelectionModel().getSelectedIndex() != 0)
		{

		}


		stage.close();

	}// end method send

}
