package nl.pameijer.ictacademie.rasp.persistencylayer;

public class PersistencyLayer {
	
	public static void getMonthlyRegistration() {
		for (String[] entry: mockArray) {
			for (String field: entry) {
				
			}
		}
	}

	
	
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
