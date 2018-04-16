package nl.pameijer.ictacademie.rasp.view.inputstudent;

import java.time.LocalDate;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InputStudentSchedule extends Application{
	GridPane gridPane;// base grid pane
	GridPane gpSitPlace;// grid pane to hold sit places and day parts labels and combo boxes
	Label lblFName, lblLName, lblOverview, lblStartDate, lblEndDate;
	TextField txtFName, txtLName;
	ArrayList<ComboBox<String>> cbSitPlace;
	Label[] lblDaySitPlace, lblMorningAfternoon;
	DatePicker dpStartDate, dpEndDate;

	public static void main(String[] args) {
		launch(args);
	}// end main

	/**
	 * method to start and show the primary stage
	 */
	public void start(Stage primaryStage) {
		// make the base gridPane
		gridPane = new GridPane();
		gridPane.setPadding(new Insets(30, 30, 30, 30));
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		setLabelsTextFields();// make labels
		setLabelDatePicker();;// make label and date pickers

		// Add the gridPane to a scene
		Scene scene = new Scene(gridPane, 1000, 700);
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
		gridPane.add(lblFName, 0, 0);
		gridPane.add(lblLName, 0, 1);
		gridPane.add(lblOverview, 0, 4);
		// make text fields
		txtFName = new TextField();
		txtFName.setMinWidth(100.0);
		txtFName.setMaxWidth(150);
		txtLName = new TextField();
		txtLName.setMinWidth(300.0);
		// add text fields to grid pane
		gridPane.add(txtFName, 1, 0);
		gridPane.add(txtLName, 1, 1);

	}// end setLabels

	/**
	 * place labels and date pickers into base grid pane
	 */
	public void setLabelDatePicker() {
		lblStartDate = new Label("Begin datum:");
		lblEndDate = new Label("Eind datum:");
		gridPane.add(lblStartDate, 0, 2);
		gridPane.add(lblEndDate, 1, 2);
		dpStartDate = new DatePicker();
		dpEndDate = new DatePicker();
		dpStartDate.setValue(LocalDate.now());// shows the current date from the system clock
		dpEndDate.setValue(LocalDate.now());
		gridPane.add(dpStartDate, 0, 3);
		gridPane.add(dpEndDate, 1, 3);
	}// setLabelDatePicker

	/**
	 * place labels and sit place combo boxes into sitPlace GridPane
	 */
	public void setComboBoxLabelSitPlace() {
		gpSitPlace = new GridPane();
		lblDaySitPlace = new Label[7];
		lblMorningAfternoon = new Label[10];
		cbSitPlace = new ArrayList<ComboBox<String>>();

		for (int i = 0; i < lblDaySitPlace.length; i++) { // place label day of week

		}

		for (int i = 0; i < lblMorningAfternoon.length; i++) { // place label day part

		}


		for (int i = 0; i <cbSitPlace.size() ; i++) { // place combo box for picking sit place

		}

	}// end setComboBoxLabelSitPlace

}// InputStudentSchedule
