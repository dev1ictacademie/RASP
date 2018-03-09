package conceptviews;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MonthView extends Application {


    private ArrayList<String> participants;

    @Override
    public void start(Stage primaryStage) throws Exception {


        participants = new ArrayList<String>();
        participants.add("Jan Jansen");
        participants.add("Piet Pietersen");
        participants.add("Joost de  Draaier");
        participants.add("Klaas Vaak");
        participants.add("Karel den Boer");
        participants.add("Egbert de Vries");
        primaryStage.setTitle("Maand overzicht.");

        System.out.println(Year.now().getValue());
        System.out.println(Calendar.getInstance().get(Calendar.MONTH) + 1);

        Scene scene = new Scene(makeCalendar(Year.now().getValue(), Calendar.getInstance().get(Calendar.MONTH) + 1), 1800, 400);
            primaryStage.setScene(scene);
            primaryStage.show();


    }

    public int daysOfMonth(int year , int month){
        LocalDate date = LocalDate.of(year, month, 1);
        return date.lengthOfMonth();
    }

    public VBox makeCalendar(int year, int month) {
    	VBox vb = new VBox();
    	HBox dayOfMonth = new HBox();
    	HBox dayOfWeek = new HBox();
    	HBox dayParts = new HBox();
    	Calendar calendar = Calendar.getInstance();
    	String monthText = new SimpleDateFormat("MMM").format(calendar.getTime());
    	
    	
    	Label label2 = new Label();
    	label2.setText(monthText + "  " + Year.now().getValue() + "     ");
    	dayOfMonth.getChildren().add(label2);

    	for (int i = 1; i <= daysOfMonth(year, month); i++) {
			Label label = new Label();
			label.setText("" + i + "   |   ");

			dayOfMonth.getChildren().add(label);


		}

    	vb.getChildren().add(dayOfMonth);
		return vb;
	}// end method makeCalendar

    public String dayName() {
    	Calendar day = Calendar.getInstance();
    	int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 1;
    	System.out.println(dayOfWeek);

		return null;
	}


    public static void main(String[] args) {

        launch(args);
    }

}