package tests;

import java.time.LocalDate;
import javafx.collections.ObservableList;
import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Schedule;
import nl.pameijer.ictacademie.rasp.model.Student;

/**
 * Test class related to returning information about the places of students
 * during certain DayParts, listing available places at certain dates and
 * DayParts and so on. 
 * @author  ttimmermans
 * @version 19-06-2018
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

	public static void main(String[] args) {
		
		TestPlaceAvailability tpa = new TestPlaceAvailability();
		//tpa.printStudentList();
		
		System.out.println();
		
		// 27 april 2018 is a friday
		tpa.showOccupiedPlaces(LocalDate.of(2018, 4, 27), DayPart.FRIDAY_AFTERNOON);
		
		System.out.println();
		
		// 30 april 2018 is a monday
		tpa.showOccupiedPlaces(LocalDate.of(2018, 4, 30), DayPart.MONDAY_AFTERNOON);
		
		
	}

}
