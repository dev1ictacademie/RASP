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
 * @version 06-07-2018
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
	 * Schedule constructor. It accepts String array or varargs.
	 * Length of this array/varargs MUST be 12 or 13. With the first element
	 * being the studentID, the second and/or third element being the starting-
	 * and ending date of the schedule (see explanation below) and with the 
	 * last 10 elements representing the place values for the 10 day parts.
	 *
	 * Whenever a Schedule is constructed by the user through the GUI this 
	 * constructor should be called with array or varargs with length 12.
	 * If that length is 12 the Schedule is constructed with a default endDate.
	 * 
	 * Whenever a Schedule is constructed by reading values from persistent
	 * memory (database) then this constructor should be called with an array
	 * or varargs with length 13. In this case the third element should
	 * provide the ending date of the schedule.
	 * 
	 * @param  scheduleData  The array describing properties of a schedule.
	 * @throws IllegalArgumentException  if length of array is not 12 or 13.
	 * @throws DateTimeParseException    if element [1] and/or [2] cannot be 
	 *                                   parsed to valid LocalDate objects.
	 */
	public Schedule(String... scheduleData) {
		if (scheduleData.length != 12 && scheduleData.length != 13) {
			throw new IllegalArgumentException();
		}
		
		// This block constructs a Schedule with a default end date
		if (scheduleData.length == 12) {
			HashMap<DayPart, Place> schedule = new HashMap<>();
			for (int i = 0; i < scheduleData.length; i++) {
				if (i >= 2 && scheduleData[i] != null) {
					schedule.put(
							DayPart.getDayPartByNumber(i - 2),
							Place.valueOf(scheduleData[i]));
				}
			}
			this.studentID = scheduleData[0];
			this.startDate = LocalDate.parse(scheduleData[1]);
			this.endDate = LocalDate.of(9999, 12, 31);
			this.schedule = schedule;

		}
		
		// This block constructs a Schedule with an end date passed as argument
		else { // scheduleData.length == 13
			HashMap<DayPart, Place> schedule = new HashMap<>();
			for (int i = 0; i < scheduleData.length; i++) {
				if (i >= 3 && scheduleData[i] != null) {
					schedule.put(
							DayPart.getDayPartByNumber(i - 3),
							Place.valueOf(scheduleData[i]));
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
	 * Compare schedules to each other to get chronological order.
	 * 
	 * Simply comparing startDate of this schedule with the endDate
	 * of another schedule for is an acceptable way to do this as long as 
	 * new schedules are ONLY initially assigned to a student with the use
	 * of the addNewSchedule method from Student class!
	 */
	@Override
	public int compareTo(Schedule anotherSchedule) {
		
		if (anotherSchedule == null) {
			throw new NullPointerException();
		}
		return this.startDate.compareTo(anotherSchedule.endDate);
	}
	
	/**
	 * Override of equals method from Object class so schedules can be compared.
	 */
	@Override
	public boolean equals(Object otherSchedule) {
		
		if (otherSchedule == this) {
			return true;
		}
		
	    if (!(otherSchedule instanceof Schedule)) {
	        return false;
	    }
		
		Schedule other = (Schedule)otherSchedule;
		
		return this.studentID.equals(other.studentID) &&
		this.startDate.equals(other.startDate) &&
		this.endDate.equals(other.endDate) &&
		this.schedule.equals(other.schedule);
	}

    /**
     * Override of hashCode method from Object class.
     */
	@Override
	public int hashCode() {
	    int code = 17;
		code = 31 * code + studentID.hashCode();
		code = 31 * code + startDate.hashCode();
		code = 31 * code + endDate.hashCode();
		code = 31 * code + schedule.hashCode();
	    return code;
	}

}