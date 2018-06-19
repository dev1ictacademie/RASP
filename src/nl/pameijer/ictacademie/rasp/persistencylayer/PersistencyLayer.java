package nl.pameijer.ictacademie.rasp.persistencylayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Place;
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
	public static String[][] studentsMockArray = {
			{"101", "Richthofen", "Baron", "von"},
			{"119", "Pietersen", "Piet", null},
			{"162", "Jovanovich", "Lena", null},
			{"184", "Murdo", "Bob", "Mac"},
			{"189", "Drump", "Tonald", null},
			{"191", "Al-Hashimi", "Salima", null},
			{"208", "Steen", "Thijs", "van der"},
			{"217", "Monroe", "Melanie", null},
			{"228", "Hummermans", "Humphry", null},
			{"247", "Gomez", "Esperanza", null},
			{"263", "Davidson", "Charlie", null}
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
	public static String[][] schedulesMockArray = {
			{"119", "2018-02-06", "9999-12-31", "ICT_6", "ICT_6", null, null, "ICT_1", "ICT_1", null, null, null, null},
			{"162", "2018-04-19", "2018-05-31", null, null, "ICT_8", "ECDL_1", null, null, "ICT_14", "ICT_14", null, null},
			{"162", "2018-06-01", "9999-12-31", null, null, "ICT_8", "ECDL_1", null, null, null, null, "ICT_16", "ICT_16"},
			{"101", "2017-11-29", "9999-12-31", "ICT_3", "ICT_3", null, null, "ICT_5", "ICT_7", null, null, null, null},
			{"184", "2017-06-21", "2018-03-14", "ICT_11", "ICT_11", null, null, null, null, null, null, "ICT_9", "ICT_5"},
			{"184", "2018-03-15", "9999-12-31", null, null, null, "ICT_18", null, null, null, null, null, "ICT_18"},
			{"189", "2018-02-12", "2018-05-23", null, null, "ICT_14", "ECDL_3", null, null, null, null, "ICT_7", "ICT_7"},
			{"189", "2018-05-24", "2018-05-24", null, null, null, null, null, null, "ICT_2", "ICT_2", null, null},
			{"189", "2018-05-25", "9999-12-31", "ICT_16", "ICT_16", null, null, null, null, null, null, "ICT_4", "ICT_4"},
			{"191", "2017-09-19", "9999-12-31", null, null, "ICT_13", "ICT_13", null, null, null, null, "ICT_13", "ICT_13"},
			{"208", "2018-05-02", "2018-06-06", null, null, null, null, null, "ICT_10", null, null, null, "ICT_10"},
			{"208", "2018-06-07", "9999-12-31", null, null, null, "ICT_17", null, null, null, "ICT_17", null, null},
			{"217", "2018-01-17", "9999-12-31", null, null, null, null, "ICT_11", "ICT_11", null, null, null, null},
			{"228", "2018-03-22", "9999-12-31", "ICT_4", "ICT_4", null, null, null, null, "ICT_6", "ICT_6", null, null},
			{"247", "2018-04-26", "2018-06-13", null, "ICT_12", null, null, null, null, "ICT_9", "ICT_9", null, null},
			{"247", "2018-06-14", "9999-12-31", null, "ICT_8", null, null, null, null, null, null, "ICT_3", "ICT_3"},
			{"263", "2017-10-24", "9999-12-31", null, null, "ICT_2", "ICT_2", null, null, "ICT_15", "ICT_15", null, null}
	};
	
	/**
	 * Construct a list of students based on input from a two-dimensional array
	 * of strings.
	 * 
	 * @param arr       The two-dimensional array of strings describing a 
	 *                  number of students.
	 */
	public static List<Student> constructStudentList(String[][] arr) {
		List<Student> studentList = new ArrayList<>();
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
	 * @param students  The list with Student objects to whom the schedules
	 *                  need to be assigned.       
	 */
	public static void constructSchedules(String[][] arr, List<Student> students) {
		for (String[] entry: arr) {
            for (Student student: students) {
            	if (student.hasThisID(entry[0])) {
            		student.addSchedule(new Schedule(entry));
            		break;
            	}
            }
		}
	}
	
	
	
	
	/* All code below (including the main-method) is test code and eventually no longer needed! */
	
	
	/* Student list made based on studentsMockArray for testing */ 
	static List<Student> testList = constructStudentList(studentsMockArray);
	
	
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
		
		
		// test the construction and assignment of the schedules above by 
		// printing out the student's ID, name and his or her schedule(s)
		for (Student stu: testList) {
			System.out.print(stu.getId() + " " + stu.getLName() + " " + 
					stu.getFName() + " " + stu.getNamePrefix() +
					schedules_String(stu) + "\n");
		}
		
	}
	
	
	/**
	 * Test method. Create and return a string describing all schedules
	 * a student has. 
	 */
	public static String schedules_String(Student stu) {

		String desc = "";

		for (Schedule schedule: stu.getSchedules()) {
			
			desc = desc + " " + schedule.getStartDate() + " " + schedule.getEndDate();

			HashMap<DayPart, Place> map = schedule.getMap();
			for(Map.Entry<DayPart, Place> entry : map.entrySet()){
				desc = desc + " " + entry.getKey().toString() + " " +
						entry.getValue().toString() + ",";
			}
			desc = desc + "   ";
			
		}
		return desc;
	}


}
