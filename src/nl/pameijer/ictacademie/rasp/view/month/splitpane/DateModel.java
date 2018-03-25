package nl.pameijer.ictacademie.rasp.view.month.splitpane;

import java.time.LocalDate;



import javafx.beans.property.SimpleIntegerProperty;

/**
 * DateModel sets a date and set the properties. Holds length of month and weekday names
 *
 * @author devadv
 *
 */
public class DateModel {

	private SimpleIntegerProperty year = new SimpleIntegerProperty();
	private SimpleIntegerProperty month = new SimpleIntegerProperty();
	private SimpleIntegerProperty day = new SimpleIntegerProperty();

	public DateModel(int year, int month) {
		this.year.set(year);
		this.month.set(month);
		this.day.set(1);// default first day of month

	}
	//year
	public final SimpleIntegerProperty yearProperty() {
		return this.year;
	}

	public final int getYear() {
		return this.yearProperty().get();
	}

	public final void setYear(final int year) {
		this.yearProperty().set(year);
	}
	//month
	public final SimpleIntegerProperty monthProperty() {
		return this.month;
	}

	public final int getMonth() {
		return this.monthProperty().get();
	}

	public final void setMonth(final int month) {
		this.monthProperty().set(month);
	}

	//day
	public final SimpleIntegerProperty dayProperty() {
		return this.day;
	}

	public final int getDay() {
		return this.dayProperty().get();
	}

	public final void setDay(final int day) {
		this.dayProperty().set(day);
	}

	//length of month
	public final int getLengthOfMonth() {
		LocalDate date = LocalDate.of(year.get(), month.get(), day.get());
		return date.lengthOfMonth();
	}
	//day of the week name translated
	public String dayOfWeek(int day) {
		LocalDate localDate = LocalDate.of(year.get(), month.get(), day);
		String dayName = "";
		switch (localDate.getDayOfWeek()) {
		case MONDAY:
			dayName = "ma";
			break;
		case TUESDAY:
			dayName = "di";
			break;
		case WEDNESDAY:
			dayName = "wo";
			break;
		case THURSDAY:
			dayName = "do";
			break;
		case FRIDAY:
			dayName = "vr";
			break;
		case SATURDAY:
			dayName = "za";
			break;
		case SUNDAY:
			dayName = "zo";
			break;

		default:
			break;
		}
		return dayName;
	}
	public String[] dayNameList(){
		String[] dayNames = new String[getLengthOfMonth()];
		for(int i =0 ; i<getLengthOfMonth();i++){
			dayNames[i] = dayOfWeek(i+1);
		}


		return dayNames;
	}

}
