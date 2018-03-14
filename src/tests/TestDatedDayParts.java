package tests;

import conceptmodel.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

/**
 * Compare for example with https://www.timeanddate.com/calendar/?year=2018
 * 
 * @author ttimmermans
 * @version 13-03-2018
 */

public class TestDatedDayParts {
	
	public static void main(String args[]) {
		
		LocalDate now = LocalDate.now();
		System.out.println("now: " + now);

	 	int lengthOfMonth = now.lengthOfMonth();
	 	System.out.println("lengthOfMonth: " + lengthOfMonth);
	 	
	 	
	 	System.out.println();
	 	
	 	
	 	// Test generation of DatedDayParts from THIS MONTH
	 	ArrayList<DatedDayPart> dayPartsThisMonth = new ArrayList<>();
	 	
	 	for (int i = 1; i <= lengthOfMonth; i++) {
	 		LocalDate date = now.withDayOfMonth(i);
	 		for (DayPart dayPart: DayPart.values()) {
	 			if (dayPart.getDay().equals(date.getDayOfWeek())) {
	 				dayPartsThisMonth.add(new DatedDayPart(date, dayPart));
	 			}
	 		}
	 	}
	 	
	 	System.out.println("DatedDayParts from THIS MONTH: ");
	 	
	 	for (DatedDayPart datedDayPart: dayPartsThisMonth) {
	 		System.out.println(datedDayPart.getDate() + " " + 
	 				datedDayPart.getDayPart());
	 	}
	 	
	 	
	 	System.out.println("\n" + "\n");
	 	
	 	
	 	// Test generation of DatedDayParts from THIS WEEK
	 	ArrayList<DatedDayPart> dayPartsThisWeek = new ArrayList<>();
	 	
 		LocalDate mostRecentMonday = 
 				now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	 	
	 	System.out.println("mostRecentMonday: " + mostRecentMonday + "\n");
	 	
	 	for (int i = 0; i < 5; i++) {
	 		LocalDate date = mostRecentMonday.plusDays(i);
	 		for (DayPart dayPart: DayPart.values()) {
	 			if (dayPart.getDay().equals(date.getDayOfWeek())) {
	 				dayPartsThisWeek.add(new DatedDayPart(date, dayPart));
	 			}
	 		}
	 	}
	 	
	 	System.out.println("DatedDayParts from THIS WEEK: ");
	 	
	 	for (DatedDayPart datedDayPart: dayPartsThisWeek) {
	 		System.out.println(datedDayPart.getDate() + " " + 
	 				datedDayPart.getDayPart());
	 	}
	 	
	}

}