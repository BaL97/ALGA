package Graphics;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {
	
	static boolean answer;	
	
	public static void display(String title, String message){
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label= new Label();
		label.setText(message);
		label.setPadding(new Insets(5,0,10,0));
		
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
