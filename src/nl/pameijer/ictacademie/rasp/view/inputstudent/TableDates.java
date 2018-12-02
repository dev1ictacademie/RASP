package nl.pameijer.ictacademie.rasp.view.inputstudent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class TableDates {

	static LocalDate now = LocalDate.now();

	static LocalDate mostRecentMonday = getMostRecentMonday(now);

	static LocalDate[] thisWeekDates = new LocalDate[5];


	public static String[] thisWeekStrings = new String[5];




	static {

		for (int i = 0; i < 5; i++) {
			thisWeekDates[i] = mostRecentMonday.plusDays(i);
			thisWeekStrings[i] = formatDate(thisWeekDates[i]);
		}
	}

	/**
	 * Get the most recent monday since date or if date itself is a monday
	 * return it's own value.
	 *
	 * @param date
	 * @return
	 */
	static LocalDate getMostRecentMonday(LocalDate date) {
		return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	}

	/*
	 * Change the currentWeek. That is, calculate all dates of weekdays
	 * from a past or future week, assign them to the LocalDate array and
	 * then also update the formatted String array with the new dates.
	 *
	 * @param n  the number of weeks to jump forward or backwards (use
	 *           negative numbers to go backwards).
	 */
	public static void changeWeek(int n) {
		for (int i = 0; i < thisWeekDates.length; i++) {
			thisWeekDates[i] = thisWeekDates[i].plusDays(n * 7);
			thisWeekStrings[i] = formatDate(thisWeekDates[i]);
		}
	}

	/**
	 * Format a LocalDate instance to a string representation.
	 */
	static String formatDate(LocalDate dateToFormat) {

		String date = "";

		switch (dateToFormat.getDayOfWeek()) {
		case MONDAY:
			date += "Ma";
			break;
		case TUESDAY:
			date += "Di";
			break;
		case WEDNESDAY:
			date += "Wo";
			break;
		case THURSDAY:
			date += "Do";
			break;
		case FRIDAY:
			date += "Vr";
			break;
		default:
			throw new IllegalArgumentException();
		}

		date += " " + dateToFormat.getDayOfMonth() + " ";

		switch (dateToFormat.getMonth()) {
		case JANUARY:
			date += "Jan";
			break;
		case FEBRUARY:
			date += "Feb";
			break;
		case MARCH:
			date += "Mar";
			break;
		case APRIL:
			date += "Apr";
			break;
		case MAY:
			date += "Mei";
			break;
		case JUNE:
			date += "Jun";
			break;
		case JULY:
			date += "Jul";
			break;
		case AUGUST:
			date += "Aug";
			break;
		case SEPTEMBER:
			date += "Sep";
			break;
		case OCTOBER:
			date += "Okt";
			break;
		case NOVEMBER:
			date += "Nov";
			break;
		case DECEMBER:
			date += "Dec";
			break;
		default:
			throw new IllegalArgumentException();
		}
		// TODO year format only for this century
		String yearFormat = String.valueOf(dateToFormat.getYear() - 2000);
		date += "'" + yearFormat;

		return date;
	}

	/**
	 * Get this week's dates.
	 */
	public static LocalDate[] getThisWeekDates() {
		return thisWeekDates;
	}

	/**
	 * Get the week dates of the week's of the corresponding localdate
	 * @param localDate
	 * @return array of LocalDates
	 */
	public static LocalDate[] getThisWeekDates(LocalDate localDate) {

		mostRecentMonday = getMostRecentMonday(localDate);
		for (int i = 0; i < 5; i++) {
			thisWeekDates[i] = mostRecentMonday.plusDays(i);
			thisWeekStrings[i] = formatDate(thisWeekDates[i]);
		}
		return thisWeekDates;

	}
}
