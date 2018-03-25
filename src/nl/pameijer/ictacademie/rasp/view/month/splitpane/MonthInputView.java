package nl.pameijer.ictacademie.rasp.view.month.splitpane;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MonthInputView {

	private final VBox vBoxStudents;
	private ScrollPane scrollPaneStudents;
	private DateModel dateModel;
	private ColumnHeader columnHeader;
	private ScrollPane scrollPaneDays;
	private VBox vBoxDays;
	private VBox root;
	private SplitPane splitPane;
	private GridPane gridStudents;
	private GridPane gridDays;
	private VBox vBoxCombo;


	public MonthInputView(DateModel dateModel) {
		this.dateModel = dateModel;
		// scrollpane
		scrollPaneStudents = new ScrollPane();
		scrollPaneDays = new ScrollPane();
		//splitpanes
		splitPane = new SplitPane();
		splitPane.setDividerPositions(0.15f);
		// Box to hold views
		root = new VBox();
		vBoxStudents = new VBox();
		vBoxDays = new VBox();
		scrollPaneStudents.setContent(vBoxStudents);
		scrollPaneDays.setContent(vBoxDays);
		columnHeader = new ColumnHeader(dateModel);
		setcomboBoxes();
		setGridPanes();
		root.getChildren().addAll(vBoxCombo ,splitPane);
	}

	public void setcomboBoxes() {
		//comboboxes
		vBoxCombo = new VBox();
		vBoxCombo.getChildren().addAll(getMonthComboBox(), getYearComboBox());
	}
	public void setGridPanes(){
		//grid for students
		gridStudents = new GridPane();
		columnHeader.createUserColumnHeader(gridStudents);
		vBoxStudents.getChildren().add(gridStudents);
		//grid for days
		gridDays = new GridPane();
		columnHeader.createColumnHeaderDays(gridDays);
		vBoxDays.getChildren().add(gridDays);
		splitPane.getItems().addAll(gridStudents , gridDays);

	}

	public void clearRefreshHeader() {

		//FIXME refresh splitpanes is not working
		gridStudents.getChildren().clear();
		gridDays.getChildren().clear();
		//root.getChildren().clear();
		setcomboBoxes();
		setGridPanes();

	}

	/**
	 *
	 * @return ComboBox months
	 */
	public ComboBox<String> getMonthComboBox() {
		ObservableList<String> months = FXCollections.observableArrayList("januari", "februari", "maart", "april",
				"mei", "juni", "juli", "augustus", "september", "oktober", "november", "december");

		ComboBox<String> monthBox = new ComboBox<String>(months);
		monthBox.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
		monthBox.setOnAction(value -> {
			int month = monthBox.getSelectionModel().getSelectedIndex();
			dateModel.setMonth(month + 1);
			System.out.println("monthbox changed: " + (month + 1));
		});

		return monthBox;

	}

	/**
	 *
	 * @return ComboBox years
	 */
	public ComboBox<Integer> getYearComboBox() {
		ObservableList<Integer> years = FXCollections.observableArrayList();
		ComboBox<Integer> yearBox = new ComboBox<Integer>();
		for (int i = 0; i < 25; i++) {
			years.add(2017 + i);
		}
		yearBox.setItems(years);
		yearBox.setValue(dateModel.getYear());
		yearBox.setOnAction(value ->{
			int year = yearBox.getSelectionModel().getSelectedItem();
			dateModel.setYear(year);
			System.out.println("yearbox changed: " + year);
		});

		return yearBox;

	}

	public Parent asParent() {

		return root;
	}

}
