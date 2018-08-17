package nl.pameijer.ictacademie.rasp.view.inputstudent;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Schedule;
import nl.pameijer.ictacademie.rasp.model.Student;
import nl.pameijer.ictacademie.rasp.view.tools.ConfirmationBox;
import nl.pameijer.ictacademie.rasp.view.tools.MessageBox;


public class InputStudentSchedule extends Application
{
	private GridPane gpBase;// base grid pane
	private GridPane gpSitPlace;// grid pane to hold sit places, day parts labels and combo boxes
	private Label lblFName, lblLName, lblPrefix , lblOverview, lblStartDate, lblPhone;
	private TextField txtFName, txtLName, txtPrefix, txtPhone_1, txtPhone_2;
	private ArrayList<ComboBox<String>> cbSitPlace;
	private Label[] lblDay;
	private DatePicker dpStartDate;
	private String days[] = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag"};
	private Model model = new Model();
	private Button btnNewStudent, btnEditStudent, btnDeleteStudent, btnSaveStudent;
	TableView<Student> occupation;
	private ArrayList<TableColumn<Student, String>> weekDayColumns = new ArrayList<>();
	private Student currentStudent;
	private Student student;
	private boolean canBeSaved = false;
	private boolean isSelected = false;
	private boolean fName = false;
	private boolean lName = false;
	private boolean isNewStudent = false;

	public static void main(String[] args) {
		launch(args);
	}// end main

	/**
	 * method to start and show the primary stage
	 */
	public void start(Stage primaryStage) {
		// make the base gridPane
		gpBase = new GridPane();
		gpBase.setPadding(new Insets(30, 30, 30, 30));
		gpBase.setHgap(10);
		gpBase.setVgap(20);

		setLabelsTextFields();// make labels
		setLabels_Buttons();// make label and date picker
		setLblDayLblDayPartCbSitPlace();// make combo boxes
		setTableView();// make table view

		setButtonBar(); // Right now (16-06-2018) layout not correct at all!
		setTextFieldsDisabled();
		// Add the gridPane to a scene
		Scene scene = new Scene(gpBase, 1300, 700);
		// Finalize and show the stage
		primaryStage.setScene(scene);
		primaryStage.setTitle("Overzicht bezetting");
		primaryStage.setScene(scene);
		primaryStage.show();
	}// end start

	/**
	 * method that makes labels, text fields and put them into base gridPane
	 */
	public void setLabelsTextFields() {
		// make labels
		lblFName = new Label("Voornaam:");
		lblLName = new Label("Achternaam:");
		lblPrefix = new Label("Tussenvoegsel:");
		lblOverview = new Label("Bezetting:");
		// add labels to gridPane
		gpBase.add(lblFName, 0, 0, 2, 1);
		gpBase.add(lblPrefix, 0, 1, 2, 1);
		gpBase.add(lblLName, 0, 2, 2, 1);
		gpBase.add(lblOverview, 0, 6, 2, 1);
		// make text fields
		txtFName = new TextField();
		txtFName.setMinWidth(100.0);//lblDayPart = new Label[2];
		txtFName.setMaxWidth(150);
		txtLName = new TextField();
		txtLName.setMinWidth(200.0);
		txtPrefix = new TextField();
		txtPrefix.setMaxWidth(130.0);

		// if text fields first name, last name or prefix changed then change addStudent button
		txtFName.textProperty().addListener((e, oldValue, newValue) -> addStudentButton("firstName"));
		txtLName.textProperty().addListener((e, oldValue, newValue) -> addStudentButton("lastName"));
		txtPrefix.textProperty().addListener((e, oldValue, newValue) -> addStudentButton(""));

		// add text fields to grid pane
		gpBase.add(txtFName, 1, 0, 1, 1);
		gpBase.add(txtPrefix, 1, 1, 1, 1);
		gpBase.add(txtLName, 1, 2, 1, 1);
	}// end setLabels

	/**
	 * Check if first and last name text fields are changed so it can be saved
	 */
	public void addStudentButton(String buttonName)
	{
		if(buttonName.equals("firstName"))
		{
			fName = true;
		}

		if(buttonName.equals("lastName"))
		{
			lName = true;
		}

		if(fName && lName)
		{
			canBeSaved = true;
		}

	}// end method addStudentButton

	/*
	 * Disable text fields
	 */
	public void setTextFieldsDisabled()
	{
		txtFName.setDisable(true);
		txtPrefix.setDisable(true);
		txtLName.setDisable(true);
		dpStartDate.setDisable(true);
	}

	/*
	 * Enable text fields
	 */
	public void setTextFieldsEnabled()
	{
		txtFName.setDisable(false);
		txtPrefix.setDisable(false);
		txtLName.setDisable(false);
	}

	/*
	 * Clear text fields
	 */
	public void clearTextFields()
	{
		txtFName.setText("");
		txtPrefix.setText("");
		txtLName.setText("");
	}

	/**
	 * Set combo boxes to first selection (empty)
	 */
	public void clearComboBoxes()
	{/*
		for (ComboBox<String> comboBox : cbSitPlace) {
			comboBox.setItems(model.getAvailablePlaces());
			comboBox.getSelectionModel().selectFirst();
		}*/

		/* Doesn't work yet (08-07-2018) entirely as supposed.
		 * Therefore 'old' code still available in comment above.
		 * But in a few situations this DOES already work perfectly fine!
		 * It's most likely a case of this method needing to be called
		 * a few additional times at certain places */
		for (int i = 0, j = 0; i < cbSitPlace.size(); i++) {
			ComboBox<String> currentComboBox = cbSitPlace.get(i);
			currentComboBox.setItems(model.getAvailablePlaces(TableDates.thisWeekDates[j],
					DayPart.getDayPartByNumber(i)));
			currentComboBox.getSelectionModel().selectFirst();
			System.out.println("i: " + i + "    j: " + j + "    date[j]: " +
					TableDates.thisWeekDates[j] + "    dayPart(i): " + DayPart.getDayPartByNumber(i));
			if (i % 2 == 1) {
				j++;
			}
		}
	}

	/**
	 * place labels and date pickers into base grid pane
	 */
	public void setLabels_Buttons()
	{
		Tooltip ttPhone = new Tooltip("Plaats hier eerste telefoon nummer.");
		dpStartDate = new DatePicker(LocalDate.now());
		addDateListener();
		lblStartDate = new Label("Begin datum:");
		lblPhone = new Label("Telefoon:");
		txtPhone_1 = new TextField();
		txtPhone_1.setMaxWidth(150.0);

		txtPhone_2 = new TextField();
		txtPhone_2.setMaxWidth(150.0);
		txtPhone_2.setEditable(true);
		btnNewStudent = new Button("Nieuw student");
		//btnNewStudent.setStyle("-fx-font: 18 arial; -fx-base: #72bfff;");
		btnNewStudent.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				setTextFieldsEnabled();
				clearTextFields();
				clearComboBoxes();
				dpStartDate.setDisable(false);
				fName = false;
				lName = false;
				isNewStudent = true;
			}
		});

		btnEditStudent = new Button("Bewerk student");
		//btnEditStudent.setStyle("-fx-font: 18 arial; -fx-base: #3ee086;");
		btnEditStudent.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				setTextFieldsDisabled();
				canBeSaved = false;
			}
		});

		btnDeleteStudent = new Button("Verwijder student");
		//btnDeleteStudent.setStyle("-fx-font: 18 arial; -fx-base: #e03e3e;");
		btnDeleteStudent.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				deleteStudent();
			}
		});

		btnSaveStudent = new Button("Opslaan");
		//btnSaveStudent.setStyle("-fx-font: 18 arial; -fx-base: #ff84fc;");
		btnSaveStudent.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				saveStudent();
			}

		});

		gpBase.add(lblStartDate, 0, 3, 1, 1);
		//gpBase.add(lblPhone, 2, 3, 1, 1);
		//gpBase.add(txtPhone_1, 3, 3, 1, 1);
		//gpBase.add(txtPhone_2, 4, 3, 1, 1);
		gpBase.add(dpStartDate, 0, 4, 1, 1);
		gpBase.add(btnNewStudent, 2, 4, 1, 1);
		//gpBase.add(btnEditStudent, 3, 4, 1, 1);
		gpBase.add(btnDeleteStudent, 3, 4, 1, 1);
		gpBase.add(btnSaveStudent, 4, 4, 1, 1);

	}// end method setLabels_DatePickers

	/**
	 * Delete selected student from table view occupation
	 */
	public void deleteStudent()
	{
		setTextFieldsDisabled();
		if(!isSelected)
		{
			MessageBox.show("Select eerst een student in tabele view!", "Verwijder fout.");
		}

		if(isSelected)// if student is selected in occupation table view
		{
			// delete confirmation dialog shows
			if(ConfirmationBox.show("Weet u het zeker?", "Verdwijder bevestiging", "Ja", "Nee"))
			{
				int index = occupation.getSelectionModel().getSelectedIndex();
				model.getStudentList().remove(index);
				clearTextFields();
				occupation.getSelectionModel().clearSelection();
				isSelected = false;
				MessageBox.show("Student is verwijderd.", "Verwijder bevestiging");
			}
		}
	}// end method deleteStudent

	/**
	 * Add a ChangeListener to the DatePicker that changes the current week if
	 * the newly picked date does not fall into the same week as current week.
	 */
	public void addDateListener() {
		dpStartDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable,
					LocalDate oldValue, LocalDate newValue) {

				LocalDate mostRecentMonday = TableDates.getMostRecentMonday(observable.getValue());

				// Calculate the number of weeks between the "current" mostRecentMonday and
				// the Monday which is the most recently passed one at the picked date.
				long weeksBetween = ChronoUnit.WEEKS.between(TableDates.thisWeekDates[0], mostRecentMonday);

				if (weeksBetween != 0) {
					TableDates.changeWeek((int)weeksBetween);
					updateTableView();
					clearComboBoxes();
				}
			}
		});
	}

	/*
	 * save student
	 */
	public void saveStudent()
	{
		if(canBeSaved)
		{
			if( ConfirmationBox.show("Weet u het zeker?", "Opslaan bevestiging", "Ja", "Nee") )
			{
				Student student = new Student(txtFName.getText(), txtLName.getText(),
						txtPrefix.getText(), model.generateStudentID());
				model.getStudentList().add(student);
				//String[] s = { student.getId(), dpStartDate.getValue().toString(),  };
				//student.addNewSchedule(new Schedule(getPlaces()));
				clearTextFields();
				clearComboBoxes();
			}
		}
		else
		{
			if(!isNewStudent)
			{
				if(!isSelected)
				{
					MessageBox.show("Je moet een student selecteren.", "Invoer fout");
				}
			}
			else
			{
				MessageBox.show("Je moet een voornaam en of achernaam invoeren.", "Invoer fout");
			}

		}

		canBeSaved = false;


	}// end method saveStudent


	/*
	 *
	 */
	public String[] getPlaces()
	{
		String[] places = new String[10];

		for (int i = 0; i < cbSitPlace.size(); i++)
		{
			places[i] = cbSitPlace.get(i).getValue();
		}
		return places;
	}// end method getPlaces

	/*
	 *
	 */
	class comboBoxListener implements ChangeListener<String>
	{
		int i;

		public comboBoxListener(int i)
		{
			this.i = i;

		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
		{
			if(currentStudent != null)
			{

				switch (i)
				{
					case 0:
						currentStudent.setMonMorning(newValue);
						break;
					case 1:
						currentStudent.setMonAfternoon(newValue);
						break;
					case 2:
						currentStudent.setTuesMorning(newValue);
						break;
					case 3:
						currentStudent.setTuesAfternoon(newValue);
						break;
					case 4:
						currentStudent.setWedMorning(newValue);
						break;
					case 5:
						currentStudent.setWedAfternoon(newValue);
						break;
					case 6:
						currentStudent.setThursMorning(newValue);
						break;
					case 7:
						currentStudent.setThursAfternoon(newValue);
						break;
					case 8:
						currentStudent.setFriMorning(newValue);
						break;
					case 9:
						currentStudent.setFriAfternoon(newValue);
						break;
					default:
						break;
				}// end switch
			}// end if
		}// end method changed

	}// end inner class myChangeListener

	/**
	 * Place labels days, day parts and sit place combo boxes into sitPlace GridPane
	 */
	public void setLblDayLblDayPartCbSitPlace() {
		gpSitPlace = new GridPane();
		gpSitPlace.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
		gpSitPlace.setHgap(50.0);
		lblDay = new Label[5];

		Label lblMorning = new Label("Ochtend");
		Label lblAfternoon = new Label("Middag");

		cbSitPlace = new ArrayList<ComboBox<String>>();
		int columspan = 4;

		for (int i = 0; i < days.length * 2; i++) { // create combo boxes
			cbSitPlace.add(new ComboBox<String>());
			//cbSitPlace.get(i).valueProperty().addListener( (obs, oldValue, newValue) -> System.out.println("new value" + newValue ));
			cbSitPlace.get(i).valueProperty().addListener(new comboBoxListener(i));
		}

		for (int i = 0; i < lblDay.length; i++) { // place labels day of week
			gpSitPlace.add(lblDay[i] = new Label(days[i]), i + 3, 0, 1, 1);
			lblDay[i].setMinWidth(80.0);
		}

		// place the morning and afternoon labels
		gpSitPlace.add( lblMorning, 1, 3, 4, 1);
		gpSitPlace.add( lblAfternoon, 1, 4, 4, 1);

		for (int i = 0; i < days.length ; i++) { // place combo boxes for picking sit place
			gpSitPlace.add(cbSitPlace.get(i * 2), i + 3, 3, columspan, 1);
			cbSitPlace.get(i * 2).setMaxWidth(100.0);
			gpSitPlace.add(cbSitPlace.get(i * 2 + 1), i + 3, 4, columspan, 1);
			cbSitPlace.get(i * 2 + 1).setMaxWidth(100.0);
		}

        /* Below code is exactly the same as: clearComboBoxes()  (at least as
         * it was prior to 08-07-2018 ;)
         * and therefore replaced with a call to clearComboBoxes()
        //////////
		for (ComboBox<String> comboBox : cbSitPlace) {
			comboBox.setItems(model.getAvailablePlaces());
			comboBox.getSelectionModel().selectFirst();
		//////////
		}*/

		clearComboBoxes();

		gpBase.add(gpSitPlace, 2, 0, 3, 4);
	}// end setLblDayLblDayPartCbSitPlace

	/**
	 * Place TableView occupation of current week of students with schedules
	 */

	public void setTableView()
	{
		occupation = new TableView<Student>();// create new table object
		occupation.setMaxWidth(1214.0);
		occupation.setMinHeight(300.0);
		model.loadDataWithScheduleAndID();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);

//		ObservableList<Student> students = (ObservableList<Student>) model.getStudentList();
//		for(int i = 0; i < students.size(); i++)
//		{
//			System.out.println(students.get(i).getSchedules().get(0).getDayParts());
//		}

		occupation.setItems(model.getStudentList());

		/*
		 *  table view change listener when click on a row the concerned text boxes and
		 *  combo boxes are set to new values
		 */
		occupation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
		{
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue)
			{
				if(occupation.getSelectionModel().getSelectedItem() != null)
				{
			        setTextFieldsAndComboBoxes(newValue);
			        currentStudent = (Student) newValue;
			        setTextFieldsDisabled();
			        canBeSaved = true;
			        isSelected = true;
				}
			}
		});


		TableColumn<Student, String> colId = new TableColumn("Id");
		colId.setMinWidth(20.0);
		colId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));

		// first name column
		TableColumn<Student, String> colFirstName = new TableColumn("Voornaam");
		colFirstName.setMinWidth(130.0);
		colFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("fName"));

		// name prefix column
		TableColumn<Student, String> colPrefix = new TableColumn("Tussenv.");
		colPrefix.setMinWidth(120.0);
		colPrefix.setCellValueFactory(new PropertyValueFactory<Student, String>("namePrefix"));

		// last name column
		TableColumn<Student, String> colLastName = new TableColumn<Student, String>("Achternaam");
		colLastName.setMinWidth(250.0);
		colLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lName"));

		final double COL_MON_FRI_WIDTH = 65.0;
		// monday column
		TableColumn<Student, String> colMonday = new TableColumn<Student, String>(TableDates.thisWeekStrings[0]);
		weekDayColumns.add(colMonday);
		TableColumn<Student, String> colMonMorning = new TableColumn<Student, String>("o");
		colMonMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colMonMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("monMorning"));
		TableColumn<Student, String> colMonAfternoon = new TableColumn<Student, String>("m");
		colMonAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colMonAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("monAfternoon"));
		colMonday.getColumns().addAll(colMonMorning, colMonAfternoon);

		// tuesday column
		TableColumn<Student, String> colTuesday = new TableColumn<Student, String>(TableDates.thisWeekStrings[1]);
		weekDayColumns.add(colTuesday);
		TableColumn<Student, String> colTuesMorning = new TableColumn<Student, String>("o");
		colTuesMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colTuesMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("tuesMorning"));
		TableColumn<Student, String> colTuesAfternoon = new TableColumn<Student, String>("m");
		colTuesAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colTuesAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("tuesAfternoon"));
		colTuesday.getColumns().addAll(colTuesMorning, colTuesAfternoon);

		// wednesday column
		TableColumn<Student, String> colWed = new TableColumn<Student, String>(TableDates.thisWeekStrings[2]);
		weekDayColumns.add(colWed);
		TableColumn<Student, String> colWedMorning = new TableColumn<Student, String>("o");
		colWedMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colWedMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("wedMorning"));
		TableColumn<Student, String> colWedAfternoon = new TableColumn<Student, String>("m");
		colWedAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colWedAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("wedAfternoon"));
		colWed.getColumns().addAll(colWedMorning, colWedAfternoon);

		// thursday column
		TableColumn<Student, String> colThursday = new TableColumn<Student, String>(TableDates.thisWeekStrings[3]);
		weekDayColumns.add(colThursday);
		TableColumn<Student, String> colThursMorning = new TableColumn<Student, String>("o");
		colThursMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colThursMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("thursMorning"));
		TableColumn<Student, String> colThursAfternoon = new TableColumn<Student, String>("m");
		colThursAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colThursAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("thursAfternoon"));
		colThursday.getColumns().addAll(colThursMorning, colThursAfternoon);

		// friday column
		TableColumn<Student, String> colFriday = new TableColumn<Student, String>(TableDates.thisWeekStrings[4]);
		weekDayColumns.add(colFriday);
		TableColumn<Student, String> colFriMorning = new TableColumn<Student, String>("o");
		colFriMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colFriMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("friMorning"));
		TableColumn<Student, String> colFriAfternoon = new TableColumn<Student, String>("m");
		colFriAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colFriAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("friAfternoon"));
		colFriday.getColumns().addAll(colFriMorning, colFriAfternoon);


		occupation.getColumns().addAll(colId, colFirstName, colPrefix, colLastName, colMonday, colTuesday,
				colWed, colThursday, colFriday );

		scrollPane.setContent(occupation);

		gpBase.add(occupation, 0, 8, 6, 4);


	}// end setTableView

	/**
	 * Fills text fields and combo boxes.
	 */
	public void setTextFieldsAndComboBoxes( Object newValue )
	{
		student = (Student) newValue;
        txtFName.setText(student.getFName());
        txtPrefix.setText(student.getNamePrefix());
        txtLName.setText(student.getLName());
        cbSitPlace.get(0).setValue(student.getMonMorning());
        cbSitPlace.get(1).setValue(student.getMonAfternoon());
        cbSitPlace.get(2).setValue(student.getTuesMorning());
        cbSitPlace.get(3).setValue(student.getTuesAfternoon());
        cbSitPlace.get(4).setValue(student.getWedMorning());
        cbSitPlace.get(5).setValue(student.getWedAfternoon());
        cbSitPlace.get(6).setValue(student.getThursMorning());
        cbSitPlace.get(7).setValue(student.getThursAfternoon());
        cbSitPlace.get(8).setValue(student.getFriMorning());
        cbSitPlace.get(9).setValue(student.getFriAfternoon());
	}// end method setTextsAndCombos

	/**
	 * Update combo boxes
	 */
	public void updateComboBoxes()
	{
		LocalDate weekDays[] = TableDates.getThisWeekDates();

		cbSitPlace.get(0).setItems(model.getAvailablePlaces(weekDays[0], DayPart.MONDAY_MORNING));
        cbSitPlace.get(1).setItems(model.getAvailablePlaces(weekDays[0], DayPart.MONDAY_AFTERNOON));
        cbSitPlace.get(2).setItems(model.getAvailablePlaces(weekDays[1], DayPart.TUESDAY_MORNING));
        cbSitPlace.get(3).setItems(model.getAvailablePlaces(weekDays[1], DayPart.TUESDAY_AFTERNOON));
        cbSitPlace.get(4).setItems(model.getAvailablePlaces(weekDays[2], DayPart.WEDNESDAY_MORNING));
        cbSitPlace.get(5).setItems(model.getAvailablePlaces(weekDays[2], DayPart.WEDNESDAY_AFTERNOON));
        cbSitPlace.get(6).setItems(model.getAvailablePlaces(weekDays[3], DayPart.THURSDAY_MORNING));
        cbSitPlace.get(7).setItems(model.getAvailablePlaces(weekDays[3], DayPart.THURSDAY_AFTERNOON));
        cbSitPlace.get(8).setItems(model.getAvailablePlaces(weekDays[4], DayPart.FRIDAY_MORNING));
        cbSitPlace.get(9).setItems(model.getAvailablePlaces(weekDays[4], DayPart.FRIDAY_AFTERNOON));

	}// end updateTextFieldsAndComboBoxes



	/**
	 * Three buttons: to switch to month-view, to move 1 week forward
	 * and to move 1 week backwards.
	 *
	 * Currently (18-06-2018) layout and alignment of the buttons
	 * is not correct at all! "Vorige Week" and "Volgende Week" buttons
	 * should be aligned to the far right! (Position of monthViewButton is
	 * kind of alright though).
	 *
	 * Also the buttons "steal" too much space for some reason from the
	 * TableView above.
	 *
	 * Finally, local declaration of GridPane and Buttons might be questionable.
	 */
	public void setButtonBar() {

		GridPane buttonBar = new GridPane();

		Button monthViewButton = new Button("Maand overzicht");
		monthViewButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Switching to MonthView...");
			}
		});

		Button previousWeekButton = new Button("Vorige Week");
		previousWeekButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Showing previous week!");
				TableDates.changeWeek(-1);
				updateTableView();
				updateComboBoxes();
			}
		});

		Button nextWeekButton = new Button("Volgende Week");
		nextWeekButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Showing next week!");
				TableDates.changeWeek(1);
				updateTableView();
				updateComboBoxes();
			}
		});

		buttonBar.add(monthViewButton, 0, 0, 2, 1);
		buttonBar.add(previousWeekButton, 3, 0, 2, 1);
		buttonBar.add(nextWeekButton, 6, 0, 2, 1);

		gpBase.add(buttonBar, 0, 13, 2, 1);
	}


	/**
	 * Update the TableView after a week change, after the studentList has
	 * changed or after schedules have been changed.
	 *
	 * Set the text from the TableColumn headers to the days and dates from
	 * the (new) current week.
	 *
	 * Reassign DayPartProperties to students so that the correct places are
	 * shown in the week overview.
	 */
	public void updateTableView() {

		for (int i = 0; i < weekDayColumns.size(); i++) {
			weekDayColumns.get(i).setText(TableDates.thisWeekStrings[i]);
		}

		for (Student student: model.getStudentList()) {
			student.setDayPartProperties(TableDates.thisWeekDates);
		}

	}


}// InputStudentSchedule
