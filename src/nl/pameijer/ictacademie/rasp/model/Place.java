package nl.pameijer.ictacademie.rasp.model;

/**
 * This enum represents a workplace for an attendee at ICT Academie.
 * It lists the available spots (and their corresponding numbers)
 * at ICT, ECDL and ServiceDesk. At ICT these numbers also are actual
 * seating places.
 *
 * @author ttimmermans
 * @version 23-03-2018
 */

public enum Place {

	ICT_1, ICT_2, ICT_3, ICT_4, ICT_5, ICT_6,
	ICT_7, ICT_8, ICT_9, ICT_10, ICT_11, ICT_12,
	ICT_13, ICT_14, ICT_15, ICT_16, ICT_17, ICT_18,

	ECDL_1, ECDL_2, ECDL_3, ECDL_4, ECDL_5, ECDL_6,

	SERVICEDESK_1, SERVICEDESK_2, SERVICEDESK_3;

	private int spot; // indicates the numbered workplace/seating place
	private String type; // a string describing the type of this place
	
	/**
	 * Constructor. Assign spot numbers (ICT_2 Should have spot number 2,
	 * ECDL_5 spot number 5 and so on).
	 */
	private Place() {
		assignSpot();
		assignType();
	}

	/**
	 * Assign the spot number by 'looking' at the digits after the underscore.
	 * Get the particular enum's name (exactly as declared), strip anything
	 * from this string preceding the digits after the underscore and then
	 * parse the remaining result as an integer.
	 */
	private void assignSpot() {

		int underscore = this.name().indexOf("_");
	 	spot = Integer.parseInt(this.name().substring(underscore + 1,
	 			this.name().length()));
	}
	
	/**
	 * Assign a string describing the type of this place (i.e. ICT or ECDL).
	 * Do this by 'looking' at the string before the underscore.
	 * Get the particular enum's name (exactly as declared) and then strip the
	 * underscore (and everything thereafter) from this string.
	 */
	private void assignType() {
		
			int underscore = this.name().indexOf("_");
			type = this.name().substring(0, underscore);
	}
}

