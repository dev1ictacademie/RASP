package nl.pameijer.ictacademie.rasp.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

	private final StringProperty fName = new SimpleStringProperty();
	private final StringProperty lName = new SimpleStringProperty();
	
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
	 * Student constructor with Schedule.
	 */
	public Student(String fName , String lName, LocalDate startDate, 
			LocalDate endDate, HashMap<DayPart, Place> schedule) {
		setFName(fName);
		setLName(lName);
		schedules.add(new Schedule(startDate, endDate, schedule));

	}
	
	/**
	 * Make a new schedule for a student and add it to the list.
	 */
	public void makeNewSchedule(LocalDate startDate, LocalDate endDate, 
			HashMap<DayPart, Place> schedule) {
		schedules.add(new Schedule(startDate, endDate, schedule));
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
	
	/**
	 * Get the current schedule (always the last from the list).
	 * Note (23-03-2018) THIS IS FALSE!!! consider future schedules..
	 * Method needs to be reworked and needs to consider current date.
	 * (currently also throws ArrayIndexOutOfBoundsException if size == 0)
	 */
	public Schedule getCurrentSchedule() {
		return schedules.get(schedules.size() - 1);
	}

	public final StringProperty fNameProperty() {
		return this.fName;
	}


	public final String getFName() {
		return this.fNameProperty().get();
	}


	public final void setFName(final String fName) {
		this.fNameProperty().set(fName);
	}


	public final StringProperty lNameProperty() {
		return this.lName;
	}


	public final String getLName() {
		return this.lNameProperty().get();
	}


	public final void setLName(final String lName) {
		this.lNameProperty().set(lName);
	}


}
