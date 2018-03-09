package conceptmodel;

import java.time.LocalDate;

/** 
 * This class represents a day part AND a date.
 * 
 * @author ttimmermans
 * @version 09-03-2018
 */

public class DatedDayPart {
	
	private final LocalDate date;    // The date this is
	private final DayPart dayPart;   // Which part of the day this is
	
	/**
	 * Constructor. Assign values to instance variables.
	 * @param  date     The date of this day.
	 * @param  dayPart  Which part (morning or afternoon) of the day this is
	 */
	public DatedDayPart(LocalDate date, DayPart dayPart) {
		this.date = date;
		this.dayPart = dayPart;
	}

	/**
	 * Get the date.
	 * @return  The date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Get which part of the day (morning or afternoon) this is.
	 * @return  The dayPart
	 */
	public DayPart getDayPart() {
		return dayPart;
	}

}
