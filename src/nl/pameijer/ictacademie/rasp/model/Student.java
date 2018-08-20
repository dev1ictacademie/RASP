package nl.pameijer.ictacademie.rasp.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

	private final StringProperty fName = new SimpleStringProperty();
	private final StringProperty lName = new SimpleStringProperty();
	private final StringProperty namePrefix = new SimpleStringProperty();
	private final StringProperty id = new SimpleStringProperty();

	private final StringProperty monMorning = new SimpleStringProperty();
	private final StringProperty monAfternoon = new SimpleStringProperty();
	private final StringProperty tuesMorning = new SimpleStringProperty();
	private final StringProperty tuesAfternoon = new SimpleStringProperty();
	private final StringProperty wedMorning = new SimpleStringProperty();
	private final StringProperty wedAfternoon = new SimpleStringProperty();
	private final StringProperty thursMorning = new SimpleStringProperty();
	private final StringProperty thursAfternoon = new SimpleStringProperty();
	private final StringProperty friMorning = new SimpleStringProperty();
	private final StringProperty friAfternoon = new SimpleStringProperty();

	// The students's schedule(s)
	private ArrayList<Schedule> schedules = new ArrayList<>();


	public Student(){}

	/**
	 * Student constructor
	 */
	public Student(String fName , String lName, String namePrefix, String id) {
		setFName(fName);
		setLName(lName);
		setNamePrefix(namePrefix);
		setId(id);
	}


	/**
	 * Set all DayPart properties of a student. Needs to be set for each
	 * student after they have been constructed and were added to the list.
	 *
	 * These properties are used by the TableView from InputStudentSchedule
	 * class to show the correct places for a certain date and day part.
	 *
	 * As such these properties might be different each week and they should
	 * be set again if a 'week-change' is initiated in the week overview.
	 *
	 * @param  weekDates  This should always be an array with length 5
	 *                    and have a Monday as first day!
	 */
	public void setDayPartProperties(LocalDate[] weekDates) {

		// Values To Assign To Properties
		String[] valuesForProps = new String[10];

		int i = 0;

		for (LocalDate date: weekDates) {

			if (getActiveSchedule(date) == null) {

				// If null there is no active schedule at this date!
				// Skip the rest of this loop and continue with the next
				// iteration to prevent a NullPointerException.
				// However, i DOES need to be incremented by 2 (for the skipped
				// increments after the assignments) so that next iterations
				// don't assign values to wrong indexNumbers of valuesForProps

				i = i + 2;
				continue;
			}

			// Get the active schedule for this date
			Schedule schedule = getActiveSchedule(date);

			DayPart thisMorning = DayPart.getMorningOf(date.getDayOfWeek());
			DayPart thisAfternoon = DayPart.getAfternoonOf(date.getDayOfWeek());

			Map<DayPart, Place> map = schedule.getMap();

			if (map.containsKey(thisMorning)) {
				valuesForProps[i] = map.get(thisMorning).name();
			}
			i++;

			if (map.containsKey(thisAfternoon)) {
				valuesForProps[i] = map.get(thisAfternoon).name();
			}
			i++;
		}

		setMonMorning(valuesForProps[0]);
		setMonAfternoon(valuesForProps[1]);
		setTuesMorning(valuesForProps[2]);
		setTuesAfternoon(valuesForProps[3]);
		setWedMorning(valuesForProps[4]);
		setWedAfternoon(valuesForProps[5]);
		setThursMorning(valuesForProps[6]);
		setThursAfternoon(valuesForProps[7]);
		setFriMorning(valuesForProps[8]);
		setFriAfternoon(valuesForProps[9]);
	}

	/**
	 * Get the schedule that is active at a certain date.
	 * @param   date   The date to get the schedule for which is then active.
	 * @return  The schedule which is active at that date. Will return null if
	 *          there is no active schedule at that date.
	 */
	public Schedule getActiveSchedule(LocalDate date) {

		Schedule activeSchedule = null;

		for (Schedule schedule: schedules) {
			if (schedule.isDateWithinSchedule(date)) {
				activeSchedule = schedule;
				break;
			}
		}

		return activeSchedule;
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
	 * Get the student's list of schedules.
	 */
	public ArrayList<Schedule> getSchedules() {
		return schedules;
	}

	/**
	 * Does the student have this exact ID?
	 */
	public boolean hasThisID(String ID) {
		return this.getId().equals(ID);
	}


	/**
	 * Assign an EXISTING schedule to a student (add it to the schedules list).
	 *
	 * This is the method that should be used when a schedule is constructed
	 * and when the ending date of previous schedule should not be modified.
	 *
	 * This is, for example, the case when the application starts and data is
	 * read from persistent memory (database) and schedule-objects are created
	 * on the basis of that data.
	 */
	public void addExistingSchedule(Schedule schedule) {
		schedules.add(schedule);
	}


	/**
	 * Assign a NEW schedule to a student (add it to the schedules list).
	 *
	 * This is the method that should be used when a schedule is made by the
	 * user of the application through the GUI and then assigned to a student
	 * for the very first time. In this case the end date of the previous
	 * schedule should be modified.
	 *
	 * If the ending date of the previous schedule is after the starting
	 * date of this schedule or is the same, set the ending date of the
	 * previous schedule to the date preceding the starting date of
	 * this schedule, excluding dates which fall in weekends.
	 */
	public void addNewSchedule(Schedule schedule) {

		if (schedules.size() > 0) {

			Schedule previousSchedule = schedules.get(schedules.size() - 1);

			if (!schedule.getStartDate().isAfter(previousSchedule.getStartDate())) {
				throw new IllegalArgumentException("StartDate of new schedule " +
						"should be after startDate of previous schedule!");
			}

			if (schedule.getStartDate().isBefore(previousSchedule.getEndDate())) {

				LocalDate newEndingDate = schedule.getStartDate();

				do {
					newEndingDate = newEndingDate.minusDays(1);
				} while ( ! newEndingDate.isBefore(schedule.getStartDate()) // <--- This check not necessary???
						|| newEndingDate.getDayOfWeek() == DayOfWeek.SATURDAY
						|| newEndingDate.getDayOfWeek() == DayOfWeek.SUNDAY);

				previousSchedule.setEndDate(newEndingDate);
			}
		}

		schedules.add(schedule);
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
	public final StringProperty namePrefixProperty()	{
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



	// DayPart properties //


	// Monday Morning
	public final StringProperty monMorningProperty() {
		return this.monMorning;
	}

	public final String getMonMorning() {
		return this.monMorningProperty().get();
	}

	public final void setMonMorning(final String monMorning) {
		this.monMorningProperty().set(monMorning);
	}


	// Monday Afternoon
	public final StringProperty monAfternoonProperty() {
		return this.monAfternoon;
	}

	public final String getMonAfternoon() {
		return this.monAfternoonProperty().get();
	}

	public final void setMonAfternoon(final String monAfternoon) {
		this.monAfternoonProperty().set(monAfternoon);
	}


    // Tuesday Morning
	public final StringProperty tuesMorningProperty() {
		return this.tuesMorning;
	}

	public final String getTuesMorning() {
		return this.tuesMorningProperty().get();
	}

	public final void setTuesMorning(final String tuesMorning) {
		this.tuesMorningProperty().set(tuesMorning);
	}


	// Tuesday Afternoon
	public final StringProperty tuesAfternoonProperty() {
		return this.tuesAfternoon;
	}

	public final String getTuesAfternoon() {
		return this.tuesAfternoonProperty().get();
	}

	public final void setTuesAfternoon(final String tuesAfternoon) {
		this.tuesAfternoonProperty().set(tuesAfternoon);
	}


    // Wednesday Morning
	public final StringProperty wedMorningProperty() {
		return this.wedMorning;
	}

	public final String getWedMorning() {
		return this.wedMorningProperty().get();
	}

	public final void setWedMorning(final String wedMorning) {
		this.wedMorningProperty().set(wedMorning);
	}


    // Wednesday Afternoon
	public final StringProperty wedAfternoonProperty() {
		return this.wedAfternoon;
	}

	public final String getWedAfternoon() {
		return this.wedAfternoonProperty().get();
	}

	public final void setWedAfternoon(final String wedAfternoon) {
		this.wedAfternoonProperty().set(wedAfternoon);
	}


	// Thursday Morning
	public final StringProperty thursMorningProperty() {
		return this.thursMorning;
	}

	public final String getThursMorning() {
		return this.thursMorningProperty().get();
	}

	public final void setThursMorning(final String thursMorning) {
		this.thursMorningProperty().set(thursMorning);
	}


	// Thursday Afternoon
	public final StringProperty thursAfternoonProperty() {
		return this.thursAfternoon;
	}

	public final String getThursAfternoon() {
		return this.thursAfternoonProperty().get();
	}

	public final void setThursAfternoon(final String thursAfternoon) {
		this.thursAfternoonProperty().set(thursAfternoon);
	}


	// Friday Morning
	public final StringProperty friMorningProperty() {
		return this.friMorning;
	}

	public final String getFriMorning() {
		return this.friMorningProperty().get();
	}

	public final void setFriMorning(final String friMorning) {
		this.friMorningProperty().set(friMorning);
	}


    // Friday Afternoon
	public final StringProperty friAfternoonProperty() {
		return this.friAfternoon;
	}

	public final String getFriAfternoon() {
		return this.friAfternoonProperty().get();
	}

	public final void setFriAfternoon(final String friAfternoon) {
		this.friAfternoonProperty().set(friAfternoon);
	}

}
