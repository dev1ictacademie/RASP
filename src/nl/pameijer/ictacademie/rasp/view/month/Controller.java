package nl.pameijer.ictacademie.rasp.view.month;

import java.time.LocalDate;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import nl.pameijer.ictacademie.rasp.model.Model;

/**
 * Controller
 *
 * @author ben
 *
 *
 */
public class Controller {

	private MonthInputView view;
	private Model model;
	private DateModel dateModel;
	private DayPartsListener dayPartsListener;

	public Controller(Model model) {
		// this month
		LocalDate date = LocalDate.now();
		dateModel = new DateModel(date.getYear(), date.getMonthValue());
		// model
		this.model = model;
		model.loadDataWithScheduleAndID();
		// view
		view = new MonthInputView(dateModel);
		
		//setListener
		MyChangeListener myChangeListener = new MyChangeListener();
		dateModel.yearProperty().addListener(myChangeListener);
		dateModel.monthProperty().addListener(myChangeListener);
		dayPartsListener = new DayPartsListener();
		//set daypart listener in view
		view.addDayPartsListener(dayPartsListener);
		setStudents();
		setDayTextFields();

	}

	/**
	 * @return the view
	 */
	public MonthInputView getView() {
		return view;
	}

	public void setStudents() {
		view.fillStudents(model.getStudentList(), view.getGridStudents());
	}

	public void setDayTextFields() {
		view.fillDayParts(model.getStudentList(), view.getGridDays());
	}

	public void setDaysHeader() {
		view.setDaysHeader();
	}

	private class MyChangeListener implements ChangeListener {

		@Override
		public void changed(ObservableValue observable, Object oldValue, Object newValue) {

			view.clearRefreshHeader();
			setDaysHeader();
			setStudents();
			setDayTextFields();

		}
	}// end inner class MyChangeListener
	
	/**
	 * The DayPartlistener class is responsible for registering changes in the
	 * values of the TextFields representing the presence status of students
	 * for a certain DayPart. It also checks for valid/invalid input, changing
	 * valid input letters to upper case and can trigger a save to the database
	 * 
	 * But saving to the database should ONLY take place if visible changes 
	 * actually appear in the TextFields. 
	 *
	 * @author hintveld and ttimmermans
	 * @version 17-04-2018
	 */
	class DayPartsListener implements ChangeListener<Object> {
		@Override
		public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {

			StringProperty obs = (StringProperty) observable;
			TextField txt = (TextField) obs.getBean();

			if (newValue.equals("x") || newValue.equals("z")
					|| newValue.equals("v") || newValue.equals("a")) {

				((StringProperty)observable).setValue(newValue.toString().toUpperCase());
			}
			else if (newValue.equals("") || newValue.equals("X")
					|| newValue.equals("Z") || newValue.equals("V")
					|| newValue.equals("A")) {

				if (oldValue.equals("X") || oldValue.equals("Z")
						|| oldValue.equals("V") || oldValue.equals("A")
						|| oldValue.equals("x") || oldValue.equals("z")
						|| oldValue.equals("v") || oldValue.equals("a")
						|| oldValue.equals("")) {
					// Saving to database
					model.saveDayPart(txt.getId(), txt.getText());
				}
			}	
			else {
				// Invalid input. Setting observable back to oldValue.
				((StringProperty)observable).setValue(oldValue.toString());
			}

		}

	}// end inner class DayPartsListener

	
	
}// end class Controller