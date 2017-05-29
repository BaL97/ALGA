package Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import visualizer.Visualizer;

public class Main extends Application{
	Stage window;
	Scene scene;
	ArrayList<Comparable> input=new ArrayList<Comparable>();
	static ArrayList<Double> doubleInput=new ArrayList<Double>();
	static ArrayList<Integer> integerInput=new ArrayList<Integer>();
	GridPane animation=new GridPane();
	FileReader f;
	BufferedReader b;
	static Boolean mode=true; //true=animation, false=single-step
	static Double swapDelay;
	static Double compDelay;
	static String speed="medium"; //slow, medium, fast
	public static Label comps = new Label("0");
	public static Label accs = new Label("0");
	public static String link="https://github.com/andrea-berling/ALGA";
	
	public static void main (String args[]){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//set the window as primary stage
		window=primaryStage;
		window.setTitle("ALGA - ALGorithm Animation");
		window.setOnCloseRequest(e->{
			e.consume();
			closeProgram();	
		});

		TextArea inputtext=new TextArea();
		setupLeft(inputtext);
		
		//Center
		HBox center=new HBox();
		setupCenter(center,inputtext);
		
		
		//Bottom
		BorderPane bottom=new BorderPane();
		setupBottom(bottom);
		
		//Top
		BorderPane topMenu=new BorderPane();
		//MenuBar
		MenuBar menu=this.setMenuBar(inputtext);
		//menu.setOnDragExited(value);
		//FlowBar
		HBox flowbar=this.setFlowControl();
		//set topMenu
		topMenu.setLeft(menu);
		topMenu.setCenter(flowbar);
		
		
		//Window's layout
		BorderPane layout = new BorderPane();
		layout.setTop(topMenu);
		layout.setCenter(center);
		layout.setBottom(bottom);
		
		ScrollPane s = new ScrollPane(layout);
		
		//Scene
		scene=new Scene(s,860,800);
		window.setScene(scene);
		window.show();
		
	}
	
	private void setupBottom(BorderPane bottom)
	{
	    // TODO Auto-generated method stub
		Rectangle divisor=new Rectangle();
		divisor.setWidth(855);
		divisor.setHeight(2);
		divisor.setFill(Color.DARKGRAY);
		
		bottom.setTop(divisor);
		HBox left=bottomLeft();
		bottom.setLeft(left);
		Text credits=new Text();
		credits.setText("\n\nWritten by: Andrea Berlingieri & Davide Balestra");
		bottom.setBottom(credits);
		VBox bottomright=new VBox();
		bottomright.setPadding(new Insets(50,110,0,0));
	}

	private void setupLeft(TextArea inputtext)
	{
		inputtext.setEditable(false);
		inputtext.setText("Data:\n");
		inputtext.setPrefSize(300, 300);
		inputtext.setMaxHeight(300);
		inputtext.setPadding(new Insets(10,10,10,10));
	}

	private void setupCenter(HBox center, TextArea inputtext)
	{
		Image sample=new Image("img/mergesort.png");
		ImageView img=new ImageView(sample);
		center.getChildren().addAll(inputtext,img);
		center.setPadding(new Insets(10,10,10,10));
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
                        inputtext.setText("Data:\n\n");
                        input=inputSelector.display();
                        for(int i=0;i<input.size();i++){
                                inputtext.appendText(input.get(i)+"\n");				
                        }
                        inputtext.appendText("\n\nInput Dimension: "+input.size());
                });
                settings.setOnAction(e->Settings.display(this));
                exit.setOnAction(e->closeProgram());
                
                //Help Menù
                Menu help=new Menu("Help");
                MenuItem about = new MenuItem("About...");
                help.getItems().add(about); //load input
                MenuItem git=new MenuItem("Git...");
                help.getItems().add(git);
                about.setOnAction(e->About.display());
                git.setOnAction(e->webPage.display(link, "GitHub"));
                 
                //main Menù Bar
                MenuBar menu=new MenuBar();	
                menu.getMenus().addAll(file,help);
		return menu;
	}
		
	private void closeProgram(){
		boolean x=ConfirmBox.display("Exit", "Are you sure?");
		if(x)
			System.exit(0);
	}
	
	public HBox setFlowControl(){
		
		Image nextImg=new Image("img/next.png");		
		Image pauseImg=new Image("img/pause.png");
		Image playImg=new Image("img/play.png");
		
		Button next=new Button("",new ImageView(nextImg));
		Button play=new Button("",new ImageView(playImg));
		Button pause=new Button("", new ImageView(pauseImg));
		
		play.setOnAction(e->{
			if(input.size()!=0){
			{Stage animation=new Stage();
			if(inputSelector.doubleflag){
				fillDouble(input);
				Visualizer.handleDoubleArray(doubleInput);}
			else{
				fillInteger(input);
				Visualizer.handleIntArray(integerInput);}
			if (Main.getCompDelay() == null && Main.getSwapDelay() == null)
			    AlertBox.display("Error, delay not set", "Please set the delay for the comparison animation and for the rectangle movemnt animation in the settings menu");
			else if(Main.getCompDelay() == null)
			    AlertBox.display("Error, delay not set", "Please set the delay for the comparison animation in the settings menu");
			else if(Main.getSwapDelay() == null)
			    AlertBox.display("Error, delay not set", "Please set the delay for the rectangle movement animation in the settings menu");
			else
                            Visualizer.prepareStage(animation);
			}
			
		//if(input.get(0).getClass().equals("Double"))
			//	Visualizer.handleDoubleArray(input);
			//else
				//Visualizer.handleIntArray(input);
			//Visualizer.prepareStage(animation);
			}
			else
				AlertBox.display("Input Error", "Empty Input");
			
		});
		
		next.setOnAction(e->{
		    Visualizer.play();
		});
		
		/*play.setOnAction(e->{ //Animazione per massimo 10 elementi
			try{mergesort.start(input,array,animation);}catch (Exception q){AlertBox.display("Error", q.getMessage());}
			});*/
		
		HBox menu=new HBox();
		menu.getChildren().addAll(play,pause,next);
		return menu;
	}

	private void fillInteger(ArrayList<Comparable> input2) {
		doubleInput.clear();
		integerInput.clear();
		for(Comparable v:input2){
			integerInput.add(Integer.parseInt(v.toString()));}
	}

	private void fillDouble(ArrayList<Comparable> input2) {
		doubleInput.clear();
		integerInput.clear();
		for(Comparable v:input2){
			doubleInput.add(Double.parseDouble(v.toString()));
		}
	}

	private HBox bottomLeft(){
		HBox left=new HBox();
		Text code=new Text();
		StackPane codepane=new StackPane();
		StackPane mergepane=new StackPane();
		try{
			f=new FileReader("src/file/mergesort.txt");
			b=new BufferedReader(f);
			String text="";
			String line = b.readLine();
			while(line!=null){
				text=text+"\n"+line;
				line = b.readLine();}
			code.setText(text);
			f.close();}catch (Exception e){AlertBox.display("Fatal Error", "File not found");}
		left.setPadding(new Insets(0,0,0,30));
		codepane.getChildren().add(code);
		codepane.setPadding(new Insets(0,30,0,0));
		Text merge=new Text();
		try{
			f=new FileReader("src/file/merge.txt");
			b=new BufferedReader(f);
			String text="";
			String line = b.readLine();
			while(line!=null){
				text=text+"\n"+line;
				line = b.readLine();}
			merge.setText(text);
			mergepane.getChildren().add(merge);
			mergepane.setPadding(new Insets(20,0,0,30));
			f.close();}catch (Exception e){AlertBox.display("Fatal Error", "File not found");}
		
		left.getChildren().addAll(codepane,mergepane);
		
		return left;
	}
	
	public void setMode(boolean mode){
		this.mode=mode;
	}
	
	public void setSpeed(String speed){
		Main.speed=speed;
	}

	public static Double getSwapDelay()
	{
	    return swapDelay;
	}

	public static void setSwapDelay(Double swapDelay)
	{
	    Main.swapDelay = swapDelay;
	}

	public static Double getCompDelay()
	{
	    return compDelay;
	}

	public static void setCompDelay(Double compDelay)
	{
	    Main.compDelay = compDelay;
	}

	public static void updateComps()
	{
	   Integer c = Integer.parseInt(comps.getText());
	   c++;
	   comps.setText(c.toString()); 
	}

	public static void updateAccesses()
	{
	   Integer a = Integer.parseInt(accs.getText());
	   a++;
	   accs.setText(a.toString()); 
	    
	}

	public static void clearStats()
	{
	   comps.setText("0"); 
	   accs.setText("0"); 
	}

	public static Boolean getMode()
	{
	    return mode;
	}

}