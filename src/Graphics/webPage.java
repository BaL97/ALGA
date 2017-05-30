package Graphics;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * web page of git repository of the project
 * @author b_a_l
 *
 */
public class webPage {

	/**
	 * show the git repository
	 * @param link
	 * @param title
	 */
	public static void display(String link, String title){
		
		Stage window=new Stage();
		window.setTitle(title);
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
