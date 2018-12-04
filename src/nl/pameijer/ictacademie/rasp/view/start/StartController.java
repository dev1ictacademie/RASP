package nl.pameijer.ictacademie.rasp.view.start;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class StartController {
	private static Stage inputStudent;
	
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
}
