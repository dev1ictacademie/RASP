package conceptviews.datemodel.calendarheader;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

	private final StringProperty fName = new SimpleStringProperty();
	private final StringProperty lName = new SimpleStringProperty();

	public Student(String fName , String lName){
		setFName(fName);
		setLName(lName);

	}

	public final StringProperty fNameProperty() {
		return this.fName;
	}


	public final String getFName() {
		return this.fNameProperty().get();
	}


	public final void setFName(final String fName) {
		this.fNameProperty().set(fName);
	}


	public final StringProperty lNameProperty() {
		return this.lName;
	}


	public final String getLName() {
		return this.lNameProperty().get();
	}


	public final void setLName(final String lName) {
		this.lNameProperty().set(lName);
	}


}
