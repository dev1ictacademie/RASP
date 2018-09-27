package nl.pameijer.ictacademie.rasp.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.pameijer.ictacademie.rasp.model.Student;

public class DAOUtils implements DAO {




	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

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
	public List<Student> getStudents() {
	      String sql = "Select * from registrant";
	      List<Student> list = new ArrayList<>();
	      try {
	         Class.forName(DRIVER);
	         Connection con = DriverManager.getConnection
	            (DB_URL, USER, PASS);
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(sql);
	         while (rs.next()) {
	            Student s = createStudent(rs);
	            list.add(s);
	         }
	         rs.close();
	         con.close();
	      } catch (ClassNotFoundException | SQLException ex) {
	      }
	      return list;
	   }




	public static void main(String[] args) {
		DAOUtils dbUtils = new DAOUtils();
		dbUtils.setConnectionDatabase();
		for (Student s : dbUtils.getStudents()){
			if(s.getNamePrefix()==null){
				System.out.println(s.getId()+ " - " +s.getFName() + " " + s.getLName());
			}else{
				System.out.println(s.getId()+ " - " +s.getFName() + " " + s.getNamePrefix()+ " - " + s.getLName());
			}
		}

	}

}
