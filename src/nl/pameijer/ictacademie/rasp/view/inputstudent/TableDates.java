package nl.pameijer.ictacademie.rasp.view.inputstudent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class TableDates {
	
	static LocalDate now = LocalDate.now();

	static LocalDate mostRecentMonday = 
			now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	
	static String[] thisWeek = new String[5];

	static {   
		for (int i = 0; i < 5; i++) {
			thisWeek[i] = formatDate(mostRecentMonday.plusDays(i));
		}
	}
	
	/**
	 * Format a LocalDate instance to a string representation.
	 */
	static String formatDate(LocalDate dateToFormat) {

		String date = "";

		switch(dateToFormat.getDayOfWeek()) {
		case MONDAY: date += "Ma"; break;
		case TUESDAY: date += "Di"; break;
		case WEDNESDAY: date += "Wo"; break;
		case THURSDAY: date += "Do"; break;
		case FRIDAY: date += "Vr"; break;
		default: throw new IllegalArgumentException();
		}

		date += " " + dateToFormat.getDayOfMonth() + " ";

		switch(dateToFormat.getMonth()) {
		case JANUARY: date += "Jan"; break;
		case FEBRUARY: date += "Feb"; break;
		case MARCH: date += "Mar"; break;
		case APRIL: date += "Apr"; break;
		case MAY: date += "Mei"; break;
		case JUNE: date += "Jun"; break;
		case JULY: date += "Jul"; break;
		case AUGUST: date += "Aug"; break;
		case SEPTEMBER: date += "Sep"; break;
		case OCTOBER: date += "Okt"; break;
		case NOVEMBER: date += "Nov"; break;
		case DECEMBER: date += "Dec"; break;
		default: throw new IllegalArgumentException();
		}

		return date;
	}

}
