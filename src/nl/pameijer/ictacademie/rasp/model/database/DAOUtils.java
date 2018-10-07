package nl.pameijer.ictacademie.rasp.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.pameijer.ictacademie.rasp.model.Student;

public class DAOUtils implements DAO {




	private Connection connection;
	private Statement statement;


	public void setConnectionDatabase() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement();
			System.out.println("Database connection !");

		} catch (Exception connectException) {
			connectException.printStackTrace();
			System.out.println("no connection");
		}
	}

	private Student createStudent(ResultSet rs) {
		Student student = new Student();

		try {
			student.setId(rs.getString("registrantID"));
			student.setFName(rs.getString("firstname"));
			student.setLName(rs.getString("middlename"));
			student.setLName(rs.getString("lastname"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return student;
	}

	private String[] createStudentRow (ResultSet rs){
		String[] row = new String[4];
		try {
			row[0] = rs.getString("registrantID");
			row[1] = rs.getString("lastname");
			row[2] = rs.getString("firstname");
			if(rs.getString("middlename") !=null){
			row[3] = rs.getString("middlename");
			}else{
				row[3]="";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return row;
	}
	
	//TODO testing purposes:  rearrange to 2d array for use in existing PesistencyLayer.contructSchedules
	
	public String[][] createSchedules2DArray(){
		System.out.println("creating schedules..");
		String[][] array = null;


		 try {
			 String sql = ("SELECT "
						+ "a.timesheetID,"
						+ "a.actuationDate,"
						+ "a.closingDate,"
						+ "c.registrantID,"
						+ "c.lastName,"
						+ "c.firstName,"
						+ "c.middleName,"
						+ "d.weekday,"
						+ "g.text AS timeUnit,"
						+ "h.text AS role,"
						+ "i.roomSpotID,"
						+ "k.text AS spot,"
						+ "m.text AS room,"
						+ "p.text AS department,"
						+ "q.place,"
						+ "q.street,q.zipcode,"
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
						+ "AND g.language = 'nl' "
						+ "AND e.title = h.textID "
						+ "AND h.language = 'nl' "
						+ "AND k.language = 'nl' "
						+ "AND d.roomSpotID = i.roomSpotID "
						+ "AND i.roomID = l.roomID "
						+ "AND m.language = 'nl' "
						+ "AND i.roomID = n.roomID "
						+ "AND p.language = 'nl' "
						+ "AND o.locationID = q.locationID "
						+ "AND r.language = 'nl'");


			 ResultSet rs = statement.executeQuery(sql);

	         rs.last();//moves cursor to last row
	         array = new String[rs.getRow()][];
	         rs.beforeFirst();
	         String ID =null;
	         StringBuilder row = null;
	         while (rs.next()) {

	        	 if(ID == null){
	        		 ID = rs.getString("registrantID");
	        		 row = new StringBuilder();
		        	 row.append("ID " + rs.getString("registrantID"));
		        	 row.append(" Actuation date: " + rs.getString("actuationDate"));
		        	 row.append(" Closing date: " + rs.getString("closingDate"));
		        	 row.append(" Weekday: " + rs.getString("weekday") );
		        	 row.append(" Time unit: " + rs.getString("timeUnit"));
		        	 row.append(" Spot: " +rs.getString("spot"));


	        	 }else{
	        		 if(ID.equals(rs.getString("registrantID"))){
	        			 //System.out.println(" add dayparts");
	        			 row.append(" Weekday: " + rs.getString("weekday"));
	        			 row.append(" Time unit: " + rs.getString("timeUnit"));
	        			 row.append(" Spot: " +rs.getString("spot"));

	        		 }else{
	        			 row = new StringBuilder();
	        			 ID = rs.getString("registrantID");
		        		 row = new StringBuilder();
			        	 row.append("ID " + rs.getString("registrantID"));
			        	 row.append(" Actuation date: " + rs.getString("actuationDate"));
			        	 row.append(" Closing date: " + rs.getString("closingDate"));
			        	 row.append(" Weekday: " + rs.getString("weekday") );
			        	 row.append(" Time unit: " + rs.getString("timeUnit"));
			        	 row.append(" Spot: " +rs.getString("spot"));

	        		 }
	        	 }


	        	 System.out.println(row);



	         }
	         rs.close();
	         connection.close();
	      } catch (SQLException ex) {
	    	  ex.printStackTrace();
	      }

		return array;

	}




	public String[][] createStudent2DArray(){

		String sql = "Select * from registrant";
		String[][] array = null;
		 try {


	         ResultSet rs = statement.executeQuery(sql);
	         rs.last();//moves cursor to last row
	         array = new String[rs.getRow()][];
	         rs.beforeFirst();
	         int i= 0;
	         while (rs.next()) {

	            String[] row = createStudentRow(rs);
	            array[i] = row;
	            i++;
	         }
	         rs.close();

	      } catch ( SQLException ex) {
	      }



		return array;

	}


	public List<Student> getStudents() {
	      String sql = "Select * from registrant";
	      List<Student> list = new ArrayList<>();
	      try {
	         ResultSet rs = statement.executeQuery(sql);
	         while (rs.next()) {
	            Student s = createStudent(rs);
	            list.add(s);
	         }
	         rs.close();

	      } catch (SQLException ex) {
	      }
	      return list;
	   }




	public static void main(String[] args) {
		DAOUtils dbUtils = new DAOUtils();
		dbUtils.setConnectionDatabase();
		/*for (Student s : dbUtils.getStudents()){
			if(s.getNamePrefix()==null){
				System.out.println(s.getId()+ " - " +s.getFName() + " " + s.getLName());
			}else{
				System.out.println(s.getId()+ " - " +s.getFName() + " " + s.getNamePrefix()+ " - " + s.getLName());
			}
		}*/

		String[][] array = dbUtils.createStudent2DArray();

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.println(array[i][j]);

			}


		}
		dbUtils.createSchedules2DArray();

	}

}
