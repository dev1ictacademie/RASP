package nl.pameijer.ictacademie.rasp.model;

import java.time.DayOfWeek;

/** 
 * This enum lists the ten different DayParts during the week at which 
 * participants can be attending at ICT Academie. A DayPart is a non-weekend 
 * day AND is either a morning or an afternoon.
 * 
 * @author ttimmermans
 * @version 17-06-2018
 */

public enum DayPart {
	
	MONDAY_MORNING, MONDAY_AFTERNOON,
	TUESDAY_MORNING, TUESDAY_AFTERNOON,
	WEDNESDAY_MORNING, WEDNESDAY_AFTERNOON,
	THURSDAY_MORNING, THURSDAY_AFTERNOON,
	FRIDAY_MORNING, FRIDAY_AFTERNOON;
	
	private DayOfWeek day;  // DayOfWeek constant provided by java.time enum 
	private PartOfDay part; // This is either a morning or an afternoon
	
	/**
	 * Constructor. Assign weekdays and parts to the DayParts.  
	 */
	private DayPart() {
		
		switch(this.ordinal()) {
		
		case 0: day = DayOfWeek.MONDAY; part = PartOfDay.MORNING; break;
		case 1: day = DayOfWeek.MONDAY; part = PartOfDay.AFTERNOON; break;
		
		case 2: day = DayOfWeek.TUESDAY; part = PartOfDay.MORNING; break;
		case 3: day = DayOfWeek.TUESDAY; part = PartOfDay.AFTERNOON; break;
		
		case 4: day = DayOfWeek.WEDNESDAY; part = PartOfDay.MORNING; break;
		case 5: day = DayOfWeek.WEDNESDAY; part = PartOfDay.AFTERNOON; break;
		
		case 6: day = DayOfWeek.THURSDAY; part = PartOfDay.MORNING; break;
		case 7: day = DayOfWeek.THURSDAY; part = PartOfDay.AFTERNOON; break;
		
		case 8: day = DayOfWeek.FRIDAY; part = PartOfDay.MORNING; break;
		case 9: day = DayOfWeek.FRIDAY; part = PartOfDay.AFTERNOON; break;
		
		}
		
	}
	
	/**
	 * Get which day of the week this is.
	 * @return  The day which this is
	 */
	public DayOfWeek getDay() {
		return day;
	}
	
	/**
	 * Get whether this is a morning or an afternoon. 
	 * @return  The part of this day
	 */
	public PartOfDay getPart() {
		return part;
	}
	
	/**
	 * Return a DayPart based on it's ordinal number.
	 * @param ordinal
	 * @return
	 */
	public static DayPart getDayPartByNumber(int ordinal) {
		
		DayPart dayPart;
		
		switch(ordinal) {
		case 0: dayPart = DayPart.MONDAY_MORNING; break;
		case 1: dayPart = DayPart.MONDAY_AFTERNOON; break;
		case 2: dayPart = DayPart.TUESDAY_MORNING; break;
		case 3: dayPart = DayPart.TUESDAY_AFTERNOON; break;
		case 4: dayPart = DayPart.WEDNESDAY_MORNING; break;
		case 5: dayPart = DayPart.WEDNESDAY_AFTERNOON; break;
		case 6: dayPart = DayPart.THURSDAY_MORNING; break;
		case 7: dayPart = DayPart.THURSDAY_AFTERNOON; break;
		case 8: dayPart = DayPart.FRIDAY_MORNING; break;
		case 9: dayPart = DayPart.FRIDAY_AFTERNOON; break;
		default: throw new IllegalArgumentException();
		}
		
		return dayPart;
	}
	
	/**
	 * Get a Morning-DayPart from a certain day of the week.
	 */
	public static DayPart getMorningOf(DayOfWeek dayOfWeek) {
		
		DayPart dayPart;
		
		switch(dayOfWeek) {
		case MONDAY: dayPart = DayPart.MONDAY_MORNING; break;
		case TUESDAY: dayPart = DayPart.TUESDAY_MORNING; break;
		case WEDNESDAY: dayPart = DayPart.WEDNESDAY_MORNING; break;
		case THURSDAY: dayPart = DayPart.THURSDAY_MORNING; break;
		case FRIDAY: dayPart = DayPart.FRIDAY_MORNING; break;
		default: throw new IllegalArgumentException();
		}
		
		return dayPart;
	}
	
	/**
	 * Get an Afternoon-DayPart from a certain day of the week.
	 */
	public static DayPart getAfternoonOf(DayOfWeek dayOfWeek) {
		
		DayPart dayPart;
		
		switch(dayOfWeek) {
		case MONDAY: dayPart = DayPart.MONDAY_AFTERNOON; break;
		case TUESDAY: dayPart = DayPart.TUESDAY_AFTERNOON; break;
		case WEDNESDAY: dayPart = DayPart.WEDNESDAY_AFTERNOON; break;
		case THURSDAY: dayPart = DayPart.THURSDAY_AFTERNOON; break;
		case FRIDAY: dayPart = DayPart.FRIDAY_AFTERNOON; break;
		default: throw new IllegalArgumentException();
		}
		
		return dayPart;
	}

}

/**
 * This enum defines the parts of a day a participant can attend at 
 * ICT Academie which are morning and afternoon.
 */
enum PartOfDay {
	
	MORNING, AFTERNOON
	
}