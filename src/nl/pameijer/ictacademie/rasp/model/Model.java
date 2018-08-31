package nl.pameijer.ictacademie.rasp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.pameijer.ictacademie.rasp.persistencylayer.PersistencyLayer;
import nl.pameijer.ictacademie.rasp.view.inputstudent.TableDates;
import nl.pameijer.ictacademie.rasp.time.TimeGuard;

public class Model {

	private final TimeGuard timeGuardian = new TimeGuard();

	private final ObservableList<Student> studentList = FXCollections.observableArrayList();

	private final ObjectProperty<Student> currentStudent = new SimpleObjectProperty<>(null);

	public Model() {
		timeGuardian.start();
	}

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
     * Add new student to observable ArrayList studentList
     */
    public void setStudent( Student newStudent )
    {
		studentList.add(newStudent);
	}


	/**
	 * Get a list of all available places for this exact date and dayPart.
	 */
	/* Doesn't work yet (08-07-2018) as supposed
	 * Therefore 'old' code still available in method above.
	 * But in some situations already works OK...
	 * This method is overloaded so the previous method above
	 * can be called again easily instead of this one for testing/debugging purposes. */
	public ObservableList<String> getAvailablePlaces(LocalDate date, DayPart dayPart) {

		ObservableList<String> availablePlaces = FXCollections.observableArrayList();
		availablePlaces.add("");

		//List<Place> availablePlaces = new ArrayList<>();

		List<Place> occupiedPlaces = new ArrayList<>();

        // Make a list with all places that are occupied this dayPart and date
		for (Student student: studentList) {
			if (student.getActiveSchedule(date) == null) {
				continue;
			}
			Map<DayPart, Place> map = student.getActiveSchedule(date).getMap();
			for (DayPart part: map.keySet()) {
				if (part == dayPart) {
					occupiedPlaces.add(map.get(part));
				}
			}
		}

		// Loop over ALL existing places and add them to the available-list if
		// they are NOT found in the occupied-list.
		for (Place place: Place.values()) {
			boolean occupied = false;
			for (Place occupiedPlace: occupiedPlaces) {
				if (place == occupiedPlace) {
					occupied = true;
					break;
				}
			}
			if (!occupied) {
				availablePlaces.add(place.name());
			}
		}

		return availablePlaces;
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
