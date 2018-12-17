package nl.pameijer.ictacademie.rasp.view.start;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.view.month.Controller;
import nl.pameijer.ictacademie.rasp.view.pdf.TravelingCostView;

public class StartController {
	private static Stage inputStudent;
	private static Stage monthView;
	private static Stage presenceProfile;
	private static Stage occupationPDF;
	private static Stage travelingCost;

	@FXML
	private void launchInputStudent() {
		if (inputStudent == null) {
			inputStudent = new Stage();
			new nl.pameijer.ictacademie.rasp.view.inputstudent.fxml.MainApp().start(inputStudent);
		} else {
			inputStudent.show();
			inputStudent.toFront();
		}
	}

	@FXML
	private void monthView(){
		if (monthView == null) {
			monthView = new Stage();
			try {
				new nl.pameijer.ictacademie.rasp.view.month.StartApp().start(monthView);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			monthView.show();
			monthView.toFront();
		}

	}
	@FXML
	private void presenceProfile(){
		if(presenceProfile== null){
			presenceProfile = new Stage();
			try {
				new nl.pameijer.ictacademie.rasp.view.inputstudent.fxml.PresenceProfile().start(presenceProfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			presenceProfile.show();
			presenceProfile.toFront();
		}

	}
	@FXML
	private void occupationPDF(){
		if(occupationPDF== null){
			occupationPDF = new Stage();

				new nl.pameijer.ictacademie.rasp.view.pdf.StartSendPDFView().start(occupationPDF);

		}else {
			occupationPDF.show();
			occupationPDF.toBack();
		}

	}

	@FXML
	private void onTravelCost() {
		if(travelingCost == null){
			travelingCost = new Stage();
			new nl.pameijer.ictacademie.rasp.view.pdf.TravelingCostView().start(travelingCost);
		}
	}

}// end class StartController
