package nl.pameijer.ictacademie.rasp.view.tools;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Place;
import nl.pameijer.ictacademie.rasp.model.Schedule;
import nl.pameijer.ictacademie.rasp.model.Student;

public class ExportToPDF {
	
	private Model model = new Model();
	private LocalDate now = LocalDate.now();
	private LocalDate mostRecentMonday = 
			now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	
	/**
	 * Constructor.
	 */
	public ExportToPDF() {
		model.loadDataWithScheduleAndID();
	}

	
	/**
	 * Return an array of strings that represents an overview of all places at 
	 * a certain dayPart from a department.
	 */
	public String[] getPlacesOverview(DayPart dayPart, String department) {
		
		List<String> listA = new ArrayList<>();
		List<String> listB = new ArrayList<>();
		
		for (Place place: Place.values()) {
			if (place.isOfType(department)) {
				listB.add(place.toString());
			}
		}
		
		LocalDate dateOfDayPart = mostRecentMonday;
		DayPart part = DayPart.getDayPartByNumber(0);

		if (dayPart != DayPart.MONDAY_MORNING) {
			int i = 1;
			while (dayPart != part) {
				part = DayPart.getDayPartByNumber(i);
				if (i % 2 == 0) {
					dateOfDayPart = dateOfDayPart.plusDays(1);
				}
				i++;
			}
		}

		for (Student student: model.getStudentList()) {
			if (student.getActiveSchedule(dateOfDayPart) != null) {
				Schedule activeSchedule = student.getActiveSchedule(dateOfDayPart);
				String desc = "";
				if (activeSchedule.isDayPartInSchedule(dayPart) &&
						activeSchedule.getMap().get(dayPart).isOfType(department)) {
					desc += activeSchedule.getMap().get(dayPart).toString();
					desc += " ";
					desc += student.getFName();
					if (student.getNamePrefix() != null && 
							student.getNamePrefix().length() != 0) {
						desc += " ";
						//desc += student.getNamePrefix();
					}
					//desc += " ";
					//desc += student.getLName();
					listA.add(desc);
				}
			}
		}
		
		for (String s: listA) {
			String place = s.substring(0, s.indexOf(" "));
			for (int i = 0; i < listB.size(); i++) {
				if (listB.get(i).equals(place)) {
					listB.add(i, s);
					listB.remove(i + 1);
				}
			}
		}
		
		for (int i = 0; i < listB.size(); i++) {
			String newValue = listB.get(i).substring(listB.get(i).indexOf("_") + 1);
			listB.add(i, newValue);
			listB.remove(i + 1);
		}
		
		return listB.toArray(new String[listB.size()]);
		
	}

	/**
	 * Main method for testing purposes.
	 */
	public static void main(String[] args) {
		
		ExportToPDF exporter = new ExportToPDF();
		String[] arr = exporter.getPlacesOverview(DayPart.MONDAY_MORNING, "ICT");
		for (String s: arr) {
			System.out.println(s);
		}
		
	}

}
