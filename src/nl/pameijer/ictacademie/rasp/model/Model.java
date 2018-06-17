package nl.pameijer.ictacademie.rasp.model;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.pameijer.ictacademie.rasp.persistencylayer.PersistencyLayer;
import nl.pameijer.ictacademie.rasp.view.inputstudent.TableDates;

public class Model {

	private final ObservableList<Student> studentList = FXCollections.observableArrayList();

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

    /**
     * Return the studentList.
     */
    public ObservableList<Student> getStudentList() {
        return studentList ;
    }

    /**
     * Method for testing combo boxes sit places
     * @return Array list Strings of sit places
     */
    public ObservableList<String> getAvailablePlaces() {

		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("");
		list.add("ICT_2");
		list.add("ICT_3");
		list.add("ECDL_1");
		list.add("ECDL_2");
		list.add("SD_1");

    	return list;
	}


	/**
	 * Load the data from the PersistencyLayer class:
	 * 
	 *  1) Construct the student objects from the array and add the to the studentList
	 *  2) Construct the schedules from the other array and assign them to the (right) students
	 *  3) Set the dayPartProperties for the students so that correct places are shown in weekView
	 */
	public void loadDataWithScheduleAndID() {
		
		studentList.setAll(PersistencyLayer.constructStudentList(PersistencyLayer.studentsMockArray));
		
		PersistencyLayer.constructSchedules(PersistencyLayer.schedulesMockArray, studentList);
		
		for (Student student: getStudentList()) {
			student.setDayPartProperties(TableDates.getThisWeekDates());
		}
		
	}
	
	
    /**
	 * Test method to construct Students, Schedules (with hard-coded HashMaps)
	 * and then add them to the ObservableList.
	 * 
	 * Used by Controller (and then indirectly by MonthInputView class).
	 */
	public void loadDataWithScheduleAndID_2() {

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
		new Student("22","Kim", "Holland", LocalDate.of(2018,3,15), LocalDate.MAX, schedule2),
		new Student("23","John", "Bouwer", LocalDate.of(2018,3,7), LocalDate.MAX, schedule),
		new Student("12","Pamela", "Negi", LocalDate.of(2018,3,5), LocalDate.MAX, schedule1),
		new Student("19","Thijs", "Steen, van der", LocalDate.of(2018,3,1), LocalDate.MAX, schedule)
		);
	}//  end method loadDataWithScheduleAndID_2


	public void saveDayPart(String id, String text) {
		// TODO split id into studentid, date, daypart and the value from text
		System.out.println("Day part saved to database. " + id + " " + text);
	}
}
