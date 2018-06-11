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
 * @version 08-06-2018
 */

public class Schedule {
	
	/* Schedule is valid in this range of dates */
	private final LocalDate startDate;
	private final LocalDate endDate;
	
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
	 * new (08-06-2018 15.40u) constructor which accepts an array of strings 
	 * and should eventually be fed by the database.
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
							Place.getPlaceByNumber(Integer.parseInt(scheduleData[i])));
					// assign i as dayPart (match with dayParts) and     scheduleData[i] as place 
				}
			}
			this.studentID = scheduleData[0];
			this.startDate = LocalDate.parse(scheduleData[1]);
			this.endDate = LocalDate.parse(scheduleData[2]);
			this.schedule = schedule;
		}	
	}
	
	////
	
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
		
		boolean dayPartFound = false;
		
		for (DayPart part: schedule.keySet()) {
			if (part == dayPart) {
				dayPartFound = true;
			}
		}
		return dayPartFound;
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

}