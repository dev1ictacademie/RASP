package tests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Holidays {
	
	ArrayList<LocalDate> holidays = new ArrayList<>();
	
	/**
	 * Make a list with dates of the holidays for a given year.
	 * @param year
	 */
	public void getHolidaysForYear(int year) {
		holidays.clear();
		
		addFixedDates(year);
		holidays.add(getKingsDay(year));
		
		LocalDate Easter = getEasterDate(year);
		holidays.add(Easter);                     // Easter Sunday
		holidays.add(Easter.plusDays(1));         // Easter Monday
		holidays.add(Easter.plusDays(39));        // Ascension Day
		holidays.add(Easter.plusDays(49));        // Pentecost Sunday
		holidays.add(Easter.plusDays(50));        // Pentecost Monday
		
		
		Collections.sort(holidays);
		
		for (LocalDate holiday: holidays) {
			System.out.println(holiday);
		}
		
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
	
	private LocalDate getEasterDate(int year) {
		
		/* 
		 * Easter date (Gregorian/western easter) calculated using the famous 
		 * Meeus/Jones/Butcher algorithm. 
		 */
		
		if (year < 1583) {
			throw new IllegalArgumentException("Invalid year. Must be >= 1583");
			// This algorithm is for gregorian calendar which began in 1583
		}

		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = (19 * a + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + 2 * e + 2 * i - h - k) % 7;
		int m = (a + 11 * h + 22 * l) / 451;
		int n = (h + l - 7 * m + 114) / 31;
		int p = (h + l - 7 * m + 114) % 31;

		return LocalDate.of(year, n, p + 1);

	}

	public static void main(String[] args) {
		Holidays holidays = new Holidays();
        holidays.getHolidaysForYear(2018);
	}

}
