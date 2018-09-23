package nl.pameijer.ictacademie.rasp.view.inputstudent.fxml;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Student;
import nl.pameijer.ictacademie.rasp.view.inputstudent.TableDates;
import javafx.scene.control.TableColumn;

/**
 * Presence Profile controller works with predefined components from the fxml
 * view.
 *
 * @author ben
 *
 */

public class PresenceProfileController implements Initializable {

	/*
	 * FXML Fields ---------------
	 */

	// Ten ComboBoxes for the corresponding dayparts of the week from
	// cbMoMorning till cbFriAfternoon.
	@FXML
	private ComboBox<String> cbMoMorning;
	@FXML
	private ComboBox<String> cbMoAfternoon;
	@FXML
	private ComboBox<String> cbTueMorning;
	@FXML
	private ComboBox<String> cbTueAfternoon;
	@FXML
	private ComboBox<String> cbWedMorning;
	@FXML
	private ComboBox<String> cbWedAfternoon;
	@FXML
	private ComboBox<String> cbThuMorning;
	@FXML
	private ComboBox<String> cbThuAfternoon;
	@FXML
	private ComboBox<String> cbFriMorning;
	@FXML
	private ComboBox<String> cbFriAfternoon;

	// TableView tableview with TableColumns
	@FXML
	private TableView<Student> tableView;
	@FXML
	private TableColumn<Student, String> firstname;
	@FXML
	private TableColumn<Student, String> namePrefix;
	@FXML
	private TableColumn<Student, String> lastname;
	@FXML
	private TableColumn<Student, String> mondayCol, tuesdayCol, wednesdayCol, thursdayCol, fridayCol;
	@FXML
	private TableColumn<Student, String> moMorning;
	@FXML
	private TableColumn<Student, String> tueMorning;
	@FXML
	private TableColumn<Student, String> wedMorning;
	@FXML
	private TableColumn<Student, String> thMorning;
	@FXML
	private TableColumn<Student, String> friMorning;
	@FXML
	private TableColumn<Student, String> moAfternoon;
	@FXML
	private TableColumn<Student, String> tueAfternoon;
	@FXML
	private TableColumn<Student, String> wedAfternoon;
	@FXML
	private TableColumn<Student, String> thAfternoon;
	@FXML
	private TableColumn<Student, String> friAfternoon;

	// Buttons save and change profile
	@FXML
	private Button btnSave;
	@FXML
	private Button btnChangeProfile;

	// Label for the statusbar
	@FXML
	private Label lblStatusBar;

	// Fields
	private Model model;
	private Student currentStudent;
	private TableDatesProperties tableDatesProperties;
	private Boolean edit = false;
	private DayPartComboBoxListener myComboListener01, myComboListener02, myComboListener03, myComboListener04,
			myComboListener05, myComboListener06, myComboListener08, myComboListener07, myComboListener09,
			myComboListener10;
	private int currentRow;

	/**
	 * Initialize fields set date,column headers, columns, alignment of
	 * columns, fill TableView, set items ComboBoxes.
	 *
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// model
		model = new Model();
		model.loadDataWithScheduleAndID();

		// set weektime
		LocalDate weekDays[] = TableDates.getThisWeekDates();
		tableDatesProperties = new TableDatesProperties();
		setDates();

		// binding column-name with corresponding date
		mondayCol.textProperty().bind(tableDatesProperties.monProperty());
		tuesdayCol.textProperty().bind(tableDatesProperties.tueProperty());
		wednesdayCol.textProperty().bind(tableDatesProperties.wedProperty());
		thursdayCol.textProperty().bind(tableDatesProperties.thuProperty());
		fridayCol.textProperty().bind(tableDatesProperties.friProperty());

		// set columns
		firstname.setCellValueFactory(new PropertyValueFactory<Student, String>("fName"));
		namePrefix.setCellValueFactory(new PropertyValueFactory<Student, String>("namePrefix"));
		lastname.setCellValueFactory(new PropertyValueFactory<Student, String>("lName"));
		moMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("monMorning"));
		moAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("monAfternoon"));
		tueMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("tuesMorning"));
		tueAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("tuesAfternoon"));
		wedMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("wedMorning"));
		wedAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("wedAfternoon"));
		thMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("thursMorning"));
		thAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("thursAfternoon"));
		friMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("friMorning"));
		friAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("friAfternoon"));

		// set alignment columns
		String value = "-fx-alignment: CENTER;";
		moMorning.setStyle(value);
		moAfternoon.setStyle(value);
		tueMorning.setStyle(value);
		tueAfternoon.setStyle(value);
		wedMorning.setStyle(value);
		wedAfternoon.setStyle(value);
		thMorning.setStyle(value);
		thAfternoon.setStyle(value);
		friMorning.setStyle(value);
		friAfternoon.setStyle(value);

		// fill table with students objects
		tableView.setItems(model.getStudentList());

		// set available places to comboboxes
		cbMoMorning.setItems(model.getAvailablePlaces(weekDays[0], DayPart.MONDAY_MORNING));
		cbMoAfternoon.setItems(model.getAvailablePlaces(weekDays[0], DayPart.MONDAY_AFTERNOON));
		cbTueMorning.setItems(model.getAvailablePlaces(weekDays[1], DayPart.TUESDAY_MORNING));
		cbTueAfternoon.setItems(model.getAvailablePlaces(weekDays[1], DayPart.TUESDAY_AFTERNOON));
		cbWedMorning.setItems(model.getAvailablePlaces(weekDays[2], DayPart.WEDNESDAY_MORNING));
		cbWedAfternoon.setItems(model.getAvailablePlaces(weekDays[2], DayPart.WEDNESDAY_AFTERNOON));
		cbThuMorning.setItems(model.getAvailablePlaces(weekDays[3], DayPart.THURSDAY_MORNING));
		cbThuAfternoon.setItems(model.getAvailablePlaces(weekDays[3], DayPart.THURSDAY_AFTERNOON));
		cbFriMorning.setItems(model.getAvailablePlaces(weekDays[4], DayPart.FRIDAY_MORNING));
		cbFriAfternoon.setItems(model.getAvailablePlaces(weekDays[4], DayPart.FRIDAY_AFTERNOON));

		// student selection
		tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (!edit) {
				// binding tabelview to comboboxes of current student
				cbMoMorning.valueProperty().bind(observable.getValue().monMorningProperty());
				cbMoAfternoon.valueProperty().bind(observable.getValue().monAfternoonProperty());
				cbTueMorning.valueProperty().bind(observable.getValue().tuesMorningProperty());
				cbTueAfternoon.valueProperty().bind(observable.getValue().tuesAfternoonProperty());
				cbWedMorning.valueProperty().bind(observable.getValue().wedMorningProperty());
				cbWedAfternoon.valueProperty().bind(observable.getValue().wedAfternoonProperty());
				cbThuMorning.valueProperty().bind(observable.getValue().thursMorningProperty());
				cbThuAfternoon.valueProperty().bind(observable.getValue().thursAfternoonProperty());
				cbFriMorning.valueProperty().bind(observable.getValue().friMorningProperty());
				cbFriAfternoon.valueProperty().bind(observable.getValue().friAfternoonProperty());

			} else {
				// keep selection on row
				System.out.println("edit in change listener");
				tableView.getSelectionModel().select(currentRow);
				cbMoMorning.requestFocus();
			}

		});
		// hide save button
		btnSave.setVisible(false);

		// selection of first row
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tableView.requestFocus();
				tableView.getSelectionModel().select(0);
				tableView.getFocusModel().focus(0);
			}
		});

	}

	/**
	 * OnAction method of next week button
	 */
	public void nextWeek() {
		TableDates.changeWeek(1);
		setDates();

	}

	/**
	 * OnAction method of last week button
	 */
	public void lastWeek() {
		TableDates.changeWeek(-1);
		setDates();

	}

	/**
	 * OnAction method of save button
	 */
	public void save() {
		System.out.println("save profile");
		System.out.println("wijzigen vanaf datum " + tableDatesProperties.getMon());
		System.out.println(currentStudent.getFName() + " " + currentStudent.getLName() + " "
				+ currentStudent.getMonMorning() + " " + currentStudent.getMonAfternoon() + " "
				+ currentStudent.getTuesMorning() + " " + currentStudent.getTuesAfternoon() + " "
				+ currentStudent.getWedMorning() + " " + currentStudent.getWedAfternoon() + " "
				+ currentStudent.getThursMorning() + " " + currentStudent.getThursAfternoon() + " "
				+ currentStudent.getFriMorning() + " " + currentStudent.getFriAfternoon());

		changeProfile();// to set edit off
		btnSave.setVisible(false);
	}

	/**
	 * OnAction method for change profile button
	 */
	public void changeProfile() {
		// TODO split method in two separate methods on for editing mode and one
		// for view mode
		edit = !edit;
		if (edit) {
			System.out.println("Editing mode");
			// set style for editing
			tableView.setStyle(" -fx-selection-bar-non-focused: red;");// -fx-selection-bar:
																		// red;
			// current student to statusbar
			currentStudent = (Student) tableView.getSelectionModel().getSelectedItem();
			if (currentStudent != null) {
				if (currentStudent.getNamePrefix() == null) {
					lblStatusBar
							.setText("Wijzigen van :  " + currentStudent.getFName() + " " + currentStudent.getLName());
				} else {
					lblStatusBar.setText("Wijzigen van :  " + currentStudent.getFName() + " "
							+ currentStudent.getNamePrefix() + " " + currentStudent.getLName());
				}
			}
			btnChangeProfile.setDisable(true);
			btnSave.setVisible(true);

			currentRow = tableView.getSelectionModel().getSelectedIndex();

			// set focus to firstcombobox
			cbMoMorning.requestFocus();

			// unbind comboboxes
			cbMoMorning.valueProperty().unbind();
			cbMoAfternoon.valueProperty().unbind();
			cbTueMorning.valueProperty().unbind();
			cbTueAfternoon.valueProperty().unbind();
			cbWedMorning.valueProperty().unbind();
			cbWedAfternoon.valueProperty().unbind();
			cbThuMorning.valueProperty().unbind();
			cbThuAfternoon.valueProperty().unbind();
			cbFriMorning.valueProperty().unbind();
			cbFriAfternoon.valueProperty().unbind();

			// add changelistener to comboboxes
			cbMoMorning.valueProperty()
					.addListener(myComboListener01 = new DayPartComboBoxListener(cbMoMorning.getId()));
			cbMoAfternoon.valueProperty()
					.addListener(myComboListener02 = new DayPartComboBoxListener(cbMoAfternoon.getId()));
			cbTueMorning.valueProperty()
					.addListener(myComboListener03 = new DayPartComboBoxListener(cbTueMorning.getId()));
			cbTueAfternoon.valueProperty()
					.addListener(myComboListener04 = new DayPartComboBoxListener(cbTueAfternoon.getId()));
			cbWedMorning.valueProperty()
					.addListener(myComboListener05 = new DayPartComboBoxListener(cbWedMorning.getId()));
			cbWedAfternoon.valueProperty()
					.addListener(myComboListener06 = new DayPartComboBoxListener(cbWedAfternoon.getId()));
			cbThuMorning.valueProperty()
					.addListener(myComboListener07 = new DayPartComboBoxListener(cbThuMorning.getId()));
			cbThuAfternoon.valueProperty()
					.addListener(myComboListener08 = new DayPartComboBoxListener(cbThuAfternoon.getId()));
			cbFriMorning.valueProperty()
					.addListener(myComboListener09 = new DayPartComboBoxListener(cbFriMorning.getId()));
			cbFriAfternoon.valueProperty()
					.addListener(myComboListener10 = new DayPartComboBoxListener(cbFriAfternoon.getId()));
		} else {
			System.out.println("View mode");
			tableView.setStyle(null);
			btnChangeProfile.setDisable(false);
			lblStatusBar.textProperty().setValue("");
			if (lblStatusBar.textProperty().isBound()) {
				lblStatusBar.textProperty().unbind();
			}

			// remove listeners
			cbMoMorning.valueProperty().removeListener(myComboListener01);
			cbMoAfternoon.valueProperty().removeListener(myComboListener02);
			cbTueMorning.valueProperty().removeListener(myComboListener03);
			cbTueAfternoon.valueProperty().removeListener(myComboListener04);
			cbWedMorning.valueProperty().removeListener(myComboListener05);
			cbWedAfternoon.valueProperty().removeListener(myComboListener06);
			cbThuMorning.valueProperty().removeListener(myComboListener07);
			cbThuAfternoon.valueProperty().removeListener(myComboListener08);
			cbFriMorning.valueProperty().removeListener(myComboListener09);
			cbFriAfternoon.valueProperty().removeListener(myComboListener10);

		}
	}

	/**
	 * Update day tablecolumns with date after the week is changed
	 */
	public void setDates() {
		tableDatesProperties.setMon(TableDates.thisWeekStrings[0]);
		tableDatesProperties.setTue(TableDates.thisWeekStrings[1]);
		tableDatesProperties.setWed(TableDates.thisWeekStrings[2]);
		tableDatesProperties.setThu(TableDates.thisWeekStrings[3]);
		tableDatesProperties.setFri(TableDates.thisWeekStrings[4]);
		for (Student student : model.getStudentList()) {
			student.setDayPartProperties(TableDates.getThisWeekDates());
		}
	}

	/**
	 * Inner class for ComboBox listeners
	 *
	 */
	class DayPartComboBoxListener implements ChangeListener<String> {
		private String id;

		public DayPartComboBoxListener(String id) {

			this.id = id;

		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

			if (currentStudent != null) {
				switch (id) {
				case "cbMoMorning":
					currentStudent.setMonMorning(newValue);
					break;
				case "cbMoAfternoon":
					currentStudent.setMonAfternoon(newValue);
					break;
				case "cbTueMorning":
					currentStudent.setTuesMorning(newValue);
					break;
				case "cbTueAfternoon":
					currentStudent.setTuesAfternoon(newValue);
					break;
				case "cbWedMorning":
					currentStudent.setWedMorning(newValue);
					break;
				case "cbWedAfternoon":
					currentStudent.setWedAfternoon(newValue);
					break;
				case "cbThuMorning":
					currentStudent.setThursMorning(newValue);
					break;
				case "cbThuAfternoon":
					currentStudent.setThursAfternoon(newValue);
					break;
				case "cbFriMorning":
					currentStudent.setFriMorning(newValue);
					break;
				case "cbFriAfternoon":
					currentStudent.setFriAfternoon(newValue);
					break;
				default:
					break;
				}// end switch
			}

		}

	}
}
