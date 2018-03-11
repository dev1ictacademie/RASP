package conceptviews.datemodel.calendarheader;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Test for how to use  CalendarHeader and DateModel
 * @author devadv
 *
 */
public class CalendarHeaderViewTest extends Application {

	private CalendarHeaderView calHeaderView;

	private VBox root;
	private VBox vbHeaders;

	private DateModel dateModel;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Box to hold views
		root = new VBox();
		// Scene
		Scene scene = new Scene(root, 1600, 400);
		// set this month and year to dateModel
		dateModel = new DateModel(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
		// calendar view with Boxes
		calHeaderView = new CalendarHeaderView(dateModel);
		root.getChildren().add(createComboBoxes());
		root.getChildren().add(createHeaders());

		primaryStage.setTitle("CalendarHeaderViewTest");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public HBox createComboBoxes() {
		// set comboboxes
		HBox hb = new HBox();
		ComboBox<String> comb1 = calHeaderView.getMonthComboBox();
		//set Listener for monthbox
		comb1.setOnAction(value -> {
			int month = comb1.getSelectionModel().getSelectedIndex() + 1;
			System.out.println("month changed " + ( month + 1));
			dateModel.setMonth(month);
			vbHeaders.getChildren().clear();
			root.getChildren().add(createHeaders());

		});
		ComboBox<Integer> comb2 = calHeaderView.getYearComboBox();
		//set Listener for yearbox
		comb2.setOnAction(value -> {
			int year = comb2.getSelectionModel().getSelectedItem();
			System.out.println("year changed " +year);
			dateModel.setYear(year);
			vbHeaders.getChildren().clear();
			root.getChildren().add(createHeaders());
		});
		hb.getChildren().add(comb1);
		hb.getChildren().add(comb2);
		return hb;

	}

	private VBox createHeaders() {
		// vertical box to hold headers
		vbHeaders = new VBox();
		Label hboxLabel = new Label("HBox");
		vbHeaders.getChildren().add(hboxLabel);
		// HBox
		HBox hb1 = new HBox();
		hb1.setPrefWidth(1200);
		hb1.getChildren().addAll(calHeaderView.createColumnHeaders());
		vbHeaders.getChildren().add(hb1);
		// label
		Label tableLabel = new Label("TableView");
		vbHeaders.getChildren().add(tableLabel);
		// create a table
		TableView table = new TableView();
		table.setPrefHeight(50);
		// fill column headers
		for (VBox vBox : calHeaderView.createColumnHeaders()) {
			TableColumn column = new TableColumn();
			column.setPrefWidth(40);
			column.setGraphic(vBox);
			table.getColumns().add(column);
		}
		vbHeaders.getChildren().add(table);
		// label
		Label gridLabel = new Label("GridPane");
		vbHeaders.getChildren().add(gridLabel);
		// grid
		GridPane grid = new GridPane();
		ArrayList<VBox> list = calHeaderView.createColumnHeaders();
		for (int i = 0; i < list.size(); i++) {
			VBox label = list.get(i);
			grid.add(label, i, 0, 1, 1);
		}
		vbHeaders.getChildren().add(grid);
		return vbHeaders;
	}

	public static void main(String[] args) {

		launch(args);
	}

}
