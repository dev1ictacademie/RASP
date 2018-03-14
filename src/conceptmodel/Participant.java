package conceptmodel;

import java.math.BigInteger;
import java.util.ArrayList;

/** 
 * This class represents an attendee at ICT Academie, someone who learns
 * programmming, web-design, ECDL or is a volunteer at the servicedesk.
 * 
 * @author ttimmermans
 * @version 09-03-2018
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

	private Presence presence; /* A listing of recorded DayParts and 
	                            * associated dates the participant was actually
	                            * present (or not when he/she was expected) */
	
	private ArrayList<Schedule> schedules; // The participant's schedules
	
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
	public Participant(BigInteger ID, String firstName, String lastName,
			String namePrefix, String phoneNumber, String username,
			double fee, double travEx, DateRange currentRegistration) {
		
		Database.saveTo(); // Save to database with appropriate query
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.namePrefix = namePrefix;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.fee = fee;
		this.travEx = travEx;
		enlistment.add(currentRegistration);
	}
	
	/**
	 * Constructor for when new Participant is NOT created by user of program
	 * but constructed by reading the values from database.
	 *//*
	public Participant() {
		// TODO stub
	}*/
	
}
