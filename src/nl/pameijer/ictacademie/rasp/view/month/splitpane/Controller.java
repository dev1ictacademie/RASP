package nl.pameijer.ictacademie.rasp.view.month.splitpane;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
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
		yearProperty.bind(dateModel.yearProperty());
		monthProperty.bind(dateModel.monthProperty());
		setListeners();
		setStudents();
		setDayTextFields();
	}

	public void setListeners(){
		yearProperty.addListener( (observable , oldvalue , newvalue) -> {
			System.out.println(" In controller year");
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

}
