package nl.pameijer.ictacademie.rasp.view.month;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * this view is responsible for total header
 *
 *
 * @author devadv
 *
 */
public class ColumnHeader {

	private DateModel dateModel;

	public ColumnHeader(DateModel dateModel) {
		this.dateModel = dateModel;

	}

	/**
	 * Method creates VBox view for one day
	 *
	 * @return VBox of one day
	 */

	private VBox createDayVBox(int year, int month, int day) {

		VBox vb = new VBox();
		Label dayLabel = new Label(day + "");
		dayLabel.setPrefWidth(60);
		dayLabel.setAlignment(Pos.CENTER);
		vb.getChildren().add(dayLabel);
		// label that contains the day of the week
		String dayName = dateModel.dayOfWeek(day);
		Label dayNameLabel = new Label(dayName);
		dayNameLabel.setPrefWidth(60);
		dayNameLabel.setAlignment(Pos.CENTER);
		vb.getChildren().add(dayNameLabel);
		// labels dayparts

		if (dayName.equals("za") || dayName.equals("zo")) {

			Label label = new Label();
			dayLabel.setPrefWidth(30);
			dayNameLabel.setPrefWidth(30);
			label.getStyleClass().add("weekend");
			label.setPrefWidth(30);
			vb.getChildren().add(label);

		} else {
			Label oLabel = new Label("o");
			oLabel.setPrefWidth(30);
			oLabel.setAlignment(Pos.CENTER);
			Label mLabel = new Label("m");
			mLabel.setPrefWidth(30);
			mLabel.setAlignment(Pos.CENTER);
			// HBox for two labels morning and afternoon */
			HBox hb = new HBox();
			hb.getChildren().add(oLabel);
			hb.getChildren().add(mLabel);
			// add HBox to VBox*/
			vb.getChildren().add(hb);
		}
		return vb;

	}

	/**
	 * Makes a list of VBox-days in a month
	 *
	 * @return ArrayList of VBoxes for column headers
	 */

	private ArrayList<VBox> vBoxDayList() {
		ArrayList<VBox> list = new ArrayList<VBox>();
		LocalDate date = LocalDate.of(dateModel.getYear(), dateModel.getMonth(), 1);
		for (int i = 0; i < date.lengthOfMonth(); i++) {
			list.add(createDayVBox(dateModel.getYear(), dateModel.getMonth(), i + 1));
		}

		return list;
	}

	public void createColumnHeaderDays(GridPane grid) {

		ArrayList<VBox> list = vBoxDayList();
		int col = 0;
		for (int i = 0; i < list.size(); i++) {
			VBox label = list.get(i);
			grid.add(label, col, 0, 2, 1);
			col += 2;
		}

	}

	public void createStudentColumnHeader(GridPane grid) {

		// grid
		// set first three columns
		grid.getColumnConstraints().add(new ColumnConstraints(40));
		grid.getColumnConstraints().add(new ColumnConstraints(100));
		grid.getColumnConstraints().add(new ColumnConstraints(150));

		Label idLabel = new Label("id");
		idLabel.setPrefHeight(54);
		createLabels(idLabel, grid);
		grid.add(idLabel, 0, 0);
		Label firstname = new Label("Voornaam");
		createLabels(firstname, grid);
		grid.add(firstname, 1, 0);
		Label lastname = new Label("Achternaam");
		createLabels(lastname, grid);
		grid.add(lastname, 2, 0);

	}

	/**
	 * add label to gridPane
	 *
	 * @param label
	 * @param grid
	 * @param row
	 * @param col
	 */
	public void createLabels(Label label, GridPane grid) {

		GridPane.setFillWidth(label, true);
		GridPane.setFillHeight(label, true);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setMaxHeight(Double.MAX_VALUE);
		label.setAlignment(Pos.CENTER);
		label.setPadding(new Insets(0, 0, 0, 5));

	}// end method createLabels

}
