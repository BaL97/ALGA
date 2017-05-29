package Graphics;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

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
		//Caricemento del file documentazione sulla label
		label.setText("Documentation\nby Andrea Berlingieri & Davide Balestra");
		label.setPadding(new Insets(5,5,10,5));
		Button button=new Button("Ok");
		button.setOnAction(e->window.close());
		//layout
		BorderPane layout = new BorderPane();
		layout.setBottom(button);
		layout.setCenter(label);
		BorderPane.setAlignment(button,Pos.CENTER);
		BorderPane.setAlignment(layout.getCenter(), Pos.CENTER);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
