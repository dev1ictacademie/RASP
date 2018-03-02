package conceptmodel;

import java.util.ArrayList;

/** 
 * This class represents a schedule of a participant
 * 
 * @author ttimmermans
 * @version 02-03-2018
 */

public class Schedule {
	
	private DateRange dateRange; // Schedule is valid in this range of dates
	private ArrayList<DayPart> schedule; // The DayParts of this schedule
	
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
	 * Add a DayPart to this schedule.
	 */
	public void addDayPart(DayPart dayPart) {
		// TODO stub
	}
	
	/**
	 * Remove a DayPart from this schedule.
	 */
	public void removeDayPart(DayPart dayPart) {
		// TODO stub
	}

}