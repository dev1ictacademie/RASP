package conceptmodel;

import java.math.BigInteger;
import java.util.ArrayList;

/** 
 * This class represents an attendee at ICT Academie, someone who learns
 * programmming, web-design, ECDL or is a volunteer at the servicedesk.
 * 
 * @author ttimmermans
 * @version 02-03-2018
 */

public class Participant {
	
	private final BigInteger ID;// An numeric ID assigned to the participant
	
	private String firstName;  // The participant's first name
	private String lastName;   // The participant's last name
	private String namePrefix; /* An, optional, prefix preceding the LAST name
	                             (very common in dutch i.e. "Rob VAN DE Ven")*/
	
	private String phoneNumber;// The participant's telephone number
	private String username;   /* The username used by this participant to 
	                              login to the system */

	private Schedule schedule; /* A listing of DayParts the participant is 
	                            * expected to be present (also lists past and
	                            * future changes in this schedule) */
	
	private Presence presence; /* A listing of recorded DayParts and 
	                            * associated dates the participant was actually
	                            * present (or not when he/she was expected) */
	
	private double fee;    /* This amount indicates the volunteer fee that 
	                        * the participant has a right to for each DAYPART
	                        * he or she is present */
	
	private double travEx; /* This amount indicates the travel expenses 
	                        * reimbursement the participant has a right to for 
	                        * each different DAY he or she is present */
	
	
	/* 
	 * This list holds information about the date someone started or starts and
	 * stopped -or will stop- at ICT Academie. It is solely a list for the 
	 * fringe case where someone starts at ICT Academie, stops, is delisted 
	 * but then is registered and starting again relatively soon.
	 */
	private ArrayList<DateRange> enlistment;
	
	
	
	/**
	 * Constructor for when new Participant is created by user of program.
	 */
	public Participant(BigInteger ID) {
		this.ID = ID;
		// TODO stub
	}
	
	/**
	 * Constructor for when new Participant is NOT created by user of program
	 * but constructed by reading the values from database.
	 *//*
	public Participant() {
		// TODO stub
	}*/
	
}
