package Graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Settings {
	
	public static void display(Main m){
		Stage window = new Stage();
		window.setTitle("Settings");
		
		Label label= new Label();
		label.setText("Animation Settings");
		label.setPadding(new Insets(5,0,0,30));
		
		
		ChoiceBox<String> selectspeed=new ChoiceBox<String>();
		selectspeed.getItems().add("Select speed...");
		selectspeed.setValue("Select speed...");
		selectspeed.getItems().add("Slow");
		selectspeed.getItems().add("Medium");
		selectspeed.getItems().add("Fast");
		StackPane pane1=new StackPane();
		pane1.getChildren().add(selectspeed);
		pane1.setPadding(new Insets(0,10,10,10));
		ChoiceBox<String> selectmode=new ChoiceBox<String>();
		selectmode.getItems().add("Select mode...");
		selectmode.setValue("Select mode...");
		selectmode.getItems().add("Animation");
		selectmode.getItems().add("Single Step");
		StackPane pane2=new StackPane();
		pane2.getChildren().add(selectmode);
		pane2.setPadding(new Insets(10,10,10,10));
		VBox choices=new VBox();
		choices.setPadding(new Insets(20,20,0,20));
		choices.getChildren().addAll(pane1,pane2);
			
			
		Button yesButton=new Button("Ok");
		Button noButton=new Button("Cancel");
		noButton.autosize();
		yesButton.setOnAction(e->{
			if(!(selectspeed.getValue().equals("Select speed...")))
				m.setSpeed(selectspeed.getValue());
			if(!(selectmode.getValue().equals("Select mode..."))){
				if(selectmode.getValue().equals("Animation"))
					m.setMode(true);
				else
					m.setMode(false);
			}
			window.close();
		});
		noButton.setOnAction(e->window.close());
		//layout
		BorderPane layout = new BorderPane();
		GridPane choice=new GridPane();
		choice.setPadding(new Insets(10,10,10,10));
		choice.setVgap(10);
		choice.setHgap(5);
		choice.setConstraints(yesButton, 0, 1);
		choice.setConstraints(noButton, 1, 1);
		choice.getChildren().addAll(yesButton,noButton);
		choice.setAlignment(Pos.CENTER);
		label.setAlignment(Pos.CENTER);
		layout.setBottom(choice);
		layout.setTop(label);
		layout.setCenter(choices);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

}
