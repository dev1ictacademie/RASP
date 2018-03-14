package conceptmodel;

import java.util.ArrayList;

/** 
 * This class represents a historical listing of the day parts with associated 
 * dates an attendee was present at ICT Academie.
 * 
 * @author ttimmermans
 * @version 06-03-2018
 */

public class Presence {
	
	private ArrayList<DatedDayPart> presence;
	
	/**
	 * Add a dayPart (with a date) to the participant's presence-listing.
	 */
	public void addDatedDayPart(DatedDayPart datedDayPart) {
		presence.add(datedDayPart);
	}
	
	/**
	 * Remove a dayPart (with a date) from the participant's presence-listing.
	 */
	public void removeDatedDayPart(DatedDayPart datedDayPart) {
		presence.remove(datedDayPart);
	}

}
