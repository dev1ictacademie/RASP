package nl.pameijer.ictacademie.rasp.view.inputstudent.fxml;

import javafx.beans.property.SimpleStringProperty;

/**
 * Convert TableDates to properties
 */
public class TableDatesProperties {


	private SimpleStringProperty mon = new SimpleStringProperty();
	private SimpleStringProperty tue = new SimpleStringProperty();
	private SimpleStringProperty wed = new SimpleStringProperty();
	private SimpleStringProperty thu = new SimpleStringProperty();
	private SimpleStringProperty fri = new SimpleStringProperty();

	public SimpleStringProperty monProperty() {
		return this.mon;
	}

	public String getMon() {
		return this.monProperty().get();
	}

	public void setMon(final String mon) {
		this.monProperty().set(mon);
	}

	public SimpleStringProperty tueProperty() {
		return this.tue;
	}

	public String getTue() {
		return this.tueProperty().get();
	}

	public void setTue(final String tue) {
		this.tueProperty().set(tue);
	}

	public SimpleStringProperty wedProperty() {
		return this.wed;
	}

	public String getWed() {
		return this.wedProperty().get();
	}

	public void setWed(final String wed) {
		this.wedProperty().set(wed);
	}

	public SimpleStringProperty thuProperty() {
		return this.thu;
	}

	public String getThu() {
		return this.thuProperty().get();
	}

	public void setThu(final String thu) {
		this.thuProperty().set(thu);
	}

	public SimpleStringProperty friProperty() {
		return this.fri;
	}

	public String getFri() {
		return this.friProperty().get();
	}

	public void setFri(final String fri) {
		this.friProperty().set(fri);
	}



}
