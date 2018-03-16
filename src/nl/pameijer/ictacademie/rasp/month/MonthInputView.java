package nl.pameijer.ictacademie.rasp.month;

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
 * Test for how to use CalendarHeader and DateModel
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


	@Override
	public void start(Stage primaryStage) throws Exception {

		//scrollpane
		ScrollPane scrollPane = new ScrollPane();
		// Box to hold views
		root = new VBox();
		// Scene
		Scene scene = new Scene(scrollPane, 1600, 600);
		// set this month and year to dateModel
		dateModel = new DateModel(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
		createDataModelListeners();
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
	//TODO getter for properties and move listeners to controller
	public void createDataModelListeners() {
		dateModel.yearProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				vbHeaders.getChildren().clear();
				root.getChildren().add(createGridPaneColumns());
				System.out.println("yearProperty");
			}
		});

		dateModel.monthProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				vbHeaders.getChildren().clear();
				root.getChildren().add(createGridPaneColumns());

			}
		});
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
		//TODO add different css setting for even rows (lightgrey)for userdata and textfield
		//TODO method for setting bottom border for textfields from schedules/dayparts
		//TODO method for cell layout now it is duplicate code
		ObservableList<Student> studentList = model.getStudentList();
		for (int row = 1; row < model.getStudentList().size(); row++) {
			// id
			Label idLabel = new Label("" + row);
			idLabel.getStyleClass().add("userlabel");
			GridPane.setFillWidth(idLabel, true);
			GridPane.setFillHeight(idLabel, true);
			idLabel.setMaxWidth(Double.MAX_VALUE);
			idLabel.setMaxHeight(Double.MAX_VALUE);
			idLabel.setAlignment(Pos.CENTER);
			grid.add(idLabel, 0, row);
			// firstname
			Label firstNameLabel = new Label(studentList.get(row - 1).getFName());
			firstNameLabel.getStyleClass().add("userlabel");
			GridPane.setFillWidth(firstNameLabel, true);
			GridPane.setFillHeight(firstNameLabel, true);
			firstNameLabel.setMaxWidth(Double.MAX_VALUE);
			firstNameLabel.setMaxHeight(Double.MAX_VALUE);
			firstNameLabel.setPadding(new Insets(0, 0, 0, 5));
			// lastname
			grid.add(firstNameLabel, 1, row);
			Label lastNameLabel = new Label(studentList.get(row - 1).getLName());
			lastNameLabel.getStyleClass().add("userlabel");
			GridPane.setFillWidth(lastNameLabel, true);
			GridPane.setFillHeight(lastNameLabel, true);
			lastNameLabel.setMaxWidth(Double.MAX_VALUE);
			lastNameLabel.setMaxHeight(Double.MAX_VALUE);
			lastNameLabel.setPadding(new Insets(0, 0, 0, 5));

			grid.add(lastNameLabel, 2, row);
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
							if(!(newValue.equals("x") || newValue.equals("z") || newValue.equals("v") || newValue.equals("a"))){
								txt.setText("");
							}

						}
					});

					grid.add(txt, i + 3, row);
				}

			}
		}
	}

	public static void main(String[] args) {

		launch(args);
	}

}
