package Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The class implements the visualization of a box with the project description
 * @author b_a_l
 *
 */
public class About {
	
	static boolean answer;	
	
	/**
	 * print the README file into a box
	 *  @author b_a_l
	 */
	public static void display(){
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("About");
		window.setMinWidth(250);
		Label label= new Label();
		Text instructions = new Text();
		readInstructions(instructions);
		//Caricemento del file documentazione sulla label
		label.setText("Documentation\nby Andrea Berlingieri & Davide Balestra");
		label.setPadding(new Insets(5,5,10,5));
		Button button=new Button("Ok");
		button.setOnAction(e->window.close());
		//layout
		BorderPane layout = new BorderPane();
		layout.setBottom(button);
		layout.setCenter(instructions);
		BorderPane.setAlignment(button,Pos.CENTER);
		BorderPane.setAlignment(layout.getCenter(), Pos.CENTER);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

	private static void readInstructions(Text instructions)
	{
	    instructions.setText("");
	   FileReader inst;
            try
            {
               inst = new FileReader("src/file/instructions");
               BufferedReader fileReader = new BufferedReader(inst);
               String line = fileReader.readLine();
               while(line != null)
               {
        	   instructions.setText(instructions.getText() + line + "\n");
        	   line = fileReader.readLine();
               }
               fileReader.close();
            } catch (FileNotFoundException e)
            {
                AlertBox.display("Init Error", "Couldn't load the instructions file");
            } catch (IOException e)
	    {
        	AlertBox.display("Reading Error", "Couldn't read the instructions file");
	    }
	}
}
