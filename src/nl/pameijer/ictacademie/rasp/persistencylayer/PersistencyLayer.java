package nl.pameijer.ictacademie.rasp.persistencylayer;

import java.util.ArrayList;

import nl.pameijer.ictacademie.rasp.model.Schedule;
import nl.pameijer.ictacademie.rasp.model.Student;

public class PersistencyLayer {
	
	/* Arrays needed at application start to be delivered by the database:

       - Array with students
       - Array with schedules
       
     */
		
	/* 
	 * A student from this array has a studentID, a last name, a first name
	 * and a last name prefix.
	 */
	static String[][] studentsMockArray = {
			{"101", "Richthofen", "Baron", "von"},
			{"119", "Pietersen", "Piet", null},
			{"162", "Jovanovich", "Lena", null},
			{"184", "Murdo", "Bob", "Mac"}	
	};
	
	/*
	 * A schedule from this array has a studentID (indicating which student 
	 * this schedule belongs to), a starting date, an end date,
     * a place value for monday morning, a place value for monday afternoon,
	 * a place value for tuesday morning, a place value for tuesday afternoon, 
	 * a place value for wednesday morning, a place value for wednesday afternoon, 
	 * a place value for thursday morning, a place value for thursday afternoon, 
	 * a place value for friday morning and a place value for friday afternoon.
	 */ 
	static String[][] schedulesMockArray = {
			{"119", "2018-02-06", "9999-12-31", "6", "6", null, null, "1", "1", null, null, null, null},
			{"162", "2018-04-19", "2018-05-31", null, null, "19", "8", null, null, "14", "14", null, null},
			{"162", "2018-06-01", "9999-12-31", null, null, "19", "8", null, null, null, null, "16", "16"},
			{"101", "2017-11-29", "9999-12-31", "3", "3", null, null, "5", "7", null, null, null, null},
			{"184", "2017-06-21", "2018-03-14", "11", "11", null, null, null, null, null, null, "9", "5"},
			{"184", "2018-03-15", "9999-12-31", null, null, null, "18", null, null, null, null, null, "18"}
	};
	
	/**
	 * Construct a list of students based on input from a two-dimensional array
	 * of strings.
	 * 
	 * @param arr       The two-dimensional array of strings describing a 
	 *                  number of students.
	 */
	public static ArrayList<Student> constructStudentList(String[][] arr) {
		ArrayList<Student> studentList = new ArrayList<>();
		for (String[] entry: arr) {
			String[] attribs = new String[4];
			int i = 0;
			for (String studentProperty: entry) {
				attribs[i] = studentProperty;
				i++;
			}
			studentList.add(new Student(attribs[2], attribs[1], attribs[3], attribs[0]));
		}
		return studentList;
	}
	
	/**
	 * Construct any number of schedules based on input from a two-dimensional 
	 * array of strings (and assign them to the appropriate student).
	 * 
	 * @param arr       The two-dimensional array of strings describing a 
	 *                  number of schedules.
	 *             
	 * @param students  The list with Student objects to which the schedules
	 *                  need to be assigned.       
	 */
	public static void constructSchedules(String[][] arr, ArrayList<Student> students) {
		for (String[] entry: arr) {
            Schedule schedule = new Schedule(entry);
            for (Student student: students) {
            	if (student.hasThisID(entry[0])) {
            		student.addSchedule(schedule);
            	}
            }
		}
	}
	
	
	/* Student list made based on studentsMockArray for testing */ 
	static ArrayList<Student> testList = constructStudentList(studentsMockArray);
	
	
	public static void main(String[] args) {
		
		// test the studentList by printing the student's properties
		for (Student stu: testList) {
			System.out.println(stu.getId() + " " + stu.getLName() + " " + 
		                       stu.getFName() + " " + stu.getNamePrefix());
		}
		
		System.out.println();
		
		// test the constructSchedules method by using the schedulesMockArray
		// to create schedules and assign them to the students from the testList
		constructSchedules(schedulesMockArray, testList);
		
	}
	
	
	
	
	
	
	//////////////////////////////////////////////
	
	/*
	 * Mock array for week schedule(s)
	 *
	 * 2-dimensional array.
	 * Each row represents the week schedule of one registrant.
	 * Per registrant (per row) the format is as follows:
	 *
	 * participant ID, Surname, first name, middle name ... followed by ... 10 day parts.
	 * The first day part represents Monday morning, last part represents Friday afternoon.
	 * Each part either contains a null (meaning not present) or a spot name (usually a desk name).
	 * The spot name tells when this registrant is present on that particular day part on that
	 * particular spot.
	 *
	 * Example:
	 *
	 * 		null, null, null, null, "ICT_9", "ICT_9", "ICT_9", null, null, null
	 *
	 * Means:
	 *
	 * This registrant is present on spot place ICT_9 on Wednesday morning and afternoon and
	 * on Thursday morning at the same spot.
	 *
	 * Purpose: The purpose of this array delivered by the database is to
	 *          provide a week-overview of the students scheduled-in that
	 *          particular week and the places/spots which they then have.
	 */
	public static String[][] weekSchedules =
		{
				{"99991", "Pietersen", "Piet", null,
					"ICT_1", null, "ICT_2", null, "ICT_3", null, "ICT_4", null, "ICT_5", null },
				{"99387", "Jansen", "Jan", null,
					null, "ICT_1", null, "ICT_2", null, "ICT_3", null, "ICT_4", null, "ICT_5" },
				{"99387", "Groot", "Kees", null,
					"ICT_13", "ICT_11", "ICT_11", "ICT_1", null, null, "ICT_11", "ICT_13", "ICT_3", "ICT_13" },
				{"99387", "Klein", "Hans", null,
					null, null, null, null, null, null, null, null, null, null},
				{"99387", "Boer", "Cornelis", "den",
					null, null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Schoenlapper", "Frederik", null,
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Groot", "Kees", "de",
					"ICT_1", null, null, null, null, "ICT_5", "ICT_5", null, null, null },
				{"97", "Klein", "Hans", null,
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, null },
				{"99387", "Hout", "Ton", "van",
					"ICT_1", null, null, null, null, "ICT_3", null, null, null, "ICT_17" },
				{"987", "Vries", "Hans", "de",
					"ICT_1", null, null, null, "ICT_17", null, null, null, null, "ICT_17" },
				{"99387", "Appel", "Karel", null,
					"ICT_1", "ICT_2", "ICT_3", "ICT_4", "ICT_5", "ICT_6", "ICT_7", "ICT_8", "ICT_9", "ICT_10" },
		};




}
