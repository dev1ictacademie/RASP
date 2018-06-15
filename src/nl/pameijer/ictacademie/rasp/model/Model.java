package nl.pameijer.ictacademie.rasp.model;


import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.pameijer.ictacademie.rasp.persistencylayer.PersistencyLayer;

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
	 * Test method to construct Students, Schedules and then add them to 
	 * the ObservableList.
	 * 
	 * Used by Controller (and then indirectly by MonthInputView class).
	 */
	public void loadDataWithScheduleAndID_2(){

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

	/**
	 * Test method to construct Students from the weekSchedules array (found in 
	 * PersistencyLayer class) and add them to the ObservableList.
	 * 
	 * Used by InputStudentSchedule class.
	 */
	public void loadDataWithScheduleAndID()
	{
		for (int i = 0; i < PersistencyLayer.weekSchedules.length; i++)
		{
			studentList.add ( new Student (PersistencyLayer.weekSchedules[i][0],
					PersistencyLayer.weekSchedules[i][2], PersistencyLayer.weekSchedules[i][1],
					PersistencyLayer.weekSchedules[i][3], PersistencyLayer.weekSchedules[i][4],
					PersistencyLayer.weekSchedules[i][5], PersistencyLayer.weekSchedules[i][6],
					PersistencyLayer.weekSchedules[i][7], PersistencyLayer.weekSchedules[i][8],
					PersistencyLayer.weekSchedules[i][9], PersistencyLayer.weekSchedules[i][10],
					PersistencyLayer.weekSchedules[i][11], PersistencyLayer.weekSchedules[i][12],
					PersistencyLayer.weekSchedules[i][13] ) );
		}

	}


	public void saveDayPart(String id, String text) {
		// TODO split id into studentid, date, daypart and the value from text
		System.out.println("Day part saved to database. " + id + " " + text);
	}
}
