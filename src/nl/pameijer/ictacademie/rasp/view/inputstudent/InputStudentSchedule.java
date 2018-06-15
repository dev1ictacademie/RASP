package nl.pameijer.ictacademie.rasp.view.inputstudent;


import java.time.LocalDate;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Student;


public class InputStudentSchedule extends Application
{
	private GridPane gpBase;// base grid pane
	private GridPane gpSitPlace;// grid pane to hold sit places, day parts labels and combo boxes
	private Label lblFName, lblLName, lblPrefix , lblOverview, lblStartDate, lblEndDate;
	private TextField txtFName, txtLName, txtPrefix;
	private ArrayList<ComboBox<String>> cbSitPlace;
	private Label[] lblDay;
	private DatePicker dpStartDate, dpEndDate;
	private String days[] = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag"};
	private Model model = new Model();
	private Button addParticipant;

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
		setLabelDatePicker();;// make label and date pickers
		setLblDayLblDayPartCbSitPlace();// make combo boxes
		setTableView();// make table view

		// Add the gridPane to a scene
		Scene scene = new Scene(gpBase, 1300, 600);
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
		gpBase.add(lblOverview, 0, 5, 2, 1);
		// make text fields
		txtFName = new TextField();
		txtFName.setMinWidth(100.0);//lblDayPart = new Label[2];
		txtFName.setMaxWidth(150);
		txtLName = new TextField();
		txtLName.setMinWidth(200.0);
		txtPrefix = new TextField();
		txtPrefix.setMaxWidth(130.0);
		// add text fields to grid pane
		gpBase.add(txtFName, 1, 0, 1, 1);
		gpBase.add(txtPrefix, 1, 1, 1, 1);
		gpBase.add(txtLName, 1, 2, 1, 1);
	}// end setLabels

	/**
	 * place labels and date pickers into base grid pane
	 */
	public void setLabelDatePicker() {
		lblStartDate = new Label("Begin datum:");
		lblEndDate = new Label("Eind datum:");
		addParticipant = new Button("Toevoegen");
		addParticipant.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{


			}
		});
		addParticipant.setStyle("-fx-font: 18 arial; -fx-base: #e03e3e;");
		gpBase.add(lblStartDate, 0, 3, 1, 1);
		gpBase.add(lblEndDate, 1, 3, 1, 1);
		dpStartDate = new DatePicker();
		dpEndDate = new DatePicker();
		dpStartDate.setValue(LocalDate.now());// shows the current date from the system clock
		dpEndDate.setValue(LocalDate.now());
		gpBase.add(dpStartDate, 0, 4, 1, 1);
		gpBase.add(dpEndDate, 1, 4, 1, 1);
		gpBase.add(addParticipant, 2, 4, 1, 1);
	}// setLabelDatePicker


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
			cbSitPlace.get(i *2 + 1).setMaxWidth(100.0);
		}

		for (ComboBox<String> comboBox : cbSitPlace) {
			comboBox.setItems(model.getAvailablePlaces());
			comboBox.getSelectionModel().selectFirst();
		}

		gpBase.add(gpSitPlace, 2, 0, 3, 4);
	}// end setLblDayLblDayPartCbSitPlace

	/**
	 * Place TableView occupation of current week of students with schedules
	 */
	public void setTableView()
	{
		TableView<Student> occupation = new TableView<Student>();// create new table object
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
			        setTextsAndCombos(newValue);
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

		// infix column
		TableColumn<Student, String> colInfix = new TableColumn("Tussenv.");
		colInfix.setMinWidth(120.0);
		colInfix.setCellValueFactory(new PropertyValueFactory<Student, String>("infix"));

		// last name column
		TableColumn<Student, String> colLastName = new TableColumn<Student, String>("Achternaam");
		colLastName.setMinWidth(250.0);
		colLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lName"));

		final double COL_MON_FRI_WIDTH = 65.0;
		// monday column
		TableColumn<Student, String> colMonday = new TableColumn<Student, String>(TableDates.thisWeek[0]);
		TableColumn<Student, String> colMonMorning = new TableColumn<Student, String>("o");
		colMonMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colMonMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("monMorning"));
		TableColumn<Student, String> colMonAfternoon = new TableColumn<Student, String>("m");
		colMonAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colMonAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("monAfternoon"));
		colMonday.getColumns().addAll(colMonMorning, colMonAfternoon);

		// theusday column
		TableColumn<Student, String> colTeusday = new TableColumn<Student, String>(TableDates.thisWeek[1]);
		TableColumn<Student, String> colTeusMorning = new TableColumn<Student, String>("o");
		colTeusMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colTeusMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("teusMorning"));
		TableColumn<Student, String> colTeusAfternoon = new TableColumn<Student, String>("m");
		colTeusAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colTeusAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("teusAfternoon"));
		colTeusday.getColumns().addAll(colTeusMorning, colTeusAfternoon);

		// wednesday column
		TableColumn<Student, String> colWed = new TableColumn<Student, String>(TableDates.thisWeek[2]);
		TableColumn<Student, String> colWedMorning = new TableColumn<Student, String>("o");
		colWedMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colWedMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("wedMorning"));
		TableColumn<Student, String> colWedAfternoon = new TableColumn<Student, String>("m");
		colWedAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colWedAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("wedAfternoon"));
		colWed.getColumns().addAll(colWedMorning, colWedAfternoon);

		// theursday column
		TableColumn<Student, String> colThursday = new TableColumn<Student, String>(TableDates.thisWeek[3]);
		TableColumn<Student, String> colThursMorning = new TableColumn<Student, String>("o");
		colThursMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colThursMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("thursMorning"));
		TableColumn<Student, String> colThursAfternoon = new TableColumn<Student, String>("m");
		colThursAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colThursAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("thursAfternoon"));
		colThursday.getColumns().addAll(colThursMorning, colThursAfternoon);

		// vriday column
		TableColumn<Student, String> colVriday = new TableColumn<Student, String>(TableDates.thisWeek[4]);
		TableColumn<Student, String> colVriMorning = new TableColumn<Student, String>("o");
		colVriMorning.setMinWidth(COL_MON_FRI_WIDTH);
		colVriMorning.setCellValueFactory(new PropertyValueFactory<Student, String>("friMorning"));
		TableColumn<Student, String> colVriAfternoon = new TableColumn<Student, String>("m");
		colVriAfternoon.setMinWidth(COL_MON_FRI_WIDTH);
		colVriAfternoon.setCellValueFactory(new PropertyValueFactory<Student, String>("friAfternoon"));
		colVriday.getColumns().addAll(colVriMorning, colVriAfternoon);


		occupation.getColumns().addAll(colId, colFirstName, colInfix, colLastName, colMonday, colTeusday,
				colWed, colThursday, colVriday );

		scrollPane.setContent(occupation);

		gpBase.add(occupation, 0, 6, 5, 4);

	}// end setTableView

	/*
	 * Fills text fields and combo boxes.
	 */
	public void setTextsAndCombos( Object newValue )
	{
		Student student = (Student) newValue;
        txtFName.setText(student.getFName());
        txtPrefix.setText(student.getNamePrefix());
        txtLName.setText(student.getLName());
        cbSitPlace.get(0).setValue(student.getMonMorning());
        cbSitPlace.get(1).setValue(student.getMonAfternoon());
        cbSitPlace.get(2).setValue(student.getTeusMorning());
        cbSitPlace.get(3).setValue(student.getTeusAfternoon());
        cbSitPlace.get(4).setValue(student.getWedMorning());
        cbSitPlace.get(5).setValue(student.getWedAfternoon());
        cbSitPlace.get(6).setValue(student.getThursMorning());
        cbSitPlace.get(7).setValue(student.getThursAfternoon());
        cbSitPlace.get(8).setValue(student.getFriMorning());
        cbSitPlace.get(9).setValue(student.getFriAfternoon());
	}// end method setTextsAndCombos



}// InputStudentSchedule
