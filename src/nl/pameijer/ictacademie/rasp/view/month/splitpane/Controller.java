package nl.pameijer.ictacademie.rasp.view.month.splitpane;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
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
public class Controller  {

	private MonthInputView view;
	private Model model;
	private DateModel dateModel;
	private SimpleIntegerProperty yearProperty = new SimpleIntegerProperty();
	private SimpleIntegerProperty monthProperty = new SimpleIntegerProperty();


	public Controller(Model model){
		//this month
		LocalDate date =  LocalDate.now();
		dateModel = new DateModel(date.getYear(), date.getMonthValue());
		//model
		this.model = model;
		model.loadData();
		//view
		view = new MonthInputView(dateModel);
		//FIXME sometimes loses the connection/bind maybe garbage collected
		yearProperty.bind(dateModel.yearProperty());
		MyChangeListener myChangeListener = new MyChangeListener();
		yearProperty.addListener(myChangeListener);
		monthProperty.bind(dateModel.monthProperty());
		monthProperty.addListener(myChangeListener);
		//setListeners();
		setStudents();
		setDayTextFields();
	}

	public void setListeners(){
		yearProperty.addListener( (observable , oldvalue , newvalue) -> {
			System.out.println(" In controller year" +yearProperty.getValue());
			view.clearRefreshHeader();
			setStudents();
			setDayTextFields();
		});
		monthProperty.addListener( (observable , oldvalue , newvalue) -> {
			System.out.println("in controller month" + monthProperty.getValue());
			view.clearRefreshHeader();
			setStudents();
			setDayTextFields();
		});
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
	
	private class MyChangeListener implements ChangeListener
	{

		@Override
		public void changed(ObservableValue observable, Object oldValue, Object newValue) {
			System.out.println(" In controller " + newValue);
			view.clearRefreshHeader();
			setStudents();
			setDayTextFields();
		}
	}
}
