package nl.pameijer.ictacademie.rasp.view.month;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.pameijer.ictacademie.rasp.model.Model;
import nl.pameijer.ictacademie.rasp.model.Student;

/**
 * monthview
 *
 * @author devadv
 *
 */
public class MonthInputView extends Application {

	private CalendarHeaderView calHeaderView;
	private VBox root;
	private VBox vbHeaders;
	private DateModel dateModel;
	private Model model;
	private Node label;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// scrollpane
		ScrollPane scrollPane = new ScrollPane();
		// Box to hold views
		root = new VBox();
		// Scene
		Scene scene = new Scene(scrollPane, 1600, 600);
		// set this month and year to dateModel
		dateModel = new DateModel(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

		// Model
		model = new Model();
		model.loadData();
		// calendar view with Boxes
		calHeaderView = new CalendarHeaderView(dateModel);
		root.getChildren().add(createComboBoxes());
		root.getChildren().add(createGridPaneColumns());
		scrollPane.setContent(root);

		// set CSS
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("CalendarHeaderViewTest");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public HBox createComboBoxes() {
		// set comboboxes
		HBox hb = new HBox();
		ComboBox<String> comb1 = calHeaderView.getMonthComboBox();
		// set Listener for monthbox
		comb1.setOnAction(value -> {
			int month = comb1.getSelectionModel().getSelectedIndex() + 1;
			System.out.println("month changed " + (month + 1));
			dateModel.setMonth(month);
		});
		ComboBox<Integer> comb2 = calHeaderView.getYearComboBox();
		// set Listener for yearbox
		comb2.setOnAction(value -> {
			int year = comb2.getSelectionModel().getSelectedItem();
			System.out.println("year changed " + year);
			dateModel.setYear(year);
		});
		hb.getChildren().add(comb1);
		hb.getChildren().add(comb2);
		return hb;

	}

	public void clearRefreshHeader() {
		vbHeaders.getChildren().clear();
		root.getChildren().add(createGridPaneColumns());
	}



	private VBox createGridPaneColumns() {
		// vertical box to hold headers
		vbHeaders = new VBox();
		Label gridLabel = new Label("GridPane");
		vbHeaders.getChildren().add(gridLabel);
		// grid
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		// set first three columns
		grid.getColumnConstraints().add(new ColumnConstraints(40));
		grid.getColumnConstraints().add(new ColumnConstraints(100));
		grid.getColumnConstraints().add(new ColumnConstraints(150));

		Label idLabel = new Label("id");
		GridPane.setConstraints(idLabel, 0, 0);
		GridPane.setFillWidth(idLabel, true);
		GridPane.setFillHeight(idLabel, true);
		idLabel.setMaxWidth(Double.MAX_VALUE);
		idLabel.setMaxHeight(Double.MAX_VALUE);
		idLabel.setAlignment(Pos.CENTER);

		Label firstname = new Label("Voornaam");
		GridPane.setConstraints(firstname, 1, 0);
		GridPane.setFillWidth(firstname, true);
		GridPane.setFillHeight(firstname, true);
		firstname.setMaxWidth(Double.MAX_VALUE);
		firstname.setMaxHeight(Double.MAX_VALUE);
		firstname.setAlignment(Pos.CENTER);

		Label lastname = new Label("Achternaam");
		GridPane.setConstraints(lastname, 2, 0);
		GridPane.setFillWidth(lastname, true);
		GridPane.setFillHeight(lastname, true);
		lastname.setMaxWidth(Double.MAX_VALUE);
		lastname.setMaxHeight(Double.MAX_VALUE);
		lastname.setAlignment(Pos.CENTER);

		grid.getChildren().addAll(idLabel, firstname, lastname);
		// set columnheaders
		ArrayList<VBox> list = calHeaderView.createColumnHeaders();
		int col = 3;
		for (int i = 0; i < list.size(); i++) {
			VBox label = list.get(i);
			grid.add(label, col, 0, 2, 1);
			col += 2;
		}
		// create textfields for users
		createRowsUserTextFields(grid);

		vbHeaders.getChildren().add(grid);
		return vbHeaders;
	}

	public void createRowsUserTextFields(GridPane grid) {
		int daysOfMonth = dateModel.getLengthOfMonth();
		String[] dayNames = dateModel.dayNameList();
		// TODO add different css setting for even rows (lightgrey)for userdata
		// and textfield
		// TODO method for setting bottom border for textfields from
		// schedules/dayparts
		// TODO method for cell layout now it is duplicate code
		ObservableList<Student> studentList = model.getStudentList();
		for (int row = 1; row < model.getStudentList().size(); row++) {
			// id
			Label idLabel = new Label("" + row);
			createLabels(idLabel, grid, row, 0);
			// firstname
			Label firstNameLabel = new Label(studentList.get(row - 1).getFName());
			createLabels(firstNameLabel, grid, row, 1);
			// lastname
			Label lastNameLabel = new Label(studentList.get(row - 1).getLName());
			createLabels(lastNameLabel, grid, row, 2);

			for (int i = 0; i < daysOfMonth * 2; i++) {
				if (dayNames[i / 2].equals("za") || dayNames[i / 2].equals("zo")) {
					Pane pane = new Pane();
					pane.setPrefWidth(10);
					pane.getStyleClass().add("weekend");
					grid.add(pane, i + 3, row);
				} else {
					TextField txt = new TextField();
					txt.setId(" " + (row - 1));
					txt.setPrefWidth(25);
					txt.textProperty().addListener(new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
							if (!(newValue.equals("x") || newValue.equals("z") || newValue.equals("v")
									|| newValue.equals("a"))) {
								txt.setText("");
							}

						}
					});

					grid.add(txt, i + 3, row);
				}

			}
		}
	}// end method createRowsUserTextFields

	public void createLabels(Label label, GridPane grid, int row, int col) {
		label.getStyleClass().add("userlabel");
		GridPane.setFillWidth(label, true);
		GridPane.setFillHeight(label, true);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setMaxHeight(Double.MAX_VALUE);
		label.setAlignment(Pos.CENTER);
		grid.add(label, col, row);
		label.setPadding(new Insets(0, 0, 0, 5));
	}

	public DateModel getDateModel() {
		return dateModel;
	}

}
