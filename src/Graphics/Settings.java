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

	static Boolean mode = true; //true=animation, false=single-step
	static Double swapDelay;
	static Double compDelay;
	static Integer speed;
	static TextField comp,swap;
	
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
		
		
		VBox choices=new VBox();
		StackPane pane1=new StackPane();
		StackPane pane2=new StackPane();
		VBox pane3 = new VBox();
		comp = new TextField();
		swap = new TextField();
		ChoiceBox<String> selectspeed=new ChoiceBox<String>();
		ChoiceBox<String> selectmode=new ChoiceBox<String>();

		setUpSpeedSelector(selectspeed,pane1);
		setUpModeSelector(selectmode,pane2);
		setUpDelays(comp,swap,pane3);
		choices.setPadding(new Insets(20,20,0,20));
		choices.getChildren().addAll(pane1,pane2,pane3);
			
			
		Button yesButton=new Button("Ok");
		Button noButton=new Button("Cancel");
		
		setUpButtons(yesButton,noButton,selectspeed,selectmode,window);

		//layout
		BorderPane layout = new BorderPane();
		GridPane choice=new GridPane();
		setUpLayout(layout,choice,yesButton,noButton,label,choices);

		//window
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

	private static void setUpLayout(BorderPane layout, GridPane choice, Button yesButton, Button noButton,
		Label label, VBox choices)
	{
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
	    
	}

	private static void setUpButtons(Button yesButton, Button noButton, ChoiceBox<String> selectspeed, ChoiceBox<String> selectmode, Stage window)
	{
		noButton.autosize();
		yesButton.setOnAction(e->{
                    Boolean ok = false;
                    if(!(selectspeed.getValue().equals("Select speed...")))
                    {
                	String speed = selectspeed.getValue();
                	Integer choice;
                	if(speed == "Slow")
                	    choice = 0;
                	else if (speed == "Medium")
                	    choice = 1;
                	else if (speed == "Fast")
                	    choice = 2;
                	else
                	    choice = null;
                        setSpeed(choice);
                        ok = true;
                    }
                    else
                    {
                	Boolean swCheck = false,compCheck = false;
                        try
                        {
                            setSwapDelay(Double.parseDouble(swap.getText()));
                            swCheck = true;
                            setCompDelay(Double.parseDouble(comp.getText()));
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
					}
                    if(!(selectmode.getValue().equals("Select mode..."))){
                            if(selectmode.getValue().equals("Animation"))
                                    setMode(true);
                            else
                                    setMode(false);
                    }
                    else
                	setMode(true);
                    if(ok)
                        window.close();
		});
		noButton.setOnAction(e->window.close());
	    
	}

	private static void setUpDelays(TextField comp2, TextField swap2, VBox pane3)
	{
		Text compdel = new Text("Comparison delay (in ms)");
		Text swapText = new Text("Rectangles movement delay (in ms)");
		swap.setMaxWidth(60);
		swap.setPrefWidth(40);
		swap.setText(Settings.getSwapDelay() == null ? String.valueOf(0) : Settings.getSwapDelay().toString());
		comp.setMaxWidth(60);
		comp.setPrefWidth(40);
		comp.setText(Settings.getCompDelay() == null ? String.valueOf(0) : Settings.getCompDelay().toString());
		pane3.getChildren().addAll(compdel,comp,swapText,swap);
		pane3.setPadding(new Insets(20,10,10,10));
		pane3.setAlignment(Pos.CENTER);
	    
	}

	private static void setUpModeSelector(ChoiceBox<String> selectmode, StackPane pane2)
	{
            selectmode.getItems().add("Select mode...");
            Boolean mode = Settings.getMode();
            if(mode == true)
                selectmode.setValue("Animation");
            else if (mode == false)
                selectmode.setValue("Single Step");
            else
				selectmode.setValue("Select mode...");
            selectmode.getItems().add("Animation");
            selectmode.getItems().add("Single Step");
            pane2.getChildren().add(selectmode);
            pane2.setPadding(new Insets(10,10,10,10));
	}

	private static void setUpSpeedSelector(ChoiceBox<String> selectspeed, StackPane pane1)
	{
		selectspeed.getItems().add("Select speed...");

		Integer speed = Settings.getSpeed();
		if(speed == null)
                    selectspeed.setValue("Select speed...");
		else if(speed == 0)
                    selectspeed.setValue("Slow");
		else if(speed == 1)
                    selectspeed.setValue("Medium");
		else 
                    selectspeed.setValue("Fast");
		selectspeed.getItems().add("Slow");
		selectspeed.getItems().add("Medium");
		selectspeed.getItems().add("Fast");
		selectspeed.setOnAction(e->{
		   if(selectspeed.getValue() == "Slow" || selectspeed.getValue() == "Medium" || selectspeed.getValue() == "Fast") 
		   {
		       if(!comp.isDisabled())
			   comp.setDisable(true);
		       if(!swap.isDisabled())
			   swap.setDisable(true);
		   }
		   else
		   {
		       if(comp.isDisabled())
			   comp.setDisable(false);
		       if(swap.isDisabled())
			   swap.setDisable(false);
		   }
		});
                pane1.getChildren().add(selectspeed);
                pane1.setPadding(new Insets(0,10,10,10));

	    
	}

	/**
	 * set animation mode
	 * @param mode
	 * single step, motion
	 */
	public static void setMode(boolean mode){
		Settings.mode=mode;
	}
	
	
	/**
	 * set animation speed
	 * @param speed
	 * slow, medium, fast
	 */
	public static void setSpeed(Integer speed){
		Settings.speed=speed;
	}

	public static Integer getSpeed(){
	    return Settings.speed;
	}


	/**
	 * get delay of sectangles swap
	 * @return
	 */
	public static Double getSwapDelay()
	{
	    return swapDelay;
	}

	/**
	 * set delay of rectangle swap
	 * @param swapDelay
	 */
	public static void setSwapDelay(Double swapDelay)
	{
	    Settings.swapDelay = swapDelay;
	}

	/**
	 * get delay of merge animation
	 * @return
	 */
	public static Double getCompDelay()
	{
	    return compDelay;
	}

	/**
	 * set delay of merge animation
	 * @param compDelay
	 */
	public static void setCompDelay(Double compDelay)
	{
	    Settings.compDelay = compDelay;
	}
	
	public static Boolean getMode()
	{
	    return Settings.mode;
	}

}
