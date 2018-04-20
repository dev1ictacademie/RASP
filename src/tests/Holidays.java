package tests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class Holidays {
	
	ArrayList<LocalDate> holidays = new ArrayList<>();
	
	/**
	 * Make a list with dates of the holidays for a given year.
	 * @param year
	 */
	public void getHolidaysForYear(int year) {
		holidays.clear();
	}
	
	private void addFixedDates(int year) {
		holidays.add(LocalDate.of(year, 1, 1));   // New year's day
		holidays.add(LocalDate.of(year, 5, 5));   // Dutch liberation day
		holidays.add(LocalDate.of(year, 12, 25)); // Christmas
		holidays.add(LocalDate.of(year, 12, 26)); // Boxing day
	}
	
	private LocalDate getKingsDay(int year) {
		LocalDate kingsDay = LocalDate.of(year, 4, 27);
		if (kingsDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
			// If april 27 is a sunday King's Day is celebrated the day before.
			kingsDay = LocalDate.of(year, 4, 26);
		}
		return kingsDay;
	}

	public static void main(String[] args) {

	}

}
