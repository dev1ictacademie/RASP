package tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Place;
import nl.pameijer.ictacademie.rasp.model.Schedule;
import nl.pameijer.ictacademie.rasp.model.Student;

/**
 * Test class related to returning information about the places of students
 * during certain DayParts, listing available places at certain dates and
 * DayParts and so on. 
 * @author  ttimmermans
 * @version 26-06-2018
 */
public class TestPlaceAvailability {
	
	Model model = new Model();
	ObservableList<Student> studentList;
	
	TestPlaceAvailability() {
		model.loadDataWithScheduleAndID();
		studentList = model.getStudentList();
	}
	
	void printStudentList() {
		for (Student student: studentList) {
			System.out.println(student.getFName());
		}
	}
	
	/**
	 * Print the occupied places at a certain date and DayPart
	 */
	void showOccupiedPlaces(LocalDate date, DayPart dayPart) {
		for (Student student: studentList) {
			for (Schedule schedule: student.getSchedules()) {
				if (schedule.isDateWithinSchedule(date)) {
					for (DayPart part: schedule.getDayParts()) {
						if (part == dayPart) {
							System.out.println(schedule.getMap().get(part));
						}
					}
				}
			}
		}
	}

	/**
	 * Get a list of all available places for this exact date and dayPart.
	 */
	public List<Place> getAvailablePlaces(LocalDate date, DayPart dayPart) {

		List<Place> availablePlaces = new ArrayList<>();

		List<Place> occupiedPlaces = new ArrayList<>();

        // Make a list with all places that are occupied this dayPart and date
		for (Student student: studentList) {
			if (student.getActiveSchedule(date) == null) {
				continue;
			}
			Map<DayPart, Place> map = student.getActiveSchedule(date).getMap();
			for (DayPart part: map.keySet()) {
				if (part == dayPart) {
					occupiedPlaces.add(map.get(part));
				}
			}
		}

		// Loop over ALL existing places and add them to the available-list if 
		// they are NOT found in the occupied-list.
		for (Place place: Place.values()) {
			boolean occupied = false;
			for (Place occupiedPlace: occupiedPlaces) {
				if (place == occupiedPlace) {
					occupied = true;
					break;
				}
			}
			if (!occupied) {
				availablePlaces.add(place);
			}
		}

		return availablePlaces;
	}
	
	/**
	 * main. run some tests.
	 */
	public static void main(String[] args) {
		
		TestPlaceAvailability tpa = new TestPlaceAvailability();
		//tpa.printStudentList();
		
		System.out.println();
		
		// 27 april 2018 is a friday
		tpa.showOccupiedPlaces(LocalDate.of(2018, 4, 27), DayPart.FRIDAY_AFTERNOON);
		
		System.out.println();
		
		// 30 april 2018 is a monday
		tpa.showOccupiedPlaces(LocalDate.of(2018, 4, 30), DayPart.MONDAY_AFTERNOON);
		
		System.out.println();
		
		// At Monday 18 June Place 3, 4, 6 and 16 are occupied 
		System.out.println(tpa.getAvailablePlaces(LocalDate.of(2018, 06, 18), DayPart.MONDAY_MORNING));
		
	}

}
