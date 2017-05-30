package Graphics;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Box to show messages during the execution of the program used for error messages
 * @author b_a_l
 *
 */
public class AlertBox {
	
	static boolean answer;	
	
	/**
	 * show the alert box
	 * @param title
	 * set the title of the window
	 * @param message
	 * set the message to show
	 * @author b_a_l
	 */
	public static void display(String title, String message){
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label= new Label();
		label.setText(message);
		label.setPadding(new Insets(5,5,10,5));
		
		Button button=new Button("Ok");
		
		button.setOnAction(e->window.close());
		
		//layout
		VBox layout = new VBox();
		layout.getChildren().addAll(label,button);
		layout.setAlignment(Pos.CENTER);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	
	}

}
