package nl.pameijer.ictacademie.rasp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	 *  1) Construct the student objects from the array and add them to the studentList
	 *  2) Construct the schedules from the other array and assign them to the (right) students
	 *  3) Sort the schedules from a student's schedule-list to guarantee chronological order
	 *  4) Set the dayPartProperties for the students so that correct places are shown in weekView
	 */
	public void loadDataWithScheduleAndID() {
		
		studentList.setAll(PersistencyLayer.constructStudentList(PersistencyLayer.studentsMockArray));
		
		PersistencyLayer.constructSchedules(PersistencyLayer.schedulesMockArray, studentList);
		
		for (Student student: getStudentList()) {
			Collections.sort(student.getSchedules());
			student.setDayPartProperties(TableDates.getThisWeekDates());
		}
		
	}

	
	public void saveDayPart(String id, String text) {
		// TODO split id into studentid, date, daypart and the value from text
		System.out.println("Day part saved to database. " + id + " " + text);
	}
	
	/**
	 * Temporary (for as long as the database doesn't handle this) method to 
	 * generate Student ID's that may be required for testing purposes. 
	 */
	public String generateStudentID() {
		
		List<Integer> ids = new ArrayList<>();
		
		for (Student stu: studentList) {
			ids.add(Integer.parseInt(stu.getId()));
		}
		
		Collections.sort(ids);
		
		// Get last element from list which is highest value after sort
		Integer highest = ids.get(ids.size() - 1);
		
		// increment by 1 to get a new, unique, Id-value
		highest++;
		
		return highest.toString();
	}
}
