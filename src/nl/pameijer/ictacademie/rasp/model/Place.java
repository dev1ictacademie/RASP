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

	/**
	 * Return a Place instance based on a string description.
	 */
	public static Place getPlaceByString(String description) {
		
		Place place;
		
		switch(description) {
		case "ICT_1": place = Place.ICT_1; break; 
		case "ICT_2": place = Place.ICT_2; break;
		case "ICT_3": place = Place.ICT_3; break;
		case "ICT_4": place = Place.ICT_4; break;
		case "ICT_5": place = Place.ICT_5; break;
		case "ICT_6": place = Place.ICT_6; break;
		case "ICT_7": place = Place.ICT_7; break;
		case "ICT_8": place = Place.ICT_8; break;
		case "ICT_9": place = Place.ICT_9; break;
		case "ICT_10": place = Place.ICT_10; break;
		case "ICT_11": place = Place.ICT_11; break;
		case "ICT_12": place = Place.ICT_12; break;
		case "ICT_13": place = Place.ICT_13; break;
		case "ICT_14": place = Place.ICT_14; break;
		case "ICT_15": place = Place.ICT_15; break;
		case "ICT_16": place = Place.ICT_16; break;
		case "ICT_17": place = Place.ICT_17; break;
		case "ICT_18": place = Place.ICT_18; break;

		case "ECDL_1": place = Place.ECDL_1; break;
		case "ECDL_2": place = Place.ECDL_2; break;
		case "ECDL_3": place = Place.ECDL_3; break;
		case "ECDL_4": place = Place.ECDL_4; break;
		case "ECDL_5": place = Place.ECDL_5; break;
		case "ECDL_6": place = Place.ECDL_6; break;

		case "SERVICEDESK_1": place = Place.SERVICEDESK_1; break;
		case "SERVICEDESK_2": place = Place.SERVICEDESK_2; break;
		case "SERVICEDESK_3": place = Place.SERVICEDESK_3; break;
		
		default: throw new IllegalArgumentException();
		}
		
		return place;
	}
	
	/**
	 * Return a Place instance based on sequential numbering.
	 */
	public static Place getPlaceByNumber(int number) {
		
		Place place;
		
		switch(number) {
		case 1: place = Place.ICT_1; break; 
		case 2: place = Place.ICT_2; break;
		case 3: place = Place.ICT_3; break;
		case 4: place = Place.ICT_4; break;
		case 5: place = Place.ICT_5; break;
		case 6: place = Place.ICT_6; break;
		case 7: place = Place.ICT_7; break;
		case 8: place = Place.ICT_8; break;
		case 9: place = Place.ICT_9; break;
		case 10: place = Place.ICT_10; break;
		case 11: place = Place.ICT_11; break;
		case 12: place = Place.ICT_12; break;
		case 13: place = Place.ICT_13; break;
		case 14: place = Place.ICT_14; break;
		case 15: place = Place.ICT_15; break;
		case 16: place = Place.ICT_16; break;
		case 17: place = Place.ICT_17; break;
		case 18: place = Place.ICT_18; break;

		case 19: place = Place.ECDL_1; break;
		case 20: place = Place.ECDL_2; break;
		case 21: place = Place.ECDL_3; break;
		case 22: place = Place.ECDL_4; break;
		case 23: place = Place.ECDL_5; break;
		case 24: place = Place.ECDL_6; break;

		case 25: place = Place.SERVICEDESK_1; break;
		case 26: place = Place.SERVICEDESK_2; break;
		case 27: place = Place.SERVICEDESK_3; break;
		
		default: throw new IllegalArgumentException();
		}
		
		return place;
	}

}

