package tests;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class TestWeekNumbers {

	public static void main(String[] args) {
		
		ZoneId z = ZoneId.of( "Europe/Paris" /* "Pacific/Auckland" */);
		LocalDate now = LocalDate.now( z );

		System.out.println(now);


		LocalDate NL_Date = LocalDate.of(2017, 1, 2);
		
		
		// ISO-8601 definition of week numbers: 
		// A week starts on Monday and the first week has a minimum of 4 days.

		// 1 January 2017 is week nr. 52 according to ISO-8601
		// 2 January 2017 is week nr. 1 according to ISO-8601

		// see some calendar online for comparison/reference



		WeekFields wf = WeekFields.of(Locale.getDefault()) ;

		System.out.println(Locale.getDefault());

		System.out.println(wf);




		/* Represent the idea of this locale’s definition of week number 
		 * as a `TemporalField`. */ 
		TemporalField weekNum = wf.weekOfWeekBasedYear(); 

		System.out.println(weekNum);



		System.out.println(now.get(weekNum));


		System.out.println(NL_Date.get(weekNum));
		
	}
	
}