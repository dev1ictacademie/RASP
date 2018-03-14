package conceptmodel;

import java.util.HashMap;

/** 
 * This class represents a schedule of a participant which is a listing of 
 * DayParts the participant is expected to be present mapped to the place he
 * or she has during that DayPart.
 * 
 * @author ttimmermans
 * @version 09-03-2018
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
	 * Remove a DayPart (and it's associated Place) from this schedule.
	 */
	public void removeDayPart(DayPart dayPart) {
		schedule.remove(dayPart);
	}

}