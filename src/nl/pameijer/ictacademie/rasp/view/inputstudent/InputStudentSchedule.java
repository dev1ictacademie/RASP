package nl.pameijer.ictacademie.rasp.view.inputstudent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;

import conceptmodel.Schedule;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Place;
import nl.pameijer.ictacademie.rasp.model.Student;
import sun.launcher.resources.launcher;

public class InputStudentSchedule extends Application
{
	private GridPane gpBase;// base grid pane
	private GridPane gpSitPlace;// grid pane to hold sit places, day parts labels and combo boxes
	private Label lblFName, lblLName, lblOverview, lblStartDate, lblEndDate;
	private TextField txtFName, txtLName;
	private ArrayList<ComboBox<String>> cbSitPlace;
	private Label[] lblDay, lblDayPart;
	private DatePicker dpStartDate, dpEndDate;
	private String days[] = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag"};
	private Model model = new Model();

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
		lblOverview = new Label("Bezetting:");
		// add labels to gridPane
		gpBase.add(lblFName, 0, 0, 2, 1);
		gpBase.add(lblLName, 0, 1, 2, 1);
		gpBase.add(lblOverview, 0, 5);
		// make text fields
		txtFName = new TextField();
		txtFName.setMinWidth(100.0);
		txtFName.setMaxWidth(150);
		txtLName = new TextField();
		txtLName.setMinWidth(200.0);
		// add text fields to grid pane
		gpBase.add(txtFName, 1, 0, 1, 1);
		gpBase.add(txtLName, 1, 1, 1, 1);

	}// end setLabels

	/**
	 * place labels and date pickers into base grid pane
	 */
	public void setLabelDatePicker() {
		lblStartDate = new Label("Begin datum:");
		lblEndDate = new Label("Eind datum:");
		gpBase.add(lblStartDate, 0, 3, 1, 1);
		gpBase.add(lblEndDate, 1, 3, 1, 1);
		dpStartDate = new DatePicker();
		dpEndDate = new DatePicker();
		dpStartDate.setValue(LocalDate.now());// shows the current date from the system clock
		dpEndDate.setValue(LocalDate.now());
		gpBase.add(dpStartDate, 0, 4, 1, 1);
		gpBase.add(dpEndDate, 1, 4, 1, 1);
	}// setLabelDatePicker

	/**
	 * Place labels days, day parts and sit place combo boxes into sitPlace GridPane
	 */
	public void setLblDayLblDayPartCbSitPlace() {
		gpSitPlace = new GridPane();
		gpSitPlace.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
		gpSitPlace.setHgap(50.0);
		lblDay = new Label[5];
		lblDayPart = new Label[2];
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
	 * Place TableView occupation of students with schedules
	 */
	public void setTableView() {
		TableView<Student> occupation = new TableView<Student>();// create new table object
		model.loadDataWithSchedule();

		/*
		ObservableList<Student> students = (ObservableList<Student>) model.getStudentList();
		for(int i = 0; i < students.size(); i++)
		{
			System.out.println(students.get(i).getSchedules().get(0).getDayParts());
		}
		*/

		occupation.setItems(model.getStudentList());

		// first name column
		TableColumn<Student, String> colFirstName = new TableColumn("Voornaam");
		colFirstName.setMinWidth(150.0);
		colFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("fName"));

		// infix column
		TableColumn<Student, String> colInfix = new TableColumn("Tussenv.");
		colInfix.setMinWidth(120.0);
		colInfix.setCellValueFactory(new PropertyValueFactory<Student, String>(""));

		// last name column
		TableColumn<Student, String> colLastName = new TableColumn<Student, String>("Achternaam");
		colLastName.setMinWidth(250.0);
		colLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lName"));

		// day of week columns
		TableColumn<Student, String> colMonday = new TableColumn<Student, String>("Ma");
		colMonday.setCellValueFactory(new PropertyValueFactory<Student, String>(""));
		TableColumn<Student, String> colMonMorning = new TableColumn<Student, String>("o");
		colMonMorning.setMinWidth(70.0);
		TableColumn<Student, String> colMonAfternoon = new TableColumn<Student, String>("m");
		colMonAfternoon.setMinWidth(70.0);
		colMonday.getColumns().addAll(colMonMorning, colMonAfternoon);

		TableColumn<Student, String> colTeusday = new TableColumn<Student, String>("Di");
		colTeusday.setCellValueFactory(new PropertyValueFactory<Student, String>(""));
		TableColumn<Student, String> colTeusMorning = new TableColumn<Student, String>("o");
		colTeusMorning.setMinWidth(70.0);
		TableColumn<Student, String> colTeusAfternoon = new TableColumn<Student, String>("m");
		colTeusAfternoon.setMinWidth(70.0);
		colTeusday.getColumns().addAll(colTeusMorning, colTeusAfternoon);

		TableColumn<Student, String> colWed = new TableColumn<Student, String>("Wo");
		colMonday.setCellValueFactory(new PropertyValueFactory<Student, String>(""));
		TableColumn<Student, String> colWedMorning = new TableColumn<Student, String>("o");
		colWedMorning.setMinWidth(70.0);
		TableColumn<Student, String> colWedAfternoon = new TableColumn<Student, String>("m");
		colWedAfternoon.setMinWidth(70.0);
		colWed.getColumns().addAll(colWedMorning, colWedAfternoon);

		TableColumn<Student, String> colThursday = new TableColumn<Student, String>("Do");
		colMonday.setCellValueFactory(new PropertyValueFactory<Student, String>(""));
		TableColumn<Student, String> colThursMorning = new TableColumn<Student, String>("o");
		colThursMorning.setMinWidth(70.0);
		TableColumn<Student, String> colThursAfternoon = new TableColumn<Student, String>("m");
		colThursAfternoon.setMinWidth(70.0);
		colThursday.getColumns().addAll(colThursMorning, colThursAfternoon);

		TableColumn<Student, String> colVriday = new TableColumn<Student, String>("Vrij");
		colMonday.setCellValueFactory(new PropertyValueFactory<Student, String>(""));
		TableColumn<Student, String> colVriMorning = new TableColumn<Student, String>("o");
		colVriMorning.setMinWidth(70.0);
		TableColumn<Student, String> colVriAfternoon = new TableColumn<Student, String>("m");
		colVriAfternoon.setMinWidth(70.0);
		colVriday.getColumns().addAll(colVriMorning, colVriAfternoon);


		occupation.getColumns().addAll(colFirstName, colInfix, colLastName, colMonday, colTeusday,
				colWed, colThursday, colVriday );

		gpBase.add(occupation, 0, 6, 5, 4);

	}// end setTableView













}// InputStudentSchedule
