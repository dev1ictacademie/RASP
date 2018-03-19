package nl.pameijer.ictacademie.rasp.view.month;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * this view is responsible for generating VBox nodes for the month or week
 *
 *
 * @author devadv
 *
 */
public class CalendarHeaderView {

	private DateModel dateModel;

	public CalendarHeaderView(DateModel dateModel) {
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
		dayLabel.setPrefWidth(50);
		dayLabel.setAlignment(Pos.CENTER);
		vb.getChildren().add(dayLabel);
		// label that contains the day of the week
		String dayName = dateModel.dayOfWeek(day);
		Label dayNameLabel = new Label(dayName);
		dayNameLabel.setPrefWidth(50);
		dayNameLabel.setAlignment(Pos.CENTER);
		vb.getChildren().add(dayNameLabel);
		// labels dayparts

		if (dayName.equals("za") || dayName.equals("zo")) {

			Label label = new Label();
			dayLabel.setPrefWidth(25);
			dayNameLabel.setPrefWidth(25);
			label.getStyleClass().add("weekend");
			label.setPrefWidth(25);
			vb.getChildren().add(label);

		} else {
			Label oLabel = new Label("o");
			oLabel.setPrefWidth(25);
			oLabel.setAlignment(Pos.CENTER);
			Label mLabel = new Label("m");
			mLabel.setPrefWidth(25);
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

	public ArrayList<VBox> createColumnHeaders() {
		ArrayList<VBox> list = new ArrayList<VBox>();
		LocalDate date = LocalDate.of(dateModel.getYear(), dateModel.getMonth(), 1);
		for (int i = 0; i < date.lengthOfMonth(); i++) {
			list.add(createDayVBox(dateModel.getYear(), dateModel.getMonth(), i + 1));
		}

		return list;

	}

	public ArrayList<VBox> createWeekColumnHeaders() {
		ArrayList<VBox> list = new ArrayList<VBox>();
		// TODO howto get weeks workings days except weekend days

		return list;
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

		return monthBox;

	}

	/**
	 *
	 * @return ComboBox years
	 */
	public ComboBox<Integer> getYearComboBox() {
		ObservableList<Integer> years = FXCollections.observableArrayList();
		ComboBox<Integer> yearBox = null;
		for (int i = 0; i < 25; i++) {
			years.add(2017 + i);
			yearBox = new ComboBox<Integer>(years);
			yearBox.setValue(dateModel.getYear());

		}
		return yearBox;

	}

}
