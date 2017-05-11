/*Da Fare:
 * scrivere una funzione mergesort che funzioni con l'input della grafica
 * creare l'animazione per la funzione mergesort
 * ogni volta che divide in due l'array colora le due porzioni con due colori diversi e le copia sotto separatamente
 * quando arriva ad i singoli elementi colora gli elementi dello stesso colore mano a mano che vengono confrontati tra loro
 * cambiare da label a textfield
 */
package Graphics;
import animation.*;
import mergesort.*;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application{
	Stage window;
	Scene scene;
	Button play; //play the algorithm (select the speed in a menù)
	Button back; //back one step
	Button next; //next step
	Button pause; //pause the algorithm
	ArrayList<Comparable> input=new ArrayList<Comparable>();
	ArrayList<StackPane> array=new ArrayList<StackPane>();
	GridPane animation=new GridPane();
	
	public static void main (String args[]){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FileReader f;
		BufferedReader b;
		// TODO Auto-generated method stub
		
		//set the window as primary stage
		window=primaryStage;
		window.setTitle("ALGA - ALGorithm Animation");
		window.setOnCloseRequest(e->{
			e.consume();
			closeProgram();	
		});

		TextArea inputtext=new TextArea();
		inputtext.setEditable(false);
		inputtext.setText("Data:\n");
		inputtext.setPrefSize(300, 300);
		inputtext.setMaxHeight(300);
		
		//Bottom
		HBox left=new HBox();
		Rectangle divisor=new Rectangle();
		divisor.setWidth(1500);
		divisor.setHeight(2);
		divisor.setFill(Color.DARKGRAY);
		
		Rectangle divisor2=new Rectangle();
		divisor2.setWidth(2);
		divisor2.setHeight(400);
		divisor2.setFill(Color.DARKGREY);
		HBox div=new HBox();
		div.getChildren().add(divisor2);
		div.setPadding(new Insets(50,0,0,50));
		
		
		HBox right=new HBox();
		BorderPane bottom=new BorderPane();
		bottom.setTop(divisor);
		Text code=new Text();
		try{
			f=new FileReader("file/mergesort.txt");
			b=new BufferedReader(f);
			String text="";
			String line = b.readLine();
			while(line!=null){
				text=text+"\n"+line;
				line = b.readLine();}
			code.setText(text);
			f.close();}catch (Exception e){AlertBox.display("Fatal Error", "File not found");}
		left.setPadding(new Insets(0,0,10,40));
		Text merge=new Text();
		try{
			f=new FileReader("file/merge.txt");
			b=new BufferedReader(f);
			String text="";
			String line = b.readLine();
			while(line!=null){
				text=text+"\n"+line;
				line = b.readLine();}
			merge.setText(text);
			f.close();}catch (Exception e){AlertBox.display("Fatal Error", "File not found");}
		
		left.getChildren().addAll(code,merge);
		bottom.setLeft(left);
		bottom.setCenter(div);
		
		Text credits=new Text();
		credits.setText("\n\nWritten by: Andrea Berlingier & Davide Balestra");
		bottom.setBottom(credits);
		
		//Top Menu
		BorderPane topMenu=new BorderPane();
		//MenuBar
		MenuBar menu=this.setMenuBar(inputtext);
		//menu.setOnDragExited(value);
		
		//FlowBar
		HBox flowbar=this.setFlowControl();
		
		//Window's layout
		BorderPane layout = new BorderPane();
		
		//set topMenu
		topMenu.setLeft(menu);
		topMenu.setCenter(flowbar);
		
		layout.setTop(topMenu);
		layout.setLeft(inputtext);
		layout.setRight(animation);
		layout.setBottom(bottom);
		
		scene=new Scene(layout,1500,800);
		window.setScene(scene);
		window.show();
		
	}
	
	public MenuBar setMenuBar(TextArea inputtext){
			//File Menù
				Menu file=new Menu("File");
				MenuItem load = new MenuItem("Load Input...");
				file.getItems().add(load); //load input
				MenuItem settings=new MenuItem("Settings...");
				file.getItems().add(settings);
				MenuItem exit=new MenuItem("Exit");
				file.getItems().add(exit); //exit the program
				load.setOnAction(e->{
					inputtext.clear();
					inputtext.setText("Data:\n");
					input=inputSelector.display();
					for(int i=0;i<input.size();i++){
						inputtext.appendText(input.get(i)+"\n");				
					}
					inputtext.appendText("\n\nInput Dimension: "+input.size());
				});
				settings.setOnAction(e->settingsWindow());
				exit.setOnAction(e->closeProgram());
				
				//Help Menù
				Menu help=new Menu("Help");
				MenuItem about = new MenuItem("About...");
				help.getItems().add(about); //load input
				MenuItem git=new MenuItem("Git...");
				help.getItems().add(git);
				about.setOnAction(e->About.display());
				
				 git.setOnAction(e->webPage.display("https://github.com/BaL97")	);
				 
				//main Menù Bar
				MenuBar menu=new MenuBar();	
				menu.getMenus().addAll(file,help);
		return menu;
	}
	
	private Object settingsWindow() {
		// TODO Auto-generated method stub
		return null;
	}
		
	private void closeProgram(){
		boolean x=ConfirmBox.display("Exit", "Are you sure?");
		if(x)
			window.close();
	}
	
	public HBox setFlowControl(){
		
		Image nextImg=new Image("img/next.png");		
		Image backImg=new Image("img/back.png");
		Image stopImg=new Image("img/stop.png");
		Image pauseImg=new Image("img/pause.png");
		Image playImg=new Image("img/play.png");
		
		Button back=new Button("",new ImageView(backImg));
		Button next=new Button("",new ImageView(nextImg));
		Button stop=new Button("", new ImageView(stopImg));
		Button play=new Button("",new ImageView(playImg));
		Button pause=new Button("", new ImageView(pauseImg));
		
		play.setOnAction(e->{ //Animazione per massimo 10 elementi
			try{mergesort.start(input,array,animation);}catch (Exception q){AlertBox.display("Error", q.getMessage());}
			});
		
		HBox menu=new HBox();
		menu.getChildren().addAll(back,stop,play,pause,next);
		return menu;
	}
}