package nl.pameijer.ictacademie.rasp.persistencylayer;

import nl.pameijer.ictacademie.rasp.model.Student;

public class PersistencyLayer {

	public static void getMonthlyRegistration() {
		for (String[] entry: mockArray) {
			for (String field: entry) {

			}
		}
	}

	/*
	 * Mock array for week schedule(s)
	 *
	 * 2-dimensional array.
	 * Each row represents the week schedule of one registrant.
	 * Per registrant (per row) the format is as follows:
	 *
	 * participant ID, Surname, first name, middle name ... followed by ... 10 day parts.
	 * The first day part represents Monday morning, last part represents Friday afternoon.
	 * Each part either contains a null (meaning not present) or a spot name (usually a desk name).
	 * The spot name tells when this registrant is present on that particular day part on that
	 * particular spot.
	 *
	 * Example:
	 *
	 * 		null, null, null, null, "ICT_9", "ICT_9", "ICT_9", null, null, null
	 *
	 * Means:
	 *
	 * This registrant is present on spot place ICT_9 on Wednesday morning and afternoon and
	 * on Thursday morning at the same spot.
	 * 
	 * Purpose: The purpose of this array delivered by the database is to 
	 *          provide a week-overview of the students scheduled-in that
	 *          particular week and the places/spots which they then have.
	 */
	String[][] weekSchedules =
		{
				{"99991", "Pietersen", "Piet", null,
					"ICT_17", "ICT_17", null, null, "ICT_17", null, null, null, "ICT_17", null },
				{"99387", "Jansen", "Jan", null,
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Groot", "Kees", null,
					"ICT_13", "ICT_13", "ICT_13", "ICT_13", null, null, "ICT_13", "ICT_13", "ICT_13", "ICT_13" },
				{"99387", "Klein", "Hans", null,
					null, null, null, null, "ICT_9", "ICT_9", "ICT_9", null, null, null},
				{"99387", "Boer", "Cornelis", "den",
					null, null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Schoenlapper", "Frederik", null,
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Groot", "Kees", "de",
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Klein", "Hans", null,
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Hout", "Ton", "van",
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Vries", "Hans", "de",
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Appel", "Karel", null,
					"ICT_1", "ICT_2", "ICT_3", "ICT_4", "ICT_5", "ICT_6", "ICT_7", "ICT_8", null, "ICT_9" },
		};

	//// Temporary Mock-Data ////

	static String[][] mockArray =
		{
				{"99991", "Richthofen", "Baron", "von", "2018-05-24", "1", "5", "3"},
				{"99387", "Bunny", "Bugs", null, "2018-05-24", "2", "5", "3"},
				{"98456", "Milutinovich", "Lena", null, "2018-05-26", "1", "5", "7"},
				{"97678", "Donald", "Bob", "Mac", "2018-05-27", "2", "4", "3"},
		};


	public static void main(String[] args) {

	}

}
