package nl.pameijer.ictacademie.rasp.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

	private final StringProperty fName = new SimpleStringProperty();
	private final StringProperty lName = new SimpleStringProperty();
	private final StringProperty namePrefix = new SimpleStringProperty();
	private final StringProperty id = new SimpleStringProperty();
	private final StringProperty infix = new SimpleStringProperty();
	private final StringProperty monMorning = new SimpleStringProperty();
	private final StringProperty monAfternoon = new SimpleStringProperty();
	private final StringProperty teusMorning = new SimpleStringProperty();
	private final StringProperty teusAfternoon = new SimpleStringProperty();
	private final StringProperty wedMorning = new SimpleStringProperty();
	private final StringProperty wedAfternoon = new SimpleStringProperty();
	private final StringProperty thursMorning = new SimpleStringProperty();
	private final StringProperty thursAfternoon = new SimpleStringProperty();
	private final StringProperty vriMorning = new SimpleStringProperty();
	private final StringProperty vriAfternoon = new SimpleStringProperty();

	// The students's schedule(s)
	private ArrayList<Schedule> schedules = new ArrayList<>();

	/**
	 * Student constructor
	 */
	public Student(String fName , String lName){
		setFName(fName);
		setLName(lName);
	}

	/**
	 * Student constructor
	 */
	public Student(String fName , String lName, String namePrefix, String id){
		setFName(fName);
		setLName(lName);
		setNamePrefix(namePrefix);
		setId(id);
	}

	/**
	 * Student constructor with Schedule.
	 */
	public Student(String fName , String lName, LocalDate startDate,
			LocalDate endDate, HashMap<DayPart, Place> schedule) {
		setFName(fName);
		setLName(lName);
		schedules.add(new Schedule(id.toString(), startDate, endDate, schedule));

	}

	/**
	 * Student constructor with ID and Schedule.
	 */
	public Student(String id, String fName , String lName, LocalDate startDate,
			LocalDate endDate, HashMap<DayPart, Place> schedule) {
		setFName(fName);
		setLName(lName);
		schedules.add(new Schedule(id, startDate, endDate, schedule));
		setId(id);
	}

	/**
	 * Student constructor with ID, First name Last name, Infix and 10 day parts.
	 */
	public Student(String id, String fName , String lName, String infix,
			String monMorning, String monAfternoon, String teusMorning, String teusAfternoon,
			String wedMorning, String wedAfternoon, String thursMorning, String thursAfternoon,
			String vriMorning, String vriAfternoon)
	{
		setFName(fName);
		setLName(lName);
		setId(id);
		setInfix(infix);
		setMonMorning(monMorning);
		setMonAfternoon(monAfternoon);
		setTeusMorning(teusMorning);
		setTeusAfternoon(teusAfternoon);
		setWedMorning(wedMorning);
		setWedAfternoon(wedAfternoon);
		setThursMorning(thursMorning);
		setThursAfternoon(thursAfternoon);
		setVriMorning(vriMorning);
		setVriAfternoon(vriAfternoon);
	}

	/**
	 * Make a new schedule for a student and add it to the list.
	 */
	public void makeNewSchedule(LocalDate startDate, LocalDate endDate,
			HashMap<DayPart, Place> schedule) {
		schedules.add(new Schedule(id.toString(), startDate, endDate, schedule));
	}

	/**
	 * Is this student expected at a certain date and day part?
	 * @param   date    the applicable date
	 * @param   dayPart the applicable dayPart
	 * @return  true if student is expected to be attending at this date and
	 *          dayPart, false if not
	 */
	public boolean isExpected(LocalDate date, DayPart dayPart) {

		boolean isExpected = false;

		for (Schedule schedule: schedules) {
			if (schedule.isDateWithinSchedule(date) &&
					schedule.isDayPartInSchedule(dayPart)) {
				isExpected = true;
			}
		}
		return isExpected;
	}

	public ArrayList<Schedule> getSchedules() {
		return schedules;
	}

	
	// First Name
	public final StringProperty fNameProperty() {
		return this.fName;
	}

	public final String getFName() {
		return this.fNameProperty().get();
	}

	public final void setFName(final String fName) {
		this.fNameProperty().set(fName);
	}


	// Last Name
	public final StringProperty lNameProperty() {
		return this.lName;
	}

	public final String getLName() {
		return this.lNameProperty().get();
	}

	public final void setLName(final String lName) {
		this.lNameProperty().set(lName);
	}
	
	
	// Name Prefix
	public final StringProperty namePrefixProperty() {
		return this.namePrefix;
	}
	
	public final String getNamePrefix() {
		return this.namePrefixProperty().get();
	}

	public final void setNamePrefix(final String namePrefix) {
		this.namePrefixProperty().set(namePrefix);
	}
	
	
	// ID
	public final StringProperty idProperty() {
		return this.id;
	}

	public final String getId() {
		return this.idProperty().get();
	}

	public final void setId(final String id) {
		this.idProperty().set(id);
	}

	public final String getInfix() {
		return this.infixProperty().get();
	}


	public final void setInfix(final String infix) {
		this.infixProperty().set(infix);
	}

	public final StringProperty infixProperty()
	{
		return this.infix;
	}

	public final StringProperty monMorningProperty()
	{
		return this.monMorning;
	}

	public final String getMonMorning() {
		return this.monMorningProperty().get();
	}

	public final void setMonMorning(final String monMorning) {
		this.monMorningProperty().set(monMorning);

	}

	public final StringProperty monAfternoonProperty()
	{
		return this.monAfternoon;
	}

	public final String getMonAfternoon() {
		return this.monAfternoonProperty().get();
	}

	public final void setMonAfternoon(final String monAfternoon) {
		this.monAfternoonProperty().set(monAfternoon);

	}

	public final StringProperty teusMorningProperty()
	{
		return this.teusMorning;
	}

	public final String getTeusMorning() {
		return this.teusMorningProperty().get();
	}

	public final void setTeusMorning(final String theusMorning) {
		this.teusMorningProperty().set(theusMorning);

	}

	public final StringProperty teusAfternoonProperty()
	{
		return this.teusAfternoon;
	}

	public final String getTeusAfternoon() {
		return this.teusAfternoonProperty().get();
	}

	public final void setTeusAfternoon(final String theusAfternoon) {
		this.teusAfternoonProperty().set(theusAfternoon);

	}


	public final StringProperty wedMorningProperty()
	{
		return this.wedMorning;
	}

	public final String getwedMorning() {
		return this.wedMorningProperty().get();
	}

	public final void setWedMorning(final String wedMorning) {
		this.wedMorningProperty().set(wedMorning);

	}

	public final StringProperty wedAfternoonProperty()
	{
		return this.wedAfternoon;
	}

	public final String getwedAfternoon() {
		return this.wedAfternoonProperty().get();
	}

	public final void setWedAfternoon(final String wedAfternoon) {
		this.wedAfternoonProperty().set(wedAfternoon);

	}

	public final StringProperty thursMorningProperty()
	{
		return this.thursMorning;
	}

	public final String getthursMorning() {
		return this.thursMorningProperty().get();
	}

	public final void setThursMorning(final String thursMorning) {
		this.thursMorningProperty().set(thursMorning);

	}

	public final StringProperty thursAfternoonProperty()
	{
		return this.thursAfternoon;
	}

	public final String getThursAfternoon() {
		return this.thursAfternoonProperty().get();
	}

	public final void setThursAfternoon(final String thursAfternoon) {
		this.thursAfternoonProperty().set(thursAfternoon);

	}

	public final StringProperty vriMorningProperty()
	{
		return this.vriMorning;
	}

	public final String getVriMorning() {
		return this.vriMorningProperty().get();
	}

	public final void setVriMorning(final String vriMorning) {
		this.vriMorningProperty().set(vriMorning);

	}

	public final StringProperty vriAfternoonProperty()
	{
		return this.vriAfternoon;
	}

	public final String getVriAfternoon() {
		return this.vriAfternoonProperty().get();
	}

	public final void setVriAfternoon(final String vriAfternoon) {
		this.vriAfternoonProperty().set(vriAfternoon);

	}
	
}
