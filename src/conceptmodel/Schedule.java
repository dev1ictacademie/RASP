package conceptmodel;

import java.util.HashMap;

/** 
 * This class represents a schedule of a participant
 * 
 * @author ttimmermans
 * @version 06-03-2018
 */

public class Schedule {
	
	private DateRange dateRange; // Schedule is valid in this range of dates
	
	/* The actual schedule is composed of a mapping between the DayParts that 
	 * the participant is supposed to be present and the Place he or she has 
	 * during that particular DayPart. */
	private HashMap<DayPart, Place> schedule = new HashMap<>();
	
	/**
	 * Constructor.
	 * @param  dateRange  The date range in which this schedule is applicable
	 */
	public Schedule(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	/**
	 * Get the date range in which this schedule is valid.
	 * @return the dateRange
	 */
	public DateRange getDateRange() {
		return dateRange;
	}

	/**
	 * Set the date range in which this is a valid schedule.
	 * @param dateRange  the dateRange to set
	 */
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	
	/**
	 * Add a DayPart with the Place participant then has to the schedule.
	 */
	public void addDayPart(DayPart dayPart, Place place) {
		schedule.put(dayPart, place);
	}
	
	/**
	 * Remove a DayPart (with the associated Place) from this schedule.
	 */
	public void removeDayPart(DayPart dayPart) {
		// TODO stub
	}

}