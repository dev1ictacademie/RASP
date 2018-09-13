package nl.pameijer.ictacademie.rasp.view.inputstudent.fxml;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.sun.rowset.RowSetFactoryImpl;

import javafx.beans.binding.BooleanBinding;
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

public class TableViewComboboxBindingController implements Initializable {

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
	@FXML
	private Button btnSave;
	@FXML
	private Button btnChangeProfile;
	@FXML
	private Label lblStatusBar;

	private Model model;
	private Student currentStudent;
	private TableDatesProperties tableDatesProperties;
	private Boolean edit = false;
	private TableViewSelectionModel<Student> defaultSelectionModel;
	private MyComboBoxListener myComboListener01;
	private MyComboBoxListener myComboListener02;
	private MyComboBoxListener myComboListener03;
	private MyComboBoxListener myComboListener04;
	private MyComboBoxListener myComboListener05;
	private MyComboBoxListener myComboListener06;
	private MyComboBoxListener myComboListener08;
	private MyComboBoxListener myComboListener07;
	private MyComboBoxListener myComboListener09;
	private MyComboBoxListener myComboListener10;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initializing ...");

		// model
		model = new Model();
		model.loadDataWithScheduleAndID();

		// set weektime
		LocalDate weekDays[] = TableDates.getThisWeekDates();
		tableDatesProperties = new TableDatesProperties();
		setDates();

		// binding column-name with corresponding date
		mondayCol.textProperty().bind(tableDatesProperties.day1Property());
		tuesdayCol.textProperty().bind(tableDatesProperties.day2Property());
		wednesdayCol.textProperty().bind(tableDatesProperties.day3Property());
		thursdayCol.textProperty().bind(tableDatesProperties.day4Property());
		fridayCol.textProperty().bind(tableDatesProperties.day5Property());

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

		// fill table
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
			}

		});
		// save current selectionmodel
		defaultSelectionModel = tableView.getSelectionModel();
		btnSave.setVisible(false);

	}

	// button next week
	public void nextWeek() {
		TableDates.changeWeek(1);
		setDates();

	}

	// button last week
	public void lastWeek() {
		TableDates.changeWeek(-1);
		setDates();

	}

	// button safe
	public void save() {
		System.out.println("save profile");
		System.out.println("wijzigen vanaf datum " + tableDatesProperties.getDay1());
		System.out.println(currentStudent.getFName() + " " + currentStudent.getLName() + " "
				+ currentStudent.getMonMorning()+ " " + currentStudent.getMonAfternoon()+ " "
				+ currentStudent.getTuesMorning() + " " + currentStudent.getTuesAfternoon()+ " "
				+ currentStudent.getWedMorning()+ " " + currentStudent.getWedAfternoon()+ " "
				+ currentStudent.getThursMorning() + " " + currentStudent.getThursAfternoon()+ " "
				+ currentStudent.getFriMorning()+ " " + currentStudent.getFriAfternoon());

		tableView.setSelectionModel(defaultSelectionModel);


		changeProfile();// to set edit off
		btnSave.setVisible(false);
	}

	// button change profile
	public void changeProfile() {

		edit = !edit;
		System.out.println("edit = " + edit);
		System.out.println("Editing....");
		if (edit) {
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
			btnChangeProfile.setVisible(false);
			btnSave.setVisible(true);
			// disable selection in table
			tableView.setSelectionModel(null);

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
			cbMoMorning.valueProperty().addListener(myComboListener01 = new MyComboBoxListener(cbMoMorning.getId()));
			cbMoAfternoon.valueProperty().addListener(myComboListener02 = new MyComboBoxListener(cbMoAfternoon.getId()));
			cbTueMorning.valueProperty().addListener(myComboListener03 = new MyComboBoxListener(cbTueMorning.getId()));
			cbTueAfternoon.valueProperty()
					.addListener(myComboListener04 = new MyComboBoxListener(cbTueAfternoon.getId()));
			cbWedMorning.valueProperty().addListener(myComboListener05 = new MyComboBoxListener(cbWedMorning.getId()));
			cbWedAfternoon.valueProperty()
					.addListener(myComboListener06 = new MyComboBoxListener(cbWedAfternoon.getId()));
			cbThuMorning.valueProperty().addListener(myComboListener07 = new MyComboBoxListener(cbThuMorning.getId()));
			cbThuAfternoon.valueProperty()
					.addListener(myComboListener08 = new MyComboBoxListener(cbThuAfternoon.getId()));
			cbFriMorning.valueProperty().addListener(myComboListener09 = new MyComboBoxListener(cbFriMorning.getId()));
			cbFriAfternoon.valueProperty()
					.addListener(myComboListener10 = new MyComboBoxListener(cbFriAfternoon.getId()));
		} else {
			btnChangeProfile.setVisible(true);
			lblStatusBar.textProperty().setValue("");
			if (lblStatusBar.textProperty().isBound()) {
				lblStatusBar.textProperty().unbind();

				System.out.println("unbounding...");
			}
			System.out.println("Stop editing....");
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
	 * update day tablecolumns with date
	 */
	public void setDates() {
		tableDatesProperties.setDay1(TableDates.thisWeekStrings[0]);
		tableDatesProperties.setDay2(TableDates.thisWeekStrings[1]);
		tableDatesProperties.setDay3(TableDates.thisWeekStrings[2]);
		tableDatesProperties.setDay4(TableDates.thisWeekStrings[3]);
		tableDatesProperties.setDay5(TableDates.thisWeekStrings[4]);
		for (Student student : model.getStudentList()) {
			student.setDayPartProperties(TableDates.getThisWeekDates());
		}
	}

	class MyComboBoxListener implements ChangeListener<String> {
		private String id;

		public MyComboBoxListener(String id) {

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
