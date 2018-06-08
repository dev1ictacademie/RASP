package nl.pameijer.ictacademie.rasp.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

	private final StringProperty fName = new SimpleStringProperty();
	private final StringProperty lName = new SimpleStringProperty();
	private final StringProperty namePrefix = new SimpleStringProperty();
	private final StringProperty id = new SimpleStringProperty();

	// The students's schedule(s)
	private ArrayList<Schedule> schedules = new ArrayList<>();

	/**
	 * Student constructor
	 */
	public Student(String fName , String lName){
		setFName(fName);
		setLName(lName);
	}

	/**
	 * Student constructor
	 */
	public Student(String fName , String lName, String namePrefix, String id){
		setFName(fName);
		setLName(lName);
		setNamePrefix(namePrefix);
		setId(id);
	}

	/**
	 * Student constructor with Schedule.
	 */
	public Student(String fName , String lName, LocalDate startDate,
			LocalDate endDate, HashMap<DayPart, Place> schedule) {
		setFName(fName);
		setLName(lName);
		schedules.add(new Schedule(id.toString(), startDate, endDate, schedule));

	}

	/**
	 * Student constructor with ID and Schedule.
	 */
	public Student(String id, String fName , String lName, LocalDate startDate,
			LocalDate endDate, HashMap<DayPart, Place> schedule) {
		setFName(fName);
		setLName(lName);
		schedules.add(new Schedule(id, startDate, endDate, schedule));
		setId(id);
	}

	/**
	 * Make a new schedule for a student and add it to the list.
	 */
	public void makeNewSchedule(LocalDate startDate, LocalDate endDate,
			HashMap<DayPart, Place> schedule) {
		schedules.add(new Schedule(id.toString(), startDate, endDate, schedule));
	}

	/**
	 * Is this student expected at a certain date and day part?
	 * @param   date    the applicable date
	 * @param   dayPart the applicable dayPart
	 * @return  true if student is expected to be attending at this date and
	 *          dayPart, false if not
	 */
	public boolean isExpected(LocalDate date, DayPart dayPart) {

		boolean isExpected = false;

		for (Schedule schedule: schedules) {
			if (schedule.isDateWithinSchedule(date) &&
					schedule.isDayPartInSchedule(dayPart)) {
				isExpected = true;
			}
		}
		return isExpected;
	}
	
	public ArrayList<Schedule> getSchedules() {
		return schedules;
	}	

	
	// First Name
	public final StringProperty fNameProperty() {
		return this.fName;
	}

	public final String getFName() {
		return this.fNameProperty().get();
	}

	public final void setFName(final String fName) {
		this.fNameProperty().set(fName);
	}


	// Last Name
	public final StringProperty lNameProperty() {
		return this.lName;
	}

	public final String getLName() {
		return this.lNameProperty().get();
	}

	public final void setLName(final String lName) {
		this.lNameProperty().set(lName);
	}
	
	
	// Name Prefix
	public final StringProperty namePrefixProperty() {
		return this.namePrefix;
	}
	
	public final String getNamePrefix() {
		return this.namePrefixProperty().get();
	}

	public final void setNamePrefix(final String namePrefix) {
		this.namePrefixProperty().set(namePrefix);
	}
	
	
	// ID
	public final StringProperty idProperty() {
		return this.id;
	}

	public final String getId() {
		return this.idProperty().get();
	}

	public final void setId(final String id) {
		this.idProperty().set(id);
	}

	
}
