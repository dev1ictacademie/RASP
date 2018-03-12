package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GridView extends Application {
	GridPane grid = new GridPane();
	DateModel dateModel = new DateModel(2018, 3);
	CalendarHeaderView viewCal = new CalendarHeaderView(dateModel);
	private ScrollPane scrollPane;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
	  setUpView();
	AnchorPane root = new AnchorPane();
	root.getChildren().add(scrollPane);
	primaryStage.setScene(new Scene(root, 1800, 600));
	primaryStage.setTitle("Maand overzicht");
	primaryStage.show();
  }// end method start


  public void setUpView() {
	  Font font = Font.font("Tahoma", FontWeight.NORMAL, 18);

	  String[] firstNameStr = { "Piet", "Klaas", "Karel", "Paula"};
	  String[] lastNameStr = {"Derksen", "de Graaf", "Appel", "Aardbei"};

      grid.setPadding(new Insets(25, 25, 25, 25));
      grid.getColumnConstraints().add(new ColumnConstraints(50));
      grid.getColumnConstraints().add(new ColumnConstraints(130));
      grid.getColumnConstraints().add(new ColumnConstraints(230));
      grid.setPadding(new Insets(25, 25, 25, 25));
      grid.setGridLinesVisible(true);

      Label id = new Label("ID: ");
      GridPane.setHalignment(id, HPos.CENTER);
      id.setFont(font);
      grid.add(id, 0, 0);

      Label firstName = new Label("Voornaam: ");
      firstName.setFont(font);
      grid.add(firstName, 1, 0);

      Label lastName = new Label("Achternaam: ");
      lastName.setFont(font);
      grid.add(lastName, 2, 0);

      for(int i = 0; i < 4; i++){
    	  Label lb = new Label();
    	  lb.setFont(font);
    	  lb.setText(i + 1 + "");
    	  grid.add(lb, 0, i + 1);
    	  GridPane.setHalignment(lb, HPos.CENTER);
      }

      for(int i = 0; i < firstNameStr.length; i++){
    	  Label lb = new Label();
    	  lb.setText(firstNameStr[i]);
    	  lb.setFont(font);
    	  grid.add(lb, 1, i + 1);
      }

      for(int i = 0; i < lastNameStr.length; i++){
    	  Label lb = new Label();
    	  lb.setText(lastNameStr[i]);
    	  lb.setFont(font);
    	  grid.add(lb, 2, i + 1);
      }


//      VBox vb = new VBox();
//      vb.setAlignment(Pos.CENTER);
//
//      Label dayNum = new Label("1 ");
//      dayNum.setFont(font);
//      vb.getChildren().add(dayNum);
//
//      Label dayName = new Label("ma");
//      dayName.setFont(font);
//      vb.getChildren().add(dayName);
//
//      HBox hb = new HBox();
//      Label dayPartMorning = new Label(" o ");
//      dayPartMorning.setFont(font);
//      hb.getChildren().add(dayPartMorning);
//
//      Label dayPartAfternoon = new Label(" m ");
//      dayPartAfternoon.setFont(font);
//      hb.getChildren().add(dayPartAfternoon);
//      vb.getChildren().add(hb);
//      GridPane.setColumnSpan(vb, 2);
//
//      grid.add(vb, 3, 0);

      int daysOfMonth = dateModel.getLengthOfMonth();

      ArrayList<VBox> list = viewCal.createColumnHeaders();
      int j = 0;
		for (int i = 0; i < list.size(); i++) {
			VBox label = list.get(i);
			grid.add(label, j + 3, 0, 2, 1);
			j += 2;
		}

      for(int i = 0; i < daysOfMonth * 2; i++){
    	  TextField txt = new TextField();
    	  txt.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!(newValue.equals("x") || newValue.equals("z") || newValue.equals("v") || newValue.equals("a"))){
					txt.setText("");
				}
				else{
					txt.setEditable(false);
				}

			}
		});

    	txt.setPrefWidth(25.0);
    	grid.add(txt, i + 3, 1);

      }// end for loop
      
      scrollPane = new ScrollPane();
      scrollPane.setContent(grid);
      scrollPane.setPrefSize(1800.0, 600.0);

  }// end method setUpView

}// end class gridView
