package Graphics;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {
	
	static boolean answer;	
	
	public static boolean display(String title, String message){
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label= new Label();
		label.setText(message);
		label.setPadding(new Insets(5,0,0,0));
		
		Button yesButton=new Button("Yes");
		Button noButton=new Button("NO");
		noButton.autosize();
		//noButton.
		
		//set the answer variable
		yesButton.setOnAction(e->{
			answer=true;
			window.close();
		});
		noButton.setOnAction(e->{
			answer=false;
			window.close();
		});
		
		//layout
		label.setPadding(new Insets(5,5,0,80));
		BorderPane layout = new BorderPane();
		GridPane choice=new GridPane();
		choice.setPadding(new Insets(10,10,10,10));
		choice.setVgap(10);
		choice.setHgap(5);
		GridPane.setConstraints(yesButton, 0, 1);
		GridPane.setConstraints(noButton, 1, 1);
		choice.getChildren().addAll(yesButton,noButton);
		choice.setAlignment(Pos.CENTER);
		layout.setCenter(choice);
		layout.setTop(label);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}

}
