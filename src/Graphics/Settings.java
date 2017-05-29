package Graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Settings window, set the animation's speed, and the mode --> single step, motion
 * @author b_a_l
 *
 */
public class Settings {
	
	/**
	 * show the settings window
	 * @param m
	 */
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
		Text compdel = new Text("Comparison delay (in ms)");
		Text swapText = new Text("Rectangles movement delay (in ms)");
		TextField comp = new TextField();
		TextField swap = new TextField();
		swap.setMaxWidth(60);
		swap.setPrefWidth(40);
		swap.setText(Main.getSwapDelay() == null ? String.valueOf(0) : Main.getSwapDelay().toString());
		comp.setMaxWidth(60);
		comp.setPrefWidth(40);
		comp.setText(Main.getCompDelay() == null ? String.valueOf(0) : Main.getCompDelay().toString());
		VBox pane3 = new VBox();
		pane3.getChildren().addAll(compdel,comp,swapText,swap);
		pane3.setPadding(new Insets(20,10,10,10));
		pane3.setAlignment(Pos.CENTER);
		choices.setPadding(new Insets(20,20,0,20));
		choices.getChildren().addAll(pane1,pane2,pane3);
			
			
		Button yesButton=new Button("Ok");
		Button noButton=new Button("Cancel");
		noButton.autosize();
		yesButton.setOnAction(e->{
                    Boolean ok = false;
                    if(!(selectspeed.getValue().equals("Select speed...")))
                    {
                            m.setSpeed(selectspeed.getValue());
                            ok = true;
                    }
                    else
                    {
                	Boolean swCheck = false,compCheck = false;
                        try
                        {
                            Main.setSwapDelay(Double.parseDouble(swap.getText()));
                            swCheck = true;
                            Main.setCompDelay(Double.parseDouble(comp.getText()));
                            compCheck = true;
                            ok = true;
                        }
                        catch (NumberFormatException nfe)
                        {
                            if(swCheck == false && compCheck == false)
                                AlertBox.display("Error, invalid input" , "Input for Rectangles movement delay and for Comparison delay is not a number");
                            else if(swCheck == false)
                        	AlertBox.display("Error, invalid input" , "Input for Rectangles movement delay is not a number");
                            else
                                AlertBox.display("Error, invalid input" , "Input for Comparison delay is not a number");
                        }
                    if(!(selectmode.getValue().equals("Select mode..."))){
                            if(selectmode.getValue().equals("Animation"))
                                    m.setMode(true);
                            else
                                    m.setMode(false);
                    }
                    if(ok)
                        window.close();
		}
		});
		noButton.setOnAction(e->window.close());
		//layout
		BorderPane layout = new BorderPane();
		GridPane choice=new GridPane();
		choice.setPadding(new Insets(10,10,10,10));
		choice.setVgap(10);
		choice.setHgap(5);
		GridPane.setConstraints(yesButton, 0, 1);
		GridPane.setConstraints(noButton, 1, 1);
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
