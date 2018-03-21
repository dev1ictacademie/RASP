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
		schedule.put(DayPart.THURSDAY_MORNING, Place.ICT_12);

		HashMap<DayPart, Place> schedule1 = new HashMap<>();
		schedule1.put(DayPart.WEDNESDAY_AFTERNOON, Place.ICT_1);
		schedule1.put(DayPart.THURSDAY_MORNING, Place.ICT_10);
		schedule1.put(DayPart.FRIDAY_AFTERNOON, Place.ICT_13);
		
		studentList.setAll(
		new Student("Piet", "Pietersen", LocalDate.of(2018,3,19),LocalDate.of(2025,3,19), schedule));
		new Student("Jan", "Jansen", LocalDate.of(2018,3,25),LocalDate.of(2025,3,25), schedule1);
			
		;
	}//  end method loadDat

}
