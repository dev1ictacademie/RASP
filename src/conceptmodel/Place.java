package conceptmodel;

/** 
 * This enum represents a workplace for an attendee at ICT Academie.
 * It lists the available spots (and their corresponding numbers)
 * at ICT, ECDL and ServiceDesk. At ICT these numbers also are actual
 * seating places.
 * 
 * @author ttimmermans
 * @version 27-02-2018
 */

public enum Place {

	ICT_1, ICT_2, ICT_3, ICT_4, ICT_5, ICT_6, 
	ICT_7, ICT_8, ICT_9, ICT_10, ICT_11, ICT_12,
	ICT_13, ICT_14, ICT_15, ICT_16, ICT_17, ICT_18,
	
	ECDL_1, ECDL_2, ECDL_3, ECDL_4, ECDL_5, ECDL_6, 
	
	SERVICEDESK_1, SERVICEDESK_2, SERVICEDESK_3;
	
	private int spot; // indicates the numbered workplace/seating place
	
	/**
	 * Constructor
	 */
	private Place() {
		assignSpot();
	}
	
	
	/**
	 * Assign spot by 'looking' at the digit after the underscore
	 */
	private void assignSpot() {
		for (Place place: Place.values()) {

		 	for (char ch: place.name().toCharArray()) {
		 		if (Character.isDigit(ch)) {
		 			
		 		}
		 	}
		}
	}
}
