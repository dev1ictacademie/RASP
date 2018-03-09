package application;

import java.awt.event.ActionListener;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GridView extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
	  Font font = Font.font("Tahoma", FontWeight.NORMAL, 18);

	  String[] firstNameStr = { "Piet", "Klaas", "Karel", "Paula"};
	  String[] lastNameStr = {"Derksen", "de Graaf", "Appel", "Aardbei"};

      GridPane grid = new GridPane();
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

      VBox vb = new VBox();
      vb.setAlignment(Pos.CENTER);

      Label dayNum = new Label("1 ");
      dayNum.setFont(font);
      vb.getChildren().add(dayNum);

      Label dayName = new Label("ma");
      dayName.setFont(font);
      vb.getChildren().add(dayName);

      HBox hb = new HBox();
      Label dayPartMorning = new Label(" o ");
      dayPartMorning.setFont(font);
      hb.getChildren().add(dayPartMorning);

      Label dayPartAfternoon = new Label(" m ");
      dayPartAfternoon.setFont(font);
      hb.getChildren().add(dayPartAfternoon);
      vb.getChildren().add(hb);
      GridPane.setColumnSpan(vb, 2);

      grid.add(vb, 3, 0);

      for(int i = 0; i < 14; i++){
    	  TextField txt = new TextField();
    	  txt.setPrefWidth(25.0);
    	  grid.add(txt, i + 3, 1);
      }

      
      
	AnchorPane root = new AnchorPane();
	root.getChildren().add(grid);
	primaryStage.setScene(new Scene(root, 1000, 600));
	primaryStage.setTitle("Maand overzicht");
	primaryStage.show();
  }



}
