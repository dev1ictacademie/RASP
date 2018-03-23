package tests;

import java.time.LocalDate;
import java.util.HashMap;

import nl.pameijer.ictacademie.rasp.model.*;

public class TestStudentWithSchedule {

	public static void main(String[] args) {
		
		HashMap<DayPart, Place> schedule = new HashMap<>();
		schedule.put(DayPart.MONDAY_MORNING, Place.ICT_4);
		schedule.put(DayPart.MONDAY_AFTERNOON, Place.ICT_8);
		schedule.put(DayPart.TUESDAY_AFTERNOON, Place.ECDL_5);
		
		Student s1 = new Student("Donald", "Duck", LocalDate.of(2018, 4, 30),
				LocalDate.MAX, schedule);

	}

}
