package animation;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import javafx.geometry.*;

public class InputItem {
	
Rectangle r=new Rectangle(); 
Text text = new Text();
StackPane stackPane = new StackPane();

public InputItem(String s, Integer i){
	setText(s);
	r.setHeight(40);
	r.setWidth(s.length()*10);
	r.setFill(Color.valueOf("#FF7F50"));
	stackPane.setMaxSize(r.getWidth(), r.getHeight());
	stackPane.setId("");
	stackPane.getChildren().addAll(r, text);
	stackPane.setAlignment(text,Pos.TOP_CENTER);
	stackPane.setAlignment(Pos.TOP_CENTER);
	stackPane.setTranslateX(i);
	stackPane.setPadding(new Insets(10,10,10,10));
}

private void setText(String s) {
	this.text=new Text(s);
}

public String getText(){
	return text.getText();
}
public StackPane getPane(){
	return stackPane;
}
}

