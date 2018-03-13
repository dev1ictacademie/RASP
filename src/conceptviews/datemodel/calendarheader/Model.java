package conceptviews.datemodel.calendarheader;


import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	private final ObservableList<Student> studentList = FXCollections.observableArrayList(student ->
    new Observable[] {student.fNameProperty(), student.lNameProperty()});

	private final ObjectProperty<Student> currentStudent = new SimpleObjectProperty<>(null);

	public ObjectProperty<Student> currentStudentProperty() {
        return currentStudent ;
    }
	public final Student getCurrentStudent() {
        return currentStudentProperty().get();
    }

    public final void setCurrentStudent(Student Student) {
        currentStudentProperty().set(Student);
    }

    public ObservableList<Student> getStudentList() {
        return studentList ;
    }


	public void loadData(){

		studentList.setAll(
		new Student("Piet", "Pietersen" ),
		new Student("Jan", "Jansen"),
		new Student("Kees", "Groot" ),
		new Student("Hans", "Klein"),
		new Student("Cornelis", "Boer, den" ),
		new Student("Frederik", "Schoenlapper"),
		new Student("Kees", "Groot, de" ),
		new Student("Hans", "Klein"),
		new Student("Karel", "Bakker" ),
		new Student("Fred", "Lubbers"),
		new Student("Ton", "Hout , van" ),
		new Student("Hans", "Vries , de"),
		new Student("Karel", "Appel"),
		new Student("Paula", "Aardbei"),
		new Student("Piet", "Derksen"),
		new Student("Klaas" , "Graaf, de")
		);
		}

}
