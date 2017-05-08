package Graphics;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class webPage {

	public static void display(String link){
		
		Stage window=new Stage();
		window.setTitle(link);
		
		WebView webpage=new WebView();	
		WebEngine engine=webpage.getEngine();
		engine.load(link);
		webpage.setPrefSize(1100, 1000);
		//layout
		VBox layout = new VBox();
		layout.getChildren().add(webpage);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	
	}
}
