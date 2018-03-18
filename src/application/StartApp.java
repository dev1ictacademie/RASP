package application;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.view.month.MonthInputController;
import nl.pameijer.ictacademie.rasp.view.month.MonthInputView;

public class StartApp{

	public static void main(String[] args) {
		Application.launch(MonthInputController.class,args);
	}

}
