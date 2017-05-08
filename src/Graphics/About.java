//Documentation Class
package Graphics;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class About {
	
	static boolean answer;	
	
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
		layout.setAlignment(button,Pos.CENTER);
		layout.setAlignment(layout.getCenter(), Pos.CENTER);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
