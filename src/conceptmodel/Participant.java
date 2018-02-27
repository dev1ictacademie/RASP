package conceptmodel;

import java.math.BigInteger;

/** 
 * This class represents an attendee at ICT Academie, someone who learns
 * programmming, web-design, ECDL or is a volunteer at the servicedesk.
 * 
 * @author ttimmermans
 * @version 27-02-2018
 */

public class Participant {
	
	private String firstName;  // The participant's first name
	private String lastName;   // The participant's last name
	private String namePrefix; /* An, optional, prefix preceding the LAST name
	                             (very common in dutch i.e. "Rob VAN DE Ven")*/

	private String phoneNumber;// The participant's telephone number
	private BigInteger ID;     // A number assigned to the participant as an ID
	private String username;   /* The username used by this participant to 
	                              login to the system */

	private Schedule schedule; /* A listing of DayParts the participant is 
	                            * expected to be present (also lists past and
	                            * future changes in this schedule) */
	
	private Presence presence; /* A listing of recorded DayParts and 
	                            * associated dates the participant was actually
	                            * present (or not) */
	
	private double fee;    /* This amount indicates the volunteer fee that 
	                        * the participant has a right to for each DAYPART
	                        * he or she is present */
	
	private double travEx; /* This amount indicates the travel expenses 
	                        * reimbursement the participant has a right to for 
	                        * each different DAY he or she is present */
	
	//startdate                           LocalDate               // The date the participant has started at ICT Academie (first day)
	//enddate                             LocalDate               // The date the participant has stopped at ICT Academie (last day)

}
