package nl.pameijer.ictacademie.rasp.view.pdf;

import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Student;


public class TravelingCostView extends Application
{
	private DatePicker datePicker;
	private VBox vBox;
	private Button btnMakePDF;
	private static String month;;
	private static String year;
	private ObservableList<Student> students;
	private static String[] name;
	private static String[] id = {"3165", "500", "1455"};
	private static String[] total = {"31,65", "50,00", "145,55"};
	private static String[] iban = {"NL30 INGB 0001234567", "NL48 RABO 9876543210", "NL48 ABN 9876543210"};


	@Override
	public void start(Stage stage) {

		vBox = new VBox();
		Insets insets = new Insets(20, 20, 20, 20);
		vBox.setPadding(insets);
		datePicker = new DatePicker();
		datePicker.setValue(LocalDate.now());
		
		btnMakePDF = new Button("Maak pdf");
		

		Model model = new Model();
		model.setDatabaseEnabled(false);
		model.loadDataWithScheduleAndID();
		students = model.getStudentList();
		name = new String[students.size()];

		for (int i = 0; i < students.size(); i++) {
			name[i] = students.get(i).getFName() + " " + students.get(i).getLName();
		}


		stage.setTitle("Travelings cost pdf");
		stage.setMinWidth(400.0);
		stage.setMinHeight(400.0);
		stage.setMaxWidth(400.0);
		stage.setMaxHeight(400.0);

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});


		vBox.getChildren().add(datePicker);
		vBox.getChildren().add(btnMakePDF);
		Scene scene = new Scene(vBox);
		stage.setScene(scene);
		stage.show();


	}// end constructor TravelingCostView


	public static String getMonth()
	{
		return month;
	}


	public static String getYear()
	{
		return year;
	}


	public static String[] getNames()
	{
		return name;
	}


	public static String[] getTotal()
	{
		return total;
	}

	public static String[] getId()
	{
		return id;
	}

	public static String[] getIban()
	{
		return iban;
	}

	public static void main(String[] args) throws IOException
    {
		launch(args);


    }// end method main

}// end class MockDataTravelingCost
