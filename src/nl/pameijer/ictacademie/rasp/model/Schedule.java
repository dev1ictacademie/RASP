package nl.pameijer.ictacademie.rasp.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

/** 
 * This class represents a schedule of a participant which is a listing of 
 * DayParts the participant is expected to be present mapped to the place he
 * or she has during that DayPart.
 * 
 * @author ttimmermans
 * @version 29-06-2018
 */

public class Schedule implements Comparable<Schedule> {
	
	/* Schedule is valid in this range of dates */
	private LocalDate startDate;
	private LocalDate endDate;
	
	/* The actual schedule is composed of a mapping between the DayParts that 
	 * the participant is supposed to be present and the Place he or she has 
	 * during that particular DayPart. */
	private final HashMap<DayPart, Place> schedule;
	
	// Indicates to which student this schedule belongs.
	private final String studentID;
	
	/**
	 * Constructor. Arguments should be provided by the view (intermediate step
	 * is constructing the HashMap based on the different DayParts and 
	 * associated Places filled in).
	 * @param  startDate   the schedule's start date
	 * @param  endDate     the schedule's end date
	 * @param  schedule    the schedule itself
	 */
	public Schedule(String studentID, LocalDate startDate, LocalDate endDate,
			HashMap<DayPart, Place> schedule) {
		this.studentID = studentID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.schedule = schedule;
	}
	
	/**
	 * Constructor which accepts String array. Array MUST have length 13
	 * with first element being StudentID (owner of the schedule), second
	 * element being the starting date of the schedule, third element being
	 * the end date and with the last 10 elements representing the place values
	 * for the 10 day parts.
	 * 
	 * @param  scheduleData  The array describing properties of a schedule.
	 * @throws IllegalArgumentException  if length of array is not 13.
	 * @throws DateTimeParseException    if element [1] and/or [2] cannot be 
	 *                                   parsed to valid LocalDate objects.
	 */
	public Schedule(String... scheduleData) {
		if (scheduleData.length != 13) {
			throw new IllegalArgumentException();
		}
		else {
			HashMap<DayPart, Place> schedule = new HashMap<>();
			for (int i = 0; i < scheduleData.length; i++) {
				if (i >= 3 && scheduleData[i] != null) {
					schedule.put(
							DayPart.getDayPartByNumber(i - 3),
							Place.valueOf(scheduleData[i]));
							//Place.getPlaceByNumber(Integer.parseInt(scheduleData[i])));
				}
			}
			this.studentID = scheduleData[0];
			this.startDate = LocalDate.parse(scheduleData[1]);
			this.endDate = LocalDate.parse(scheduleData[2]);
			this.schedule = schedule;
		}	
	}
	
	/**
	 * Get the starting date.
	 * @return  the start date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Get the ending date.
	 * @return  the end date
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	
	/**
	 * Set the starting date of this schedule.
	 * @param  the start date
	 */
	public void setStartDate() {
		// currently (29-06-2018) not in use
	}

	/**
	 * Set the ending date of this schedule.
	 * @param  the end date
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Test if a given date is within the period of this schedule.
	 * So if true, the date should not be before the startDate of the schedule
	 * and not after the endDate.
	 * @return  true if this date is within this schedule, false if not
	 */
	public boolean isDateWithinSchedule(LocalDate date) {
		if ( (!date.isBefore(startDate)) && (!date.isAfter(endDate)) ) {
			return true;
		}
		else {
			return false;
		}	
	}
	
	/**
	 * Is a certain dayPart in this schedule or not?
	 */
	public boolean isDayPartInSchedule(DayPart dayPart) {
		return schedule.containsKey(dayPart);
	}
	
	/**
	 * Return the actual schedule (DayPart, Place mappings) of this schedule.
	 */
	public HashMap<DayPart, Place> getMap() {
		return schedule;
	}
	
	/**
	 * Return the DayParts in this schedule.
	 */
	public Set<DayPart> getDayParts() {
		return schedule.keySet();
	}

	/**
	 * Simply comparing startDate of this schedule with the endDate
	 * of another schedule for order is an acceptable way to compare
	 * them because a schedule can never be added to the list without
	 * the previous schedule's endDate being set to this schedule's
	 * endDate minus 1 day (weekend days excluded).
	 * 
	 * This applies as long as schedules are only added to the student's
	 * schedule list with the addSchedule method from Student class which
	 * is the ONLY way schedules should be added to the list!
	 */
	@Override
	public int compareTo(Schedule anotherSchedule) {
		return this.startDate.compareTo(anotherSchedule.endDate);
	}

}