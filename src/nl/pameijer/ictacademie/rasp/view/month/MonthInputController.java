package nl.pameijer.ictacademie.rasp.view.month;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.Model;

public class MonthInputController extends Application {

	private MonthInputView monthInputView;
	private Model model;
	private SimpleIntegerProperty yearProperty = new SimpleIntegerProperty();
	private SimpleIntegerProperty monthProperty = new SimpleIntegerProperty();


	@Override
	public void start(Stage primaryStage) throws Exception {
		monthInputView = new MonthInputView();
		monthInputView.start(primaryStage);
		model = new Model();
		yearProperty.bind(monthInputView.getDateModel().yearProperty());
		monthProperty.bind(monthInputView.getDateModel().monthProperty());

		setListeners();
	}

	public void setListeners() {
		yearProperty.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				System.out.println("in controller" + observable);
				monthInputView.clearRefreshHeader();
			}
		});

		monthProperty.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				System.out.println("in controller month" + monthProperty.getValue());
				monthInputView.clearRefreshHeader();
			}
		});

	}

}
