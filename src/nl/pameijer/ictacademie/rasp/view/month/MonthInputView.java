package nl.pameijer.ictacademie.rasp.view.month;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.pameijer.ictacademie.rasp.model.DayPart;
import nl.pameijer.ictacademie.rasp.model.Student;
import nl.pameijer.ictacademie.rasp.view.month.Controller.DayPartsListener;
import sun.security.action.GetLongAction;

public class MonthInputView {

	private DateModel dateModel;
	private ColumnHeader columnHeader;
	// combos
	private VBox vBoxCombo;
	// scrollPanes
	private ScrollPane sStudentsHeader;
	private ScrollPane sStudents;
	private ScrollPane sDaysHeader;
	private ScrollPane sDaysTextFields;
	// splitpanes
	private SplitPane splitPane;
	private SplitPane splitPaneHeaders;
	private SplitPane splitPaneStudentsTextFields;
	// gridpanes
	private GridPane gridStudentsHeader;
	private GridPane gridStudents;
	private GridPane gridDaysHeader;
	private GridPane gridDaysTextFields;
	private VBox root;
	private TextField txt;
	private DayPartsListener dayPartsListener;

	public MonthInputView(DateModel dateModel) {
		this.dateModel = dateModel;

		root = new VBox();
		// scrollpane
		sStudentsHeader = new ScrollPane();
		sStudents = new ScrollPane();
		sDaysHeader = new ScrollPane();
		sDaysTextFields = new ScrollPane();
		// splitpanes
		splitPane = new SplitPane();
		splitPane.setDividerPositions(0.15f);
		splitPane.setOrientation(Orientation.VERTICAL);
		splitPaneHeaders = new SplitPane();
		splitPaneHeaders.setOrientation(Orientation.HORIZONTAL);
		splitPaneHeaders.setDividerPositions(0.21f);
		splitPaneHeaders.setMinHeight(54);
		splitPaneHeaders.setMaxHeight(54);
		splitPaneStudentsTextFields = new SplitPane();
		splitPaneStudentsTextFields.setOrientation(Orientation.HORIZONTAL);
		splitPaneStudentsTextFields.setDividerPositions(0.21f);
		// gridpanes
		gridStudentsHeader = new GridPane();
		gridStudents = new GridPane();
		gridDaysHeader = new GridPane();
		gridDaysTextFields = new GridPane();
		// scrollpanes
		sStudentsHeader.setContent(gridStudentsHeader);
		sStudentsHeader.setHbarPolicy(ScrollBarPolicy.NEVER);
		sStudentsHeader.setVbarPolicy(ScrollBarPolicy.NEVER);
		sStudentsHeader.setMinWidth(290);
		sStudentsHeader.setMaxWidth(290);
		sStudents.setContent(gridStudents);
		sStudents.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sStudents.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sStudents.setMinWidth(290);
		sStudents.setMaxWidth(290);
		sDaysHeader.setContent(gridDaysHeader);
		sDaysHeader.setHbarPolicy(ScrollBarPolicy.NEVER);
		sDaysHeader.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sDaysTextFields.setContent(gridDaysTextFields);
		sDaysTextFields.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sDaysTextFields.setContent(gridDaysTextFields);
		// bindings
		sStudents.vvalueProperty().bindBidirectional(sDaysTextFields.vvalueProperty());
		sDaysHeader.hvalueProperty().bind(sDaysTextFields.hvalueProperty());
		// comboboxes
		setcomboBoxes();
		// columnheader object for studentheader
		columnHeader = new ColumnHeader(dateModel);
		columnHeader.createStudentColumnHeader(gridStudentsHeader);
		// columnHeader.createColumnHeaderDays(gridDaysHeader);
		// days header
		setDaysHeader();
		setSplitPanes();

	}

	public void setcomboBoxes() {
		// comboboxes
		vBoxCombo = new VBox();
		vBoxCombo.getChildren().addAll(getMonthComboBox(), getYearComboBox());
		root.getChildren().add(vBoxCombo);
	}

	public void setDaysHeader() {
		columnHeader.createColumnHeaderDays(gridDaysHeader);
	}

	public void setSplitPanes() {
		splitPaneHeaders.getItems().addAll(sStudentsHeader, sDaysHeader);
		splitPaneStudentsTextFields.getItems().addAll(sStudents, sDaysTextFields);
		splitPane.getItems().addAll(splitPaneHeaders, splitPaneStudentsTextFields);
		root.getChildren().add(splitPane);
	}

	public void clearRefreshHeader() {

		gridStudents.getChildren().clear();
		gridDaysTextFields.getChildren().clear();
		gridDaysHeader.getChildren().clear();
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

		yearBox.setOnAction(value -> {
			int year = yearBox.getSelectionModel().getSelectedItem();
			dateModel.setYear(year);
			System.out.println("yearbox changed: " + year);
		});

		return yearBox;

	}

	/**
	 * Fills the grid with id, first name and last name labels
	 *
	 * @param students
	 * @param grid
	 */
	public void fillStudents(ObservableList<Student> students, GridPane grid) {
		for (int i = 0; i < students.size(); i++) {
			Label idLabel = new Label();
			idLabel.setPrefHeight(30);
			idLabel.setMinWidth(40);
			idLabel.getStyleClass().add("userlabel");
			idLabel.setText("" + (i + 1));
			grid.add(idLabel, 0, i + 1);
			columnHeader.createLabels(idLabel, grid);
			// first name
			Label firstName = new Label();
			firstName.setMinWidth(100);
			firstName.setText(students.get(i).getFName());
			firstName.getStyleClass().add("userlabel");
			grid.add(firstName, 1, i + 1);
			columnHeader.createLabels(firstName, grid);
			// last name
			Label lastName = new Label();
			lastName.setMinWidth(150);
			lastName.setText(students.get(i).getLName());
			lastName.getStyleClass().add("userlabel");
			grid.add(lastName, 2, i + 1);
			columnHeader.createLabels(lastName, grid);

		}
	}// end method fillStudents

	/**
	 * fillDayparts makes a row of TextFields for each student and put them into
	 * a grid
	 *
	 * @param students
	 * @param grid
	 */
	public void fillDayParts(ObservableList<Student> students, GridPane grid) {
		// TODO Let this method work correctly when switching months through GUI
		String[] dayNames = dateModel.dayNameList();
		for (int row = 0; row < students.size(); row++) {
			// index is a counter for dayparts form daypartlist which is in the
			// DataModel
			for (int col = 0, index = 0; col < dateModel.getLengthOfMonth() * 2; col++) {
				// weekend
				if (dayNames[col / 2].equals("za") || dayNames[col / 2].equals("zo")) {
					Pane pane = new Pane();
					pane.setMinWidth(15);
					pane.getStyleClass().add("weekend");
					grid.add(pane, col, row + 1, 1, students.size());
				} else {
					txt = new TextField();

					System.out.println(students.get(row).getFName());
					txt.setPrefSize(30, 30);
					txt.getStyleClass().add("textfield");

					if (students.get(row).isExpected(
							LocalDate.of(dateModel.getYear(), dateModel.getMonth(), col / 2 + 1),
							dateModel.getDayPartList().get(index))) {

						txt.setStyle(" -fx-border-color: #073E70 #073E70 red #073E70");
					} else {
						txt.setStyle("-fx-border-color: #073E70");
					}

					txt.setId("" + students.get(row).getFName() + "-" + students.get(row).getLName() + "-"
							+ LocalDate.of(dateModel.getYear(), dateModel.getMonth(), col / 2 + 1) + "-"
							+ (dateModel.getDayPartList().get(index)));
					System.out.println(txt.getId());
					index++;

					txt.textProperty().addListener((observable)-> {

						StringProperty obs = (StringProperty) observable;
						System.out.println("Change: "+ (obs.getValue()));


						if (!(obs.getValue().equals("x") || obs.getValue().equals("z")
								|| obs.getValue().equals("v") || obs.getValue().equals("a"))) {
							System.out.println("not allowed");
							obs.setValue("");

						}

					});
					// add change listener class to dayparts text fields
					txt.textProperty().addListener(dayPartsListener);

					grid.add(txt, col, row + 1);
				}

			} // end inner for
} // end outer for

}// end method fillDayParts


	public GridPane getGridStudents() {
		return gridStudents;
	}

	public GridPane getGridDays() {
		return gridDaysTextFields;
	}

	public Parent asParent() {

		return root;
	}

	public static ArrayList<Node> getAllNodes(Parent root) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		addAllDescendents(root, nodes);
		return nodes;
	}

	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
		for (Node node : parent.getChildrenUnmodifiable()) {
			nodes.add(node);
			if (node instanceof Parent)
				addAllDescendents((Parent) node, nodes);
		}
	}

	public DayPartsListener getDayPartsListener() {
		return dayPartsListener;
	}

	public void addDayPartsListener(DayPartsListener dayPartsListener) {
		this.dayPartsListener = dayPartsListener;
	}
}
