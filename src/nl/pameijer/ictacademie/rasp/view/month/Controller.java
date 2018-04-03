package nl.pameijer.ictacademie.rasp.view.month;

import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	public Controller(Model model) {
		// this month
		LocalDate date = LocalDate.now();
		dateModel = new DateModel(date.getYear(), date.getMonthValue());
		// model
		this.model = model;
		model.loadDataWithSchedule();
		// view
		view = new MonthInputView(dateModel);
		//setListener
		MyChangeListener myChangeListener = new MyChangeListener();
		dateModel.yearProperty().addListener(myChangeListener);
		dateModel.monthProperty().addListener(myChangeListener);
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
	}

}