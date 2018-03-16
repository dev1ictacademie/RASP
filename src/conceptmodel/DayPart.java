package conceptmodel;

import java.time.DayOfWeek;

/** 
 * This enum lists the ten different DayParts during the week at which 
 * participants can be attending at ICT Academie. A DayPart is a non-weekend 
 * day AND is either a morning or an afternoon.
 * 
 * @author ttimmermans
 * @version 06-03-2018
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

}

/**
 * This enum defines the parts of a day a participant can attend at 
 * ICT Academie which are morning and afternoon.
 */
enum PartOfDay {
	
	MORNING, AFTERNOON
	
}
