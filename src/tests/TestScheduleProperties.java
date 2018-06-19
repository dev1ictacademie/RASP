package tests;

import javafx.collections.ObservableList;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Schedule;
import nl.pameijer.ictacademie.rasp.model.Student;

public class TestScheduleProperties {
	
	Model model = new Model();
	ObservableList<Student> studentList;
	
	TestScheduleProperties() {
		model.loadDataWithScheduleAndID();
		studentList = model.getStudentList();
	}

	public static void main(String[] args) {
		
		TestScheduleProperties tsp = new TestScheduleProperties();
		
		Schedule mostRecentSchedule = null;
		
		for (Student stu: tsp.studentList) {
			if (stu.hasThisID("189")) {
				
				// get last schedule from list
				mostRecentSchedule = stu.getSchedules().get( stu.getSchedules().size() -1 );
				
			}
		}
		
		System.out.println(mostRecentSchedule.getStartDate());
		
		System.out.println(mostRecentSchedule.getEndDate());

	}
	
	/**
	 * Test method. Get available places for 
	 */
	public void getCurrent() {
		
	}

}
