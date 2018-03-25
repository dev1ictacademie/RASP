package nl.pameijer.ictacademie.rasp.view.buildingblocks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.Model;

public class StartApp extends Application{

	public static void main(String[] args) {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Model model = new Model();
        MonthInputView view = new MonthInputView();
        Controller controller = new Controller(model , view);

		Scene scene = new Scene(view.asParent(), 400, 400);
	        primaryStage.setScene(scene);
	        primaryStage.show();



	}

}
