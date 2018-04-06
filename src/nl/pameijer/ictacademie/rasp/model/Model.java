package nl.pameijer.ictacademie.rasp.model;


import java.time.LocalDate;
import java.util.HashMap;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	private final ObservableList<Student> studentList = FXCollections.observableArrayList(student ->
    new Observable[] {student.fNameProperty(), student.lNameProperty()});

	private final ObjectProperty<Student> currentStudent = new SimpleObjectProperty<>(null);

	public ObjectProperty<Student> currentStudentProperty() {
        return currentStudent ;
    }
	public final Student getCurrentStudent() {
        return currentStudentProperty().get();
    }

    public final void setCurrentStudent(Student Student) {
        currentStudentProperty().set(Student);
    }

    public ObservableList<Student> getStudentList() {
        return studentList ;
    }


	public void loadData(){

		studentList.setAll(
		new Student("Piet", "Pietersen" ),
		new Student("Jan", "Jansen"),
		new Student("Kees", "Groot" ),
		new Student("Hans", "Klein"),
		new Student("Cornelis", "Boer, den" ),
		new Student("Frederik", "Schoenlapper"),
		new Student("Kees", "Groot, de" ),
		new Student("Hans", "Klein"),
		new Student("Karel", "Bakker" ),
		new Student("Fred", "Lubbers"),
		new Student("Ton", "Hout , van" ),
		new Student("Hans", "Vries , de"),
		new Student("Karel", "Appel"),
		new Student("Paula", "Aardbei"),
		new Student("Piet", "Derksen"),
		new Student("Klaas" , "Graaf, de")
		);
	}//  end method loadData

	public void loadDataWithSchedule(){

		HashMap<DayPart, Place> schedule = new HashMap<>();
		schedule.put(DayPart.MONDAY_MORNING, Place.ICT_1);
		schedule.put(DayPart.MONDAY_AFTERNOON,Place.ICT_8);
		schedule.put(DayPart.TUESDAY_AFTERNOON, Place.ECDL_5);

		HashMap<DayPart, Place> schedule1 = new HashMap<>();
		schedule1.put(DayPart.WEDNESDAY_AFTERNOON, Place.ICT_1);
		schedule1.put(DayPart.THURSDAY_MORNING, Place.ICT_10);
		schedule1.put(DayPart.FRIDAY_AFTERNOON, Place.ICT_13);

		HashMap<DayPart, Place> schedule2 = new HashMap<>();
		schedule2.put(DayPart.MONDAY_MORNING, Place.ICT_3);
		schedule2.put(DayPart.MONDAY_AFTERNOON, Place.ICT_9);
		schedule2.put(DayPart.FRIDAY_MORNING, Place.ICT_11);

		studentList.setAll(
		new Student("Piet", "Pietersen", LocalDate.of(2018,3,3), LocalDate.MAX, schedule),
		new Student("Ton", "Hout , van", LocalDate.of(2018,4,15), LocalDate.MAX, schedule1),
		new Student("Jan", "Jansen", LocalDate.of(2018,3,7), LocalDate.MAX, schedule),
		new Student("Paula", "Aardbei", LocalDate.of(2018,3,5), LocalDate.MAX, schedule1),
		new Student("Karel", "Appel", LocalDate.of(2018,3,1), LocalDate.MAX, schedule2),
		new Student("Hans", "Andersen", LocalDate.of(2018,3,3), LocalDate.MAX, schedule),
		new Student("Kim", "Hulst, van", LocalDate.of(2018,3,15), LocalDate.MAX, schedule2),
		new Student("John", "Bouwer", LocalDate.of(2018,3,7), LocalDate.MAX, schedule),
		new Student("Pamela", "Negi", LocalDate.of(2018,3,5), LocalDate.MAX, schedule1),
		new Student("Thijs", "Steen, van der", LocalDate.of(2018,3,1), LocalDate.MAX, schedule)
		);

	}//  end method loadDat

	public void loadDataWithScheduleAndID(){

		HashMap<DayPart, Place> schedule = new HashMap<>();
		schedule.put(DayPart.MONDAY_MORNING, Place.ICT_1);
		schedule.put(DayPart.MONDAY_AFTERNOON,Place.ICT_8);
		schedule.put(DayPart.TUESDAY_AFTERNOON, Place.ECDL_5);

		HashMap<DayPart, Place> schedule1 = new HashMap<>();
		schedule1.put(DayPart.WEDNESDAY_AFTERNOON, Place.ICT_1);
		schedule1.put(DayPart.THURSDAY_MORNING, Place.ICT_10);
		schedule1.put(DayPart.FRIDAY_AFTERNOON, Place.ICT_13);

		HashMap<DayPart, Place> schedule2 = new HashMap<>();
		schedule2.put(DayPart.MONDAY_MORNING, Place.ICT_3);
		schedule2.put(DayPart.MONDAY_AFTERNOON, Place.ICT_9);
		schedule2.put(DayPart.FRIDAY_MORNING, Place.ICT_11);

		studentList.setAll(
		new Student("1", "Piet", "Pietersen", LocalDate.of(2018,3,3), LocalDate.MAX, schedule),
		new Student("2","Ton", "Hout , van", LocalDate.of(2018,4,15), LocalDate.MAX, schedule1),
		new Student("4","Jan", "Jansen", LocalDate.of(2018,3,7), LocalDate.MAX, schedule),
		new Student("5","Paula", "Aardbei", LocalDate.of(2018,3,5), LocalDate.MAX, schedule1),
		new Student("8","Karel", "Appel", LocalDate.of(2018,3,1), LocalDate.MAX, schedule2),
		new Student("11","Hans", "Andersen", LocalDate.of(2018,3,3), LocalDate.MAX, schedule),
		new Student("22","Kim", "Hulst, van", LocalDate.of(2018,3,15), LocalDate.MAX, schedule2),
		new Student("23","John", "Bouwer", LocalDate.of(2018,3,7), LocalDate.MAX, schedule),
		new Student("12","Pamela", "Negi", LocalDate.of(2018,3,5), LocalDate.MAX, schedule1),
		new Student("19","Thijs", "Steen, van der", LocalDate.of(2018,3,1), LocalDate.MAX, schedule)
		);
	}
}
