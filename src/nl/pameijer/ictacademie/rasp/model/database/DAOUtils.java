package nl.pameijer.ictacademie.rasp.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Current database driver for the RASP database. 
 * <br />Should be refactored (comment January 2019).
 * </p><p>
 * Note: Most, if not all, SQL statements are static (are hard coded).
 * When refactoring, use SQL prepare statements to prevent SQL injection.   
 * </p>
 * @author Wim Ahlers
 * @since January, 2019
 */
public class DAOUtils implements DAO {

	private Connection connection;
	private Statement statement;
	
	// some hard coded database keys and values
	public static final long MORNING = 1; // table: timeunit)
	public static final long AFTERNOON = 2; // table: timeunit)
	public static final String DATE_UNDETERMINED = "9999-12-31"; 
	
	/**
	 * <p>Connect to the RASP database</p>
	 * @throws Exception Database connect exception. 
	 */
	public void setConnectionDatabase() throws Exception {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement();

		} catch (Exception e) {
			throw new Exception("Failed to connect to: " + DB_URL, e); 			
		}
	}

	/**
	 * <p>Disconnect from the RASP database</p>
	 * @throws Exception Database disconnect exception. 
	 */
	public void closeConnection() throws Exception {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new Exception("Failed to disconnect from: " + DB_URL, e); 			
		}
	}
	
	/**
	 * Retrieve the default language 
	 * 
	 * @return default language
	 */
	public String getDefaultLanguage() {
		// TODO store default settings either in database, or property files, 
		//		or config files, storing "name => value" pairs.
		//		For example the pair: "language" => "nl"
		// 		language hardcoded here ... for the moment! ...
		return "nl"; // ISO 639-1 (Dutch)
	}
	
	/**
	 * <p>
	 * Get registrants list.
	 * </p>
	 * <p>
	 * The array returned contains per array row the following fields:
	 * </p>
	 * <ul><li>
	 * registrantID			: registrant key
	 * </li><li>
	 * lastName				: Surname name of registrant
	 * </li><li>
	 * firstName			: First name of registrant			
	 * </li><li>
	 * middleName			: Registrant's middle name(s) or middle initial(s)
	 * </li><li>
	 * roleID				: Key to role. E.g. Student, volunteer, etc.
	 * </li><li>
	 * phoneNumberPrimary	: Registrant's main contact phone number (or null)
	 * </li><li>
	 * phoneNumberSecondary	: Registrant's alternative phone number (or null) 
	 * </li><li>
	 * phoneNumberRelation	: A contact persons phone number (or null)
	 * </li><li>
	 * emailAddress			: Registrant's e-mail address (or null)
	 * </li><li>
	 * zipcode				: Registrant's residence postal code
	 * </li><li>
	 * dailyCommuterCost	: Registrant's daily commuter cost (in euro cents)
	 * </li><li>
	 * commuterTypeID		: Key to commuter type. E.g. "Public Transport" 
	 * </li></ul>
	 * <p>
	 * 			
	 * @return formatted registrant personal attributes list.
	 * @throws Exception Database retrieve exception. 
	 */
	public ArrayList<String[]> getRegistrantsList() 
			throws Exception {
		ArrayList<String[]> registrantList = new ArrayList<String[]>();
		final int DATA_SIZE  = 12; // currently 12 data elements.
		String[] data = new String[DATA_SIZE];
		
		try {
			// retrieve the "raw" registrant attributes list. 
			ResultSet rs = getRegistrants();
			
			while(rs.next()) {
				// build a registrant attribute record
				data[0] = "" + rs.getLong("registrantID");
				data[1] = rs.getString("lastName");
				data[2] = rs.getString("firstName");
				data[3] = rs.getString("middleName");
				data[4] = "" + rs.getLong("roleID");
				data[5] = rs.getString("phoneNumberPrimary");
				data[6] = rs.getString("phoneNumberSecondary");
				data[7] = rs.getString("phoneNumberRelation");
				data[8] = rs.getString("emailAddress");
				data[9] = rs.getString("zipcode");
				data[10] = rs.getString("dailyCommuterCost");
				data[11] = "" + rs.getLong("commuterTypeID");
				registrantList.add(data);
				data = new String[DATA_SIZE];
			} 
			
			rs.close();

		} catch(Exception e) {
			// TODO change general exception catch into a more specific
			// exception catch. 
			// And possibly (re)thrown an (new) exception.
			// On exception, return empty data set.
			registrantList = new ArrayList<String[]>();
			throw e; // let the method invoker decide what to do...
		}
		
		return registrantList;
	}

	/**
	 * <p>
	 * Get all data of all registrants
	 * </p>
	 * @return ResultSet get registrants
	 * @throws Exception
	 */
	public ResultSet getRegistrants() throws Exception {
		ResultSet resultSet = null; // initially no data.
		
		try {
			// Select all attributes unconditionally.
			String select = ("SELECT " 
					+ "a.registrantID, "
					+ "a.lastName, "
					+ "a.firstName, "
					+ "a.middleName, "
					+ "a.role AS roleID , "
					+ "a.phoneNumberPrimary, "
					+ "a.phoneNumberSecondary, "
					+ "a.phoneNumberRelation, "
					+ "a.emailAddress, "
					+ "b.zipcode, "
					+ "b.dailyCommuterCost, "
					+ "b.commuterType AS commuterTypeID "
					+ "FROM `registrant` a join `commuter`b " 
					+ "on a.commuterID = b.commuterID "		
					+ " ORDER BY lastName"); 

			// Retrieve the raw "registrants" data
			resultSet = statement.executeQuery(select);
		} catch (Exception e) {
System.out.println("xxx 187 empty resultset + " + e.getMessage());
			resultSet = null; // on exception return an empty result set.
			throw e; // let the method invoker decide what to do...
		}
		
		return resultSet;
	}
	
	/**
	 * <p>
	 * Get timesheet link list.
	 * </p>
	 * <p>
	 * The list returned contains per row objects of the following types:
	 * </p>
	 * <ul><li>
	 * Long: timesheetID - The timesheet key
	 * </li><li>
	 * LocalDate: actuationDate - The date the timesheet was actuated 
	 * </li><li>
	 * LocalDate: closingDate - The date the timesheet ends/ended 
	 * (date 9999-12-31 means undetermined)			
	 * </li><li>
	 * Long: futureTimesheetID - key to the next linked timesheet.<br>
	 * 	"null" means there is no next timesheet (this is last in linked chain)
	 * </li><li>
	 * Long: pastTimesheetID - key to the previous linked timesheet
	 * 	"null" means there is no previous timesheet (first in linked chain)
	 * </li></ul>
	 * <p>
	 * <p>Note: To access the data you can use "instanceof".</p>
	 * 			
	 * @return registrant timesheets list.
	 * @throws Exception Database retrieve exception. 
	 */
	public List<List<Object>> getTimesheetLinkList(Long timesheetID) 
			throws Exception {
		// initialization
		ResultSet rs = null; // initially no data.
		List<List<Object>>  timesheetList = new ArrayList<List<Object>>();

		try {
			// retrieve linked timesheet list
			String sql = "SELECT `timesheetID`, "
					+ "`actuationDate`, "
					+ "`closingDate`, "
					+ "`futureTimesheetID`, "
					+ "`pastTimesheetID` "
					+ "FROM "
					+ "`timesheet` "
					+ "WHERE "
					+ "`timesheetID` IN "
					+ "(SELECT `timesheetID` "
					+ "FROM  `registranttimesheet` "
					+ "WHERE  `registrantID` = "
					+ "(SELECT `registrantID` FROM "
					+ "`registranttimesheet` "
					+ "WHERE `timesheetID` = " + timesheetID
					+ ")) "
					+ "ORDER BY `timesheetID`"; 
			rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				// build a timesheet attribute record
				List<Object> row = new ArrayList<Object>();
				row.add(new Long(rs.getLong("timesheetID")));
				row.add(LocalDate.parse(rs.getString("actuationDate")));
				row.add(LocalDate.parse(rs.getString("closingDate")));
				row.add(new Long(rs.getLong("futureTimesheetID")));
				row.add(new Long(rs.getLong("pastTimesheetID")));
				timesheetList.add(row);
			} 
		} catch(Exception e) {
			timesheetList = new ArrayList<List<Object>>();
			throw e; // let the method invoker decide what to do...
		}
		
		return timesheetList;
	}

	/**
	 * <p>
	 * Get all data of all registrants
	 * </p>
	 * @return ResultSet get registrants
	 * @throws Exception
	 */
	public ResultSet getRegistrantTimesheets(Long registrantID) throws Exception {
		ResultSet resultSet = null; // initially no data.
		
		try {
			// Select all attributes unconditionally.
			String sql = ("SELECT " 
					+ "timesheet.timesheetID, "
					+ "actuationDate, "
					+ "closingDate, "
					+ "futureTimesheetID , "
					+ "pastTimesheetID, "
					+ "FROM `timesheet`, `registranttimesheet` " 
					+ "WHERE registranttimesheet.timesheetID=timesheet.timesheetID "
					+ "AND registrantID=" + registrantID 
					+ " ORDER BY actuationDate"); 

			// Retrieve the raw "registrants" data
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
			resultSet = null; // on exception return an empty result set.
			throw e; // let the method invoker decide what to do...
		}
		
		return resultSet;
	}
	
	/**
	 * <p>
	 * Format the standard week schedule per registrant.
	 * </p>
	 * <p>
	 * The standard week schedule contains per array element the following 
	 * fields:
	 * </p>
	 * <ol><li>
	 * key for the registrant table data.
	 * </li><li>
	 * timesheet actuation date (format, by example: 2018-12-31)
	 * </li><li>
	 * timesheet closing date (date 9999-12-31 means "undetermined")
	 * </li><li>
	 * Monday morning spot
	 * </li><li>
	 * Monday afternoon spot
	 * </li><li>
	 * Tuesday morning spot
	 * </li><li>
	 * Tuesday afternoon spot
	 * </li><li>
	 * Wednesday morning spot
	 * </li><li>
	 * Wednesday afternoon spot
	 * </li><li>
	 * Thursday morning spot
	 * </li><li>
	 * Thursday afternoon spot
	 * </li><li>
	 * Friday morning spot
	 * </li><li>
	 * Friday afternoon spot
	 * </li><li>
	 * Saturday morning spot
	 * </li><li>
	 * Saturday afternoon spot
	 * </li><li>
	 * Sunday morning spot
	 * </li><li>
	 * Sunday afternoon spot
	 * </li></ol>
	 * <p>
	 * Note: A repeating group per registrant is created to represent the 
	 * work week work days (as illustrated above with the aforementioned 
	 * Monday through Sunday day parts spot references). Which represents
	 * the registrant's working week schedule.<br />
	 * Saturdays and Sundays are commonly not part of a working week.
	 * Saturdays and Sundays are included here for (future) generic usage.
	 * </p>
	 * 
	 * @param language output language request parameter (ISO 639-1)
	 * 			
	 * @return formatted week schedule per registrant (in requested langauge). 
	 * @throws Exception Database retrieve exception. 
	 */
	public ArrayList<String[]> getStandardWeekSchedule(String language) 
			throws Exception {
		ArrayList<String[]> standardWeekSchedule = new ArrayList<String[]>();
		final int DATA_SIZE  = 18; // currently 18 data elements.
		
		try {
			// retrieve the "raw" registrant schedule data in the
			// desired language.
System.out.println("xxx 365 before getRegistrantSchedule(" + language + ")");
			ResultSet rs = getRegistrantSchedule(language);
System.out.println("xxx 367 after getRegistrantSchedule(" + language + ")");
			
			// Initialize first week schedule record.
			rs.next();
			String[] data = new String[DATA_SIZE];
			data[0] = rs.getString("registrantID");
			data[1] = "" + rs.getLong("timesheetID");
			data[2] = rs.getString("actuationDate");
			data[3] = rs.getString("closingDate");
System.out.println("xxx 376 first data: " + data[0] + data[1] + data[2] + data[3] );
			
			do {
				// build a week schedule record for every time sheet
				if ((""+rs.getLong("timesheetID")).equals(data[1])) {
					fillRecord(rs, data);
				} else {
					// Store currently build week schedule record.
					standardWeekSchedule.add(data);
					
					// initialize next week schedule record...
					data = new String[DATA_SIZE]; 
					data[0] = rs.getString("registrantID");
					data[1] = "" + rs.getLong("timesheetID");
					data[2] = rs.getString("actuationDate");
					data[3] = rs.getString("closingDate");
					fillRecord(rs, data);
				}
			} while(rs.next());
			
			// Store last build week schedule record.
			standardWeekSchedule.add(data);
			rs.close();

		} catch(Exception e) {
			standardWeekSchedule = new ArrayList<String[]>();
			throw e; // let the method invoker decide what to do...
		}
		
		return standardWeekSchedule;
	}
	
	/*
	 * @see getFormattedWeekSchedule(String language)
	 * 
	 * Builds the schedule record for the method:
	 * getFormattedWeekSchedule(String language)
	 */
	private void fillRecord(ResultSet rs, String[] record) throws Exception {
		try {
			// Schedule data is registered with other data.
			// The first 4 columns contain user identification data.
			// The subsequent columns contain the Schedule data.
			int offSetFromStart = 4;
			// A day has 2 parts (morning and afternoon). Weekday=1 is Monday. 
			// Monday morning starts at index=3. Monday afternoon starts at 
			// index=4. Tuesday=2, Wednesday=3 ... Sunday=7 (ISO 8601).
			// Thus each day has 2 columns. The following algorithm
			// calculates the correct index for the  schedule column.
			int startWeekColumn = (rs.getInt("weekday") - 1) * 2;
			int dayPartIndex = offSetFromStart + startWeekColumn;
			
			if (rs.getLong("timeUnitID") == AFTERNOON) {
				dayPartIndex++; // afternoon is after morning part.
			} else if (rs.getLong("timeUnitID") != MORNING) {
				// Business rule (at date written): A day consists of the above
				// 2 day parts only. This may change in the future. When so, 
				// either modify the select statement or modify the code.
				throw new Exception("Unexpected timeUnitID: " 
						+ rs.getLong("timeUnitID"));
			}
		
			// register the registrant's location on that day part.
			record[dayPartIndex] = 
				rs.getString("room") + "_" + rs.getString("spot");

		} catch(Exception e) {
			throw e; // let the method invoker decide what to do...
		}
	}
	
	/**
	 * <p>
	 * Format the week schedule per registrant.
	 * </p><p>
	 * For details @see getFormattedWeekSchedule(language) <br />
	 * The default language will be used for the database data returned.
	 * </p>
	 * @return formatted week schedule per registrant.
	 * @throws Exception Database retrieve exception. 
	 */
	public ArrayList<String[]> getStandardWeekSchedule() 
			throws Exception {
		
		return getStandardWeekSchedule(getDefaultLanguage());
	}
	
	/**
	 * Retrieve location data of all spots in the default language.
	 * 
	 * For details @see getAllSpots(String language)
	 * 
	 * @return resultSet spot information. On exception "null" is returned. 
	 * @throws Exception Database retrieve exception.
	 */
	public ResultSet getRegistrantSchedule() throws Exception {
		return getRegistrantSchedule(getDefaultLanguage());
	}
	
	/**
	 * <p>
	 * Retrieve schedule data. Result set returned contains the following 
	 * fields:<br />
	 * (note: data retrieved is denormalized database data)
	 * </p><ul><li>
	 * timesheetID		bigint	timesheet key
	 * </li><li>
	 * actuationDate	date	The data this timesheet was actuated.
	 * </li><li>
	 * closingDate		date	The data this timesheet ended.
	 * </li><li>
	 * registrantID		bigint	registrant key
	 * </li><li>
	 * lastName			String	registrant's surname 
	 * </li><li>
	 * firstName		String	registrant's first name 
	 * </li><li>
	 * middleName		String	registrant's middle name(s)
	 * </li><li>
	 * weekday			int		ISO 8601 1=monday, 2=tuesday, 3=wednesday, etc.
	 * </li><li>
	 * timeUnitID		bigint	timeUnit ID. E.g. 1="Morning", 2="afternoon"
	 * </li><li>
	 * timeUnit			String	name of time unit. E.g. "Morning"
	 * </li><li>
	 * role				String	registrant's role. E.g. "student", "volunteer"	
	 * </li><li>
	 * roomSpotID		bigint	roomSpot key. The key to the following fields:
	 * <ul><li>
	 * 	spot			String	A specific place, in the room:
	 * </li><li>
	 * 	room			String	room, at the department:
	 * </li><li>
	 * 	department		String	department, in the place:
	 * </li><li>
	 * 	place			String	place, with the street address:
	 * </li><li>
	 * 	street			String	street + street number, with the zip code: 
	 * </li><li>
	 * 	zipcode			String	zip code (or postal code), with the brand name:
	 * </li><li>
	 * 	locationName	String	location name (or brand name. E.g. ACME house).
	 * </li></ul></li></ul>
	 * <p>
	 * The format of the "date" field is (by example): 9999-12-31.
	 * All key fields are "bigint". bigint corresponds with the "long" format. 
	 * </p><p>
	 * result set is sorted by registrantID in ascending order. 
	 * And per registrantID ordered by timeSheetID in ascending order.
	 * </p>
	 * 
	 * @param language language used for the result set (internationalization)
	 * @return resultSet schedule information. 
	 * @throws Exception Database retrieve exception.
	 */
	public ResultSet getRegistrantSchedule(String language) throws Exception {
		ResultSet resultSet = null; // initially no data.
		
		try {
			// combining normalized tables to get a registrant schedule list. 
			// Note: The normalization performance penalty is accepted because 
			// database consistency has the greater priority here.
			String sql = ("SELECT " 
					+ "a.timesheetID," 
					+ "a.actuationDate," 
					+ "a.closingDate," 
					+ "c.registrantID,"
					+ "c.lastName," 
					+ "c.firstName," 
					+ "c.middleName," 
					+ "d.weekday,"
					+ "f.timeUnitID,"
					+ "g.text AS timeUnit,"
					+ "h.text AS role," 
					+ "i.roomSpotID," 
					+ "k.text AS spot," 
					+ "m.text AS room,"
					+ "p.text AS department," 
					+ "q.place," 
					+ "q.street,"
					+ "q.zipcode," 
					+ "r.text AS locationName " 
					+ "FROM "
					+ "`timesheet` a," 
					+ "`registranttimesheet` b " 
					+ "INNER JOIN " 
					+ "`registrant` c " 
					+ "ON "
					+ "b.registrantID = c.registrantID," 
					+ "`weekschedule` d," 
					+ "`role` e," 
					+ "`timeunit` f "
					+ "INNER JOIN " 
					+ "`text` g " 
					+ "ON " 
					+ "f.title = g.textID," 
					+ "`text` h," 
					+ "`roomspot` i "
					+ "INNER JOIN " 
					+ "`spot` j " 
					+ "ON " 
					+ " i.spotID = j.spotID " 
					+ "INNER JOIN " 
					+ "`text` k "
					+ "ON j.name = k.textID," 
					+ "`room` l	" 
					+ "INNER JOIN " 
					+ "`text` m " 
					+ "ON l.name = m.textID, "
					+ "`departmentroom` n " 
					+ "INNER JOIN " 
					+ "`department` o " 
					+ "ON "
					+ "n.departmentID = o.departmentID " 
					+ "INNER JOIN " 
					+ "`text` p " 
					+ "ON " 
					+ "o.name = p.textID,"
					+ "`location` q " 
					+ "INNER JOIN " 
					+ "`text` r " 
					+ "ON q.name = r.textID " 
					+ "WHERE "
					+ "b.timesheetID = a.timesheetID " 
					+ "AND b.timesheetID = d.timesheetID " 
					+ "AND c.role = e.roleID "
					+ "AND d.timeUnitID = f.timeUnitID " 
					+ "AND g.language = \"" + language + "\" " 
					+ "AND e.title = h.textID "
					+ "AND h.language =  \"" + language + "\" " 
					+ "AND k.language =  \"" + language + "\" " 
					+ "AND d.roomSpotID = i.roomSpotID "
					+ "AND i.roomID = l.roomID " 
					+ "AND m.language =  \"" + language + "\" " 
					+ "AND i.roomID = n.roomID "
					+ "AND p.language =  \"" + language + "\" " 
					+ "AND o.locationID = q.locationID " 
					+ "AND r.language =  \"" + language + "\" "
					+ "ORDER BY registrantID, timesheetID"); 
		
System.out.println("xxx 616 sql :" + sql);
			// Retrieve the raw "timesheet per registrant" data
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
System.out.println("xxx 620 empty resultset + " + e.getMessage());
			resultSet = null; // on exception return null.
			throw e; // let the method invoker decide what to do...
		}
		
		return resultSet;
	}
	
	/**
	 * <p>
	 * Get formatted spot list.
	 * </p><p>
	 * For details @see getFormattedAllSpots(language) <br />
	 * The default language will be used for the database data returned.
	 * </p>
	 * @return spots list (formatted).
	 * @throws Exception Database retrieve exception. 
	 */
	public ArrayList<String[]> getAllSpotsList() 
			throws Exception {
		return getFormattedAllSpots(getDefaultLanguage());
	}
	
	
	/**
	 * <p>
	 * Get a formatted list of all spots.
	 * </p>
	 * <p>
	 * The array returned contains per array row the following fields:
	 * </p>
	 * <ul></li>
	 * locationID			: Location key
	 * </li><li>
	 * street				: Location's street
	 * </li><li>
	 * place				: Location's place or residence			
	 * </li><li>
	 * zipcode				: Location's residence postal code
	 * </li><li>
	 * locationName			: Location's name. E.g. "Headquarters"
	 * </li><li>
	 * departmentID			: Department key
	 * </li><li>
	 * departmentName		: Department's name for location. E.g. "IT"
	 * </li><li>
	 * roomID				: Room key 
	 * </li><li>
	 * roomName				: Room's name for department
	 * </li><li>
	 * spotID				: Spot key (refers to specific place. E.g. a desk)
	 * </li><li>
	 * seats				: Number of seats at a spot (usually 1). 
	 * 						  0 means undetermined/undefined
	 * </li><li>
	 * spotName				: Spot name for the spot in that room
	 * </li></ul>
	 * <p>
	 * 			
	 * @return formatted all spots attributes list.
	 * @throws Exception Database retrieve exception. 
	 */
	public ArrayList<String[]> getFormattedAllSpots(String language) 
			throws Exception {
		ArrayList<String[]> allSpots = new ArrayList<String[]>();
		final int DATA_SIZE  = 13; // currently 13 data elements.
		String[] data = new String[DATA_SIZE];
		
		try {
			// retrieve the "raw" all spots data. 
			ResultSet rs = getAllSpots();
			
			while(rs.next()) {
				// build a spot record
				data[0] = "" + rs.getLong("locationID");
				data[1] = rs.getString("street");
				data[2] = rs.getString("place");
				data[3] = rs.getString("zipcode");
				data[4] = rs.getString("locationName");
				data[5] = "" + rs.getLong("departmentID");
				data[6] = "" + rs.getLong("roomSpotID");
				data[7] = rs.getString("departmentName");
				data[8] = "" + rs.getLong("roomID");
				data[9] = rs.getString("roomName");
				data[10] = "" + rs.getLong("spotID");
				data[11] = rs.getString("seats");
				data[12] = rs.getString("spotName");
				allSpots.add(data);
				data = new String[DATA_SIZE];
			} 
			
			rs.close();

		} catch(Exception e) {
			// TODO change general exception catch into a more specific
			// exception catch. 
			// And possibly (re)thrown an (new) exception.
			// On exception, return empty data set.
			allSpots = new ArrayList<String[]>();
			throw e; // let the method invoker decide what to do...
		}
		
		return allSpots;
	}

	/**
	 * Retrieve location data of all spots in the default language.
	 * 
	 * For details @see getAllSpots(String language)
	 * 
	 * @return resultSet spot information. On exception "null" is returned. 
	 * @throws Exception Database retrieve exception.
	 */
	public ResultSet getAllSpots() throws Exception {
		return getAllSpots(getDefaultLanguage());
	}
	
	/**
	 * <p>
	 * Retrieve location data of all spots. A spot is a specific place where
	 * people reside or work. For instance: A specific desk in a specific room
	 * within a specific administration at a specific location. The result set 
	 * returned contains the following fields:<br />
	 * (note: data retrieved is denormalized database data)
	 * </p>
	 * <ul><li>
	 * locationID		bigint	key for a specific location
	 * </li><li>
	 * street			String	street where the spot is
	 * </li><li>
	 * place			String	town where the spot is
	 * </li><li>
	 * zipcode			String	zip code (a.k.a. postal code) of the street
	 * </li><li>
	 * locationName		String	the spot's firm or residence name 
	 * </li><li>
	 * departmentID		bigint	referential key for the department table 
	 * </li><li>
	 * departmentName	String	the spot's department name
	 * </li><li>
	 * roomID			bigint	referential key for the room table 
	 * </li><li>
	 * roomName			String	the name of the room where the spot is
	 * </li><li>
	 * 	spotID			bigint	referential key for the spot table
	 * </li><li>
	 * 	seats			int		number of seats at a the spot (0 = undefined)
	 * </li><li>
	 * 	spotName		String	the name of the spot. E.g. "desk 3.14"
	 * </li></ul>
	 * <p>
	 * All key fields are "bigint". bigint corresponds with the "long" format. 
	 * </p><p>
	 * Result set is sorted by place in ascending order. 
	 * And per place ordered by zip code in ascending order.
	 * And per zip code ordered by room name in ascending order.
	 * And per room name ordered by spot name in ascending order.
	 * As a rule the spot name refers to a specific (work) desk. 
	 * </p>
	 * 
	 * @param language language used for the result set (ISO 639-1)
	 * @return resultSet spot information. On exception "null" is returned. 
	 * @throws Exception Database retrieve exception.
	 */
	public ResultSet getAllSpots(String language) throws Exception {
		ResultSet resultSet = null; // initially no data.
		
		try {
			// combining normalized tables to get a spot information list. 
			// Note: The normalization performance penalty is accepted because 
			// database consistency has the greater priority here.
			String select = "SELECT " 
				+ "a.locationID, "
				+ "a.street, "
				+ "a.place, "
				+ "a.zipcode, "
				+ "g.text AS locationName, "
				+ "b.departmentID, "
				+ "e.roomSpotID, "
				+ "h.text AS departmentName, "
				+ "d.roomID, "
				+ "i.text AS roomName, "
				+ "f.spotID, "
				+ "f.seats, "
				+ "j.text AS spotName " 
				+ "FROM "
				+ "`location` a " 
				+ "INNER JOIN "
				+ "`department` b " 
				+ "ON a.locationID = b.locationID " 
				+ "INNER JOIN "
				+ "`departmentroom` c " 
				+ "ON b.departmentID = c.departmentID " 
				+ "INNER JOIN "
				+ "`room` d " 
				+ "ON c.roomID = d.roomID " 
				+ "INNER JOIN "
				+ "`roomspot` e " 
				+ "ON d.roomID = e.roomID " 
				+ "INNER JOIN "
				+ "`spot` f " 
				+ "ON e.spotID = f.spotID, "
				+ "`text` g, "
				+ "`text` h, "
				+ "`text` i, "
				+ "`text` j " 
				+ "WHERE "
				+ "a.name = g.textID " 
				+ "AND b.name = h.textID " 
				+ "AND d.name = i.textID " 
				+ "AND f.name = j.textID " 
				+ "AND g.language = '" + language + "'" 
				+ "AND h.language = '" + language + "'" 
				+ "AND i.language = '" + language + "'" 
				+ "AND j.language = '" + language + "'" 
				+ "ORDER BY "
				+ "a.place, "
				+ "a.zipcode, "
				+ "roomName, "
				+ "spotName ";
		
			// Retrieve the "all spots" data
			resultSet = statement.executeQuery(select);
		} catch (Exception e) {
			resultSet = null; // on exception return null.
			throw e; // let the method invoker decide what to do...
		}
		
		return resultSet;
	}
	
	/**
	 * <p>add and store a new weekly timesheet</p>
	 * <p>
	 * A timesheet (or time sheet) is a method for recording the amount of 
	 * a worker's time spent on each job. Traditionally a sheet of paper with 
	 * the data arranged in tabular format, here digital stored.
	 * </p><p>
	 * In this case the timesheet represents a week (7 days). Whereby Monday
	 * is day 1, Tuesday is day 2, etc. (ISO 8601).<br>
	 * As a rule Saturdays and Sundays are not considered working days. 
	 * However, keeping all week days is more generic and therefore more future 
	 * proof without any performance or functionality penalty.
	 * </p><p>
	 * The business rules (c.q. integrity rules) for adding a weekly timesheet 
	 * are as follows:
	 * </p>
	 * <ul><li>
	 * The actuation date (c.q. start date) of the timesheet to be added  
	 * ALWAYS starts after or on the current date.
	 * </li><li>
	 * The end date of the timesheet to be added ALWAYS must be after the 
	 * actuation date. 
	 * </li><li>
	 * 9999-12-31 as an end date means the end date is undetermined.
	 * </li><li>
	 * The format of the dates are conform ISO 8601 (yyyy-mm-dd).
	 * </li><li>
	 * When the timesheet is not the first timesheet for this specific
	 * registrant, and all previous timesheets have consecutive start and end
	 * dates then the actuation date of the to be added timesheet must be
	 * after the actuation date of the most recent (=the last in the chain of) 
	 * timesheet.
	 * </li><li>
	 * When the timesheets are not consecutive then a new weekly timesheet
	 * can be added inbetween the time period gap of two timesheets.
	 * With the limitation that the actuation data of the weekly timesheet
	 * to be added is AFTER the end data of the preceding timesheet and
	 * the end data of the timesheet to be added is BEFORE the actuation
	 * data of the successive timesheet.
	 * </li><li>
	 * This method supports no other permutations for adding a weekly
	 * timesheet other than mentioned in this summary.<br />
	 * Note: Delete or update current timesheet listings using other methods
	 * When there is a need to do so. 
	 * </li></lu><p></p>
	 * 
	 * @param registrantID registrant ID for which a timesheet is added.
	 * @param startDate Date the timesheet will start (will be actived).
	 * @param endDate Date the timesheet will end (will be deactivated). 
	 * @param locationData A 2-dimensional array containing rows 
	 * representing a week schedule (a timesheet). Each row contains:<br><br>
	 * <ul><li>
	 * long: Weeknumber (ISO 8601). Monday=1, Tuesday=2, ... Sunday=7 
	 * </li><li>
	 * long: timeUnitID. Represents a part of the day (E.g. "morning").
	 * </li><li>
	 * long: roomspotID. (identifies a specific spot (usyually a desk)
	 * within a specific room for a specific department at a specific 
	 * location). 
	 * </li></ul>
	 * <p>Note: All IDs must be retrieved from database</p> 
	 * 
	 * @throws Exception SQL or business rules violations exceptions.
	 * @return timesheetID ID of the added (or inserted) timesheet.
	 */
	public long addWeekTimeSheet(long registrantID, LocalDate startDate, 
			LocalDate endDate, ArrayList<Long[]> locationData)
					throws Exception {

		// initialization
		ResultSet resultSet = null; // initially no data.
		long timesheetID = 0; // return value, initially no timesheet added.
		connection.setAutoCommit(false); // start transaction.
		
		try {  
			// Preliminary check:
			// Check if the start date is on or after the current date.
			// And if the end date is after the start date
			// Business rule: A timesheet cannot be created for or in the past.
			if (LocalDate.now().isAfter(startDate) 
				|| endDate.isBefore(startDate) 
				) {
				throw new DateTimeException("One or more invalid dates"
						+ ". Current date is: " + LocalDate.now()
						+ ". Start date value passed is: " + startDate
						+ ". End date value passed is: " + endDate);
			}
			
			// When Preliminary check is passed:
			statement.execute("LOCK TABLES "
					+ "`registrant` READ, "
					+ "`registranttimesheet` WRITE, "
					+ "`timesheet` WRITE, "
					+ "`weekschedule` WRITE");
			
			// Business rule: registrant must exist.
			// Note: checked here, should be guarded by referential integrity.
			resultSet = statement.executeQuery("SELECT COUNT(registrantID) "
					+ "FROM registrant WHERE registrantID=" + registrantID);
			resultSet.next(); // one and only row containing "count"
			
			if (resultSet.getInt("COUNT(registrantID)") == 0) {
				throw new Exception("RegistrantID=" + registrantID 
					+ ". RegistrantID nonexistent or (recently) deleted.");
			}
		
			// Get all relevant timesheets data for this specific registrant.
			String sql="SELECT timesheet.timesheetID, "
					+ "actuationDate, "
					+ "closingDate "
					+ "FROM timesheet, registranttimesheet "
					+ "WHERE registranttimesheet.timesheetID=timesheet.timesheetID "
					+ "AND registrantID=" + registrantID + " "
					+ "ORDER BY actuationDate";
			resultSet = statement.executeQuery(sql);
			
			ArrayList<Long> timesheetIDs = new ArrayList<Long>();
			ArrayList<String> actuationDates = new ArrayList<String>();
			ArrayList<String> closingDates = new ArrayList<String>();
			int currentIndex = 0;
			int idx = 0;
			
			// retrieve all relevant timesheet data.
			while (resultSet.next()) {
				timesheetIDs.add(resultSet.getLong("timesheetID"));
				closingDates.add(resultSet.getString("closingDate"));
				String actuationDate = resultSet.getString("actuationDate");
				actuationDates.add(actuationDate);
				
				currentIndex++;
				
				// identify where the new timesheet has to be placed 
				// in the chain of timesheets.
				if (startDate.toString().compareTo(actuationDate) >= 1) {
					idx = currentIndex;
				}
			}
		
			Long pastTimesheetID = null; 
			Long futureTimesheetID = null;
			
			// Set values for sql insert statement of new timesheet 
			if (0 == currentIndex) {
				// first timesheet. Add new timesheet
				pastTimesheetID = null; 
				futureTimesheetID = null;
			} else {
				if (0 == idx) {
					// add timesheet at begin of chain
					pastTimesheetID = null; 
					futureTimesheetID = timesheetIDs.get(0);
					String SuccesiveActuationDate = actuationDates.get(idx);
					
					// Business rule: When a timesheet is inserted before
					// an existing timesheet then its startDate must be
					// past its former timesheet endDate (if there is a former
					// timesheet). And its endDate must end before its  
					// succesive timesheet actuationDate.
					if (endDate.toString().compareTo(SuccesiveActuationDate) 
							>= 1) {
						throw new Exception("New timesheet period overlaps "
								+ "its succesive timesheet period with "
								+ "timesheetID: " 
								+ timesheetIDs.get(idx)
								+ ". Time overlap of timesheets not allowed.");
					}
				} else if (currentIndex == idx){
					// append new timesheet at end of chain
					pastTimesheetID =timesheetIDs.get(idx - 1); 
					futureTimesheetID = null;
				} else {
					// insert new timesheet within two chain links
					pastTimesheetID = timesheetIDs.get(idx - 1); 
					futureTimesheetID = timesheetIDs.get(idx);
					String SuccesiveActuationDate = actuationDates.get(idx);
					
					// Business rule: (see above)
					if (endDate.toString().compareTo(SuccesiveActuationDate) 
							>= 1) {
						throw new Exception("New timesheet period overlaps "
								+ "its succesive timesheet period with "
								+ "timesheetID: " 
								+ timesheetIDs.get(idx)
								+ ". Time overlap of timesheets not allowed.");
					}
				}
			}
			
			// add or insert new timesheet
			sql = "INSERT INTO `timesheet` "
					+ "(`actuationDate`, "
					+ "`closingDate`, "
					+ "`futureTimesheetID`,"
					+ "`pastTimesheetID`) "
					+ "VALUES "
					+ "(\"" + startDate.toString() + "\", "
					+ "\"" + endDate.toString() + "\", "
					+ futureTimesheetID + ", "
					+ pastTimesheetID + ")"; 
			statement.executeUpdate(sql);

			// get last_inserted_id
			resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
			resultSet.next(); // one and only row containing timesheetID
			timesheetID = resultSet.getLong("LAST_INSERT_ID()");
			
			if (null != futureTimesheetID) {
				// chain inserted timesheetID into the succesive timesheet
				// as pastTimesheetID (i.e. chain the timesheets)
				sql = "UPDATE `timesheet` SET "
						+ "`pastTimesheetID`=" + timesheetID + " "
						+ "WHERE `timesheetID`=" + futureTimesheetID;

				statement.executeUpdate(sql);
			} 
			
			if (null != pastTimesheetID) {
				// chain inserted timesheetID into the former timesheet
				// as futureTimesheetID (i.e. chain the timesheets)
				// Business rule: The timesheet closing data of the
				// previous timesheet must be at least one day less 
				// than the actuationDate of the inserted timesheet.
				// When not, this closing date will be set to the day
				// previous to the actuationDate of the inserted
				// timesheet.
				String previousClosingDate = closingDates.get(idx - 1);
				
				if (previousClosingDate.compareTo(startDate.toString()) >= 1)
				{
					previousClosingDate = 
							startDate.minusDays(1L).toString();
				}
				
				sql = "UPDATE `timesheet` SET "
						+ "`closingDate`=\"" + previousClosingDate + "\", "
						+ "`futureTimesheetID`=" + timesheetID + " "
						+ "WHERE `timesheetID`=" + pastTimesheetID;
				statement.executeUpdate(sql);
			}
			
			// Register the registrant-timesheet relation.
			sql = "INSERT INTO `registranttimesheet` "
					+ "(`registrantID`, `timesheetID`) "
					+ "VALUES ("
					+ registrantID + ","
					+ timesheetID + ")";
			statement.executeUpdate(sql);
			
			// Register the timesheet schedule details
			int weekdayIndex = 0;
			int timeUnitIndex = 1;
			int roomspotIDindex = 2;
			String values = "";
			
			for (Long[] details : locationData) {
				values += ",(" + timesheetID + ","
						+ Math.toIntExact(details[weekdayIndex]) + ","
						+ details[timeUnitIndex] + ","
						+ details[roomspotIDindex] + ")";
			}
			
			sql = "INSERT INTO `weekschedule` "
					+ "(`timesheetID`, `weekday`, `timeUnitID`, `roomSpotID`) "
					+ "VALUES " + values.substring(1);
			statement.executeUpdate(sql);

			connection.commit();
		} catch (Exception e) {
			timesheetID = 0L;
			connection.rollback();
			throw e; // let the method invoker decide what to do...
		}
		
		statement.execute("UNLOCK TABLES");
		return timesheetID;
	}
	
	/**
	 * <p>
	 * Remove timesheet.
	 * </p><p>
	 * A timesheet can be removed only when the timesheet's actuation date
	 * is today or in the future (in other words, when it was never used).
	 * </p><p>
	 * Timesheets of which the actuation data is before current date cannot 
	 * be removed but they can be closed.
	 * </p>
	 * The list returned contains per row objects of the following types:
	 * </p>
	 * *param timesheetID The timesheetID to be removed	
	 * 		
	 * @return registrant timesheets list.
	 * @throws Exception Database exception or illegal remove action exception. 
	 */
	public void removeTimesheet(Long timesheetID) 
			throws Exception {
		// initialization
		ResultSet resultSet = null; // initially no data.
		connection.setAutoCommit(false); // start transaction.

		try {
			statement.execute("LOCK TABLES "
					+ "`registrant` READ, "
					+ "`registranttimesheet` WRITE, "
					+ "`timesheet` WRITE, "
					+ "`weekschedule` WRITE");
			
			// timesheet will be removed, linked list references 
			// will be updated.
			String sql = "SELECT `futureTimesheetID`, `pastTimesheetID` "
					+ "FROM `timesheet` "
					+ "WHERE `timesheetID` = " + timesheetID;
			resultSet = statement.executeQuery(sql);
			
			// Modify former and successive link (when exists)
			resultSet.next();
			Long futureTimesheetID = resultSet.getLong("futureTimesheetID");
			Long pastTimesheetID = resultSet.getLong("pastTimesheetID");
			
			
			// update reference link former timesheet
			if (futureTimesheetID != null) {
				sql = "UPDATE `timesheet` "
						+ "SET `futureTimesheetID`=" + futureTimesheetID
						+ " WHERE `timesheetID`=" + pastTimesheetID;
				statement.executeUpdate(sql);
			}
			
			// update reference link successive timesheet
			if (pastTimesheetID != null) {
				sql = "UPDATE `timesheet` "
						+ "SET `pastTimesheetID`=" + pastTimesheetID
						+ " WHERE `timesheetID`=" + futureTimesheetID;
				statement.executeUpdate(sql);
			}
			
			// remove timesheet requested to be removed.
			//sql = "DELETE FROM `timesheet` WHERE `timesheetID`=" + timesheetID;
			//statement.executeUpdate(sql); 
			connection.commit();
		}catch (SQLException e) {
			System.out.println ("Caught java.sql.SQLIntegrityConstraintViolationException") ;
			connection.rollback();
		} catch(Exception e) {
			connection.rollback();
			throw e; // let the method invoker decide what to do...
		}
		
		statement.execute("UNLOCK TABLES");
	}
	
	/**
	 * <h1>Creating language support for a new language</h1>
	 * <p>
	 * This method initially copies all default language text records and 
	 * stores these records as new language records supporting the language 
	 * identified by the language code passed to this method.<br>
	 * These copied records are <i>not</i> translated. Translating these new
	 * records in the language identified by the language code is a separate
	 * and manual action that must be performed <i>after</i> the execution
	 * of this method.
	 * <p></p>
	 * Preconditions:<br>
	 * <ul><li>
	 * The language code passed must be ISO 639-1 compliant.
	 * </li><li>
	 * No text records must exist for the language code passed prior to
	 * the execution of this method.
	 * </li></ul>
	 * <p>
	 * Postconditions:<br>
	 * Database remains consistent. That is, either a <i>complete</i> set of 
	 * records is added, or the database remains as is (i.e. all or nothing).  
	 * </p> 
	 * 
	 * @param languageCode An ISO 639-1 2-letter compliant language code
	 * @throws Exception "language code not 639-1 compliant exception", 
	 * 	"language already defined exception", any database exception.
	 */
	public void addLanguage(String languageCode) 
			throws Exception {
		// initialization
		ResultSet resultSet = null; // initially no data.
		connection.setAutoCommit(false); // start transaction.

		try {
			// Satisfying preconditions:
			// The language code passed must be ISO 639-1 compliant.
			resultSet = statement.executeQuery("SELECT COUNT(languageCode) "
					+ "FROM i18n_iso639_1 WHERE languageCode=\"" 
					+ languageCode + "\"");
			resultSet.next(); 
			
			if (resultSet.getInt("COUNT(languageCode)") == 0) {
				throw new Exception("languageCode passed: " + languageCode 
					+ ". Language code not 639-1 compliant "
					+ "(or languageCode removed from i18n_iso639_1 table).");
			}
			
			// There must not be a single text record supporting the language
			// specified by the language code passed. The creation of
			// these records is all or nothing for this method.
			resultSet = statement.executeQuery("SELECT COUNT(language) "
					+ "FROM `text` "
					+ "WHERE language=\"" + languageCode + "\"");
			resultSet.next(); 
			
			int recordCount = resultSet.getInt("COUNT(language)");
			if (resultSet.getInt("COUNT(language)") > 0) {
				throw new Exception("(some) Language records already exist. " 
					+ "Number of text records supporting the \"" + languageCode 
					+ "\" language found is: " + recordCount
					+ ". This method creates either all records "
					+ "or takes no action");
			}

			// Copy all default text records to create all new text records
			// supporting the language identified by the language code passed.
			String sql = ("SELECT textID, text " 
					+ "FROM `text` " 
					+ "WHERE language = \"" + this.getDefaultLanguage() 
					+ "\""); 
			resultSet = statement.executeQuery(sql);
			
			// Insert text records supporting the language identified by the
			// language code using all the copied default text records.
			sql = "insert into text (textID, language, text) values ";
			// Add first text record (intialization)
			if (resultSet.next()) {
				sql += "(" + resultSet.getLong("textID") + ", \"" 
						+ languageCode + "\", " 
						+ "\"" + resultSet.getString("text")  + "\")";
			}
			
			// add all other text records
			while(resultSet.next()) {
				sql += ", (" + resultSet.getLong("textID") 
						+ ", \"" + languageCode + "\"" 
						+ ", \"" + resultSet.getString("text")  + "\")";
			}
			
			// Close SQL statement (finalization)
			sql += ";";
			
			// insert all new records
			statement.executeUpdate(sql);
			
			connection.commit();
		} catch(Exception e) {
			connection.rollback();
			throw e; // let the method invoker decide what to do...
		}
	}
}