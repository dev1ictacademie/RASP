package conceptmodel;

/** 
 * This enum lists the five different presence statuses that can be assigned
 * to a participant at a certain DayPart.
 * 
 * @author ttimmermans
 * @version 02-03-2018
 */

public enum PresenceStatus {
	
	PRESENT,               // Participant has attended as usual
	
	NOT_PRESENT,           // Participant has not attended at a DayPart he or 
	                       // she was expected to be present without notice OR 
	                       // has called in sick/unable to come within 24 hours
	
	LEAVE_OF_ABSENCE,      // Participant took time off, is on vacation etc.
	
	SICK,                  // Participant is sick (if reported sick within the
	                       // last 24 hours this is NOT the suitable status; 
	                       // use NOT_PRESENT instead)
	
	CLOSED                 // ICT Academie was closed at this time, hence there 
                           // was no attendance by participant
	
}
