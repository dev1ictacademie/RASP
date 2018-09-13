package nl.pameijer.ictacademie.rasp.view.inputstudent.fxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableDatesProperties {
	/**
	 * Convert TableDates to properties
	 */

	private SimpleStringProperty day1 = new SimpleStringProperty();
	private SimpleStringProperty day2 = new SimpleStringProperty();
	private SimpleStringProperty day3 = new SimpleStringProperty();
	private SimpleStringProperty day4 = new SimpleStringProperty();
	private SimpleStringProperty day5 = new SimpleStringProperty();

	public final SimpleStringProperty day1Property() {
		return this.day1;
	}

	public final String getDay1() {
		return this.day1Property().get();
	}

	public final void setDay1(final String day1) {
		this.day1Property().set(day1);
	}

	public final SimpleStringProperty day2Property() {
		return this.day2;
	}

	public final String getDay2() {
		return this.day2Property().get();
	}

	public final void setDay2(final String day2) {
		this.day2Property().set(day2);
	}

	public final SimpleStringProperty day3Property() {
		return this.day3;
	}

	public final String getDay3() {
		return this.day3Property().get();
	}

	public final void setDay3(final String day3) {
		this.day3Property().set(day3);
	}

	public final SimpleStringProperty day4Property() {
		return this.day4;
	}

	public final String getDay4() {
		return this.day4Property().get();
	}

	public final void setDay4(final String day4) {
		this.day4Property().set(day4);
	}

	public final SimpleStringProperty day5Property() {
		return this.day5;
	}

	public final String getDay5() {
		return this.day5Property().get();
	}

	public final void setDay5(final String day5) {
		this.day5Property().set(day5);
	}

}
