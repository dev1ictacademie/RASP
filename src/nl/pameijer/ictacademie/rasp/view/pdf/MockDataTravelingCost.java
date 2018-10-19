package nl.pameijer.ictacademie.rasp.view.pdf;


public class MockDataTravelingCost
{
	static String[] month = {"januari", "februari", "maart", "april", "mei", "juni"};
	static String[] year = {"2015", "2016", "2017", "2018", "2019"};
	static String[] name = {"Donald Duck", "Mickey Mouse", "Katrien Duck", "Billie Turf"};
	static String[] id = {"3165", "500", "1455", "15"};
	static String[] total = {"31,65", "50,00", "145,55", "6,40"};
	static String[] iban = {"NL30 INGB 0001234567", "NL48 RABO 9876543210", "NL48 ABN 9876543210", "NL30 INGB 0007659991"};

	public static String getMonths()
	{
		return month[0];
	}


	public static String getYears()
	{
		return year[3];
	}


	public static String[] getNames()
	{
		return name;
	}


	public static String[] getTotal()
	{
		return total;
	}

	public static String[] getId()
	{
		return id;
	}

	public static String[] getIban()
	{
		return iban;
	}

}// end class MockDataTravelingCost
