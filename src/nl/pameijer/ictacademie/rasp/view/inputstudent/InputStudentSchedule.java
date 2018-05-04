package nl.pameijer.ictacademie.rasp.view.inputstudent;

import java.time.LocalDate;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;

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
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Student;
import sun.launcher.resources.launcher;

public class InputStudentSchedule extends Application{
	private GridPane gpBase;// base grid pane
	private GridPane gpSitPlace;// grid pane to hold sit places, day parts labels and combo boxes
	private Label lblFName, lblLName, lblOverview, lblStartDate, lblEndDate;
	private TextField txtFName, txtLName;
	private ArrayList<ComboBox<String>> cbSitPlace;
	private Label[] lblDay, lblDayPart;
	private DatePicker dpStartDate, dpEndDate;
	private String days[] = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag"};
	private String dayParts[] = {"Ochtend", "Middag"};
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
		Scene scene = new Scene(gpBase, 1600, 700);
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
		gpBase.add(lblOverview, 0, 4);
		// make text fields
		txtFName = new TextField();
		txtFName.setMinWidth(100.0);
		txtFName.setMaxWidth(150);
		txtLName = new TextField();
		txtLName.setMinWidth(200.0);
		// add text fields to grid pane
		gpBase.add(txtFName, 1, 0, 2, 1);
		gpBase.add(txtLName, 1, 1, 2, 1);

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
	 * place labels days, day parts and sit place combo boxes into sitPlace GridPane
	 */
	public void setLblDayLblDayPartCbSitPlace() {
		gpSitPlace = new GridPane();
		gpSitPlace.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));
		gpSitPlace.setHgap(90.0);
		lblDay = new Label[5];
		lblDayPart = new Label[2];
		cbSitPlace = new ArrayList<ComboBox<String>>();
		int columspan = 4;

		for (int i = 0; i < days.length * 2; i++) { // create combo boxes
			cbSitPlace.add(new ComboBox<String>());
		}

		for (int i = 0; i < lblDay.length; i++) { // place labels day of week
			gpSitPlace.add(lblDay[i] = new Label(days[i]), i + 4, 0, 1, 1);
			lblDay[i].setMinWidth(80.0);
		}

		for (int i = 0; i < dayParts.length; i++) { // add day part labels
			if(i % 2 == 0){
				gpSitPlace.add(lblDayPart[i] = new Label(dayParts[i]), 3, 3, columspan, 1);
			}else{
				gpSitPlace.add(lblDayPart[i] = new Label(dayParts[i]), 3, 4, columspan, 1);
			}
		}

		for (int i = 0; i < days.length ; i++) { // place combo boxes for picking sit place
			gpSitPlace.add(cbSitPlace.get(i * 2), i + 4, 3, columspan, 1);
			cbSitPlace.get(i * 2).setMaxWidth(165.0);
			gpSitPlace.add(cbSitPlace.get(i * 2 + 1), i + 4, 4, columspan, 1);
			cbSitPlace.get(i *2 + 1).setMaxWidth(165.0);
		}

		for (ComboBox<String> comboBox : cbSitPlace) {
			comboBox.setItems(model.getAvailablePlaces());
			comboBox.getSelectionModel().selectFirst();
		}

		gpBase.add(gpSitPlace, 1, 0, 4,4);

	}// end setLblDayLblDayPartCbSitPlace

	/**
	 * place table view occupation in base grid pane
	 */
	public void setTableView() {
		TableView<Student> occupation = new TableView<Student>();// create new table object
		model.loadDataWithSchedule();
		occupation.setItems(model.getStudentList());// items list is created
		
		// first name column
		TableColumn<Student, String> colFirstName = new TableColumn("Voornaam");
		colFirstName.setMinWidth(100.0);
		colFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("fName"));
		
		// last name column
		TableColumn<Student, String> colLastName = new TableColumn<Student, String>("Achternaam");
		colLastName.setMinWidth(200.0);
		colLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lName"));

		occupation.getColumns().addAll(colFirstName, colLastName);
		gpBase.add(occupation, 0, 5, 2, 2);

	}// end setTableView













}// InputStudentSchedule
