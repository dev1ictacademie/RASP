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
		model.loadDataWithSchedule();
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
	 * inner class
	 *
	 * @author hintveld
	 *
	 */
	class DayPartsListener implements ChangeListener<Object> {
		@Override
		public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
			System.out.println("DaypartListener in Controller to save data to model");
			System.out.println(observable);
			StringProperty obs = (StringProperty) observable;
			System.out.println(obs.getBean());
			TextField txt = (TextField) obs.getBean();
			System.out.println(txt.getId());
		}

	}// end inner class DayPartsListener



}// end class Controller