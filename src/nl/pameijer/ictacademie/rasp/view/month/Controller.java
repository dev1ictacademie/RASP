package nl.pameijer.ictacademie.rasp.view.month;

import java.time.LocalDate;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nl.pameijer.ictacademie.rasp.model.Model;

/**
 * Controller
 *
 * @author ben
 *
 */
public class Controller {

	private MonthInputView view;
	private Model model;
	private DateModel dateModel;
	private DayPartsListener dayPartsListener;
	private TextFocusListener textFocusListener;

	public Controller(Model model) {
		// this month
		LocalDate date = LocalDate.now();
		dateModel = new DateModel(date.getYear(), date.getMonthValue());
		// model
		this.model = model;
		model.loadDataWithScheduleAndID();
		// view
		view = new MonthInputView(dateModel);

		// set Listeners
		MyChangeListener myChangeListener = new MyChangeListener();
		dateModel.yearProperty().addListener(myChangeListener);
		dateModel.monthProperty().addListener(myChangeListener);
		dayPartsListener = new DayPartsListener();
		view.addDayPartsListener(dayPartsListener);
		textFocusListener = new TextFocusListener();
		view.addTextFocusListener(textFocusListener);
		view.addKeyListener(new KeyListener());

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
	 * values of the TextFields representing the presence status of students for
	 * a certain DayPart. It also checks for valid/invalid input, changing valid
	 * input letters to upper case and can trigger a save to the database
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

			if (newValue.equals("x") || newValue.equals("z") || newValue.equals("v") || newValue.equals("a")) {

				((StringProperty) observable).setValue(newValue.toString().toUpperCase());
			} else if (newValue.equals("") || newValue.equals("X") || newValue.equals("Z") || newValue.equals("V")
					|| newValue.equals("A")) {

				if (oldValue.equals("X") || oldValue.equals("Z") || oldValue.equals("V") || oldValue.equals("A")
						|| oldValue.equals("x") || oldValue.equals("z") || oldValue.equals("v") || oldValue.equals("a")
						|| oldValue.equals("")) {
					// Saving to database
					model.saveDayPart(txt.getId(), txt.getText());
				}
			} else {
				// Invalid input. Setting observable back to oldValue.
				((StringProperty) observable).setValue(oldValue.toString());
			}

		}

	}// end inner class DayPartsListener

	/**
	 * A TextFocusListener checks if a TextField is focused (selected) or not.
	 * If focused it sets the appropriate "highlighting" background color of the
	 * TextField.
	 *
	 * If not focused it reads the text in the field and then assigns the
	 * appropriate background color based on the value read since different
	 * presence-status-codes are associated with different colors.
	 *
	 * Original borders of textField (student expected or not?) should be kept
	 * as is and therefore getStyle() is used here so existing style-attributes
	 * are not reset.
	 */
	class TextFocusListener implements ChangeListener<Boolean> {

		@Override
		public void changed(ObservableValue<? extends Boolean> obs, Boolean oldValue, Boolean newValue) {

			ReadOnlyBooleanProperty observable = (ReadOnlyBooleanProperty) obs;
			TextField txt = (TextField) observable.getBean();

			if (txt.isFocused()) {
				txt.setStyle(txt.getStyle().substring(0, txt.getStyle().indexOf(";") + 1)
						+ " -fx-control-inner-background: #FAFFBD;");
			} else {
				if (txt.getText().equals("")) {
					if (txt.getStyle().substring(0, txt.getStyle().indexOf(";")).contains("red")) {
						txt.setStyle(txt.getStyle().substring(0, txt.getStyle().indexOf(";") + 1)
								+ " -fx-control-inner-background: white;");
					} else {
						txt.setStyle(txt.getStyle().substring(0, txt.getStyle().indexOf(";") + 1)
								+ " -fx-control-inner-background: #ECECEC;");
					}
				} else if (txt.getText().equals("X")) {
					txt.setStyle(txt.getStyle().substring(0, txt.getStyle().indexOf(";") + 1)
							+ " -fx-control-inner-background: #E6FFED;");
				} else if (txt.getText().equals("Z")) {
					txt.setStyle(txt.getStyle().substring(0, txt.getStyle().indexOf(";") + 1)
							+ " -fx-control-inner-background: #FFF8CC;");
				} else if (txt.getText().equals("V")) {
					txt.setStyle(txt.getStyle().substring(0, txt.getStyle().indexOf(";") + 1)
							+ " -fx-control-inner-background: #F1F8FF;");
				} else if (txt.getText().equals("A")) {
					txt.setStyle(txt.getStyle().substring(0, txt.getStyle().indexOf(";") + 1)
							+ " -fx-control-inner-background: #FFEEF0;");
				}
			}
		}

	}// end inner class TextFocusListener

	class KeyListener implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			// which textfield ?
			TextField txtBox = (TextField) event.getSource();
			// get row number from String
			String row = txtBox.getId().substring(0, 1);
			// String without user row and userid
			String subId = txtBox.getId().substring(5);
			// integer variable to hold next row
			int nextRow = 0;
			if (event.getCode() == KeyCode.DOWN) {
				System.out.println("DOWN");
				// go one row below
				nextRow = Integer.parseInt(row) + 1;
			}
			if (event.getCode() == KeyCode.UP) {
				System.out.println("UP");
				nextRow = Integer.parseInt(row) - 1;
			}
			//if there is next row what is the id of student on that row
			if(nextRow >= 0 && nextRow < model.getStudentList().size()){
				String studentid = model.getStudentList().get(nextRow).getId();
				// set lookup string
				String lookup = "#" + String.valueOf(nextRow) + "-" + studentid + subId;
				System.out.println("Lookup String :" +  lookup);
				Node nodeToFind = (Node) getView().asParent().lookup(lookup);
				if(nodeToFind != null){
					nodeToFind.requestFocus();
				}

			}

		}

	}

}// end class Controller