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

		//Left
		TextArea inputtext=new TextArea();
		setupLeft(inputtext);
		
		//Center
		GridPane center=new GridPane();
		setupCenter(center);
		
		//Right
		Image algorithms=new Image("img/algorithms.png");
		ImageView image=new ImageView(algorithms);
		
		
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
		layout.setLeft(inputtext);
		layout.setCenter(center);
		layout.setRight(image);
		layout.setBottom(bottom);
		
		ScrollPane s = new ScrollPane(layout);
		
		//Scene
		scene=new Scene(s,1500,800);
		window.setScene(scene);
		window.show();
		
	}
	
	private void setupBottom(BorderPane bottom)
	{
	    // TODO Auto-generated method stub
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
		bottom.setTop(divisor);
		HBox left=bottomLeft();
		bottom.setLeft(left);
		bottom.setCenter(div);
		Text credits=new Text();
		credits.setText("\n\nWritten by: Andrea Berlingieri & Davide Balestra");
		bottom.setBottom(credits);
		VBox bottomright=new VBox();
		bottomright.setPadding(new Insets(50,110,0,0));
		Label lab=new Label("Merge Sort Sample\n");
		lab.setPadding(new Insets(0,20,20,20));
		Image sample=new Image("img/sample.png");
		ImageView sampleimg=new ImageView(sample);
		bottomright.getChildren().addAll(lab,sampleimg);
		bottom.setRight(bottomright);
	    
	}

	private void setupLeft(TextArea inputtext)
	{
		inputtext.setEditable(false);
		inputtext.setText("Data:\n");
		inputtext.setPrefSize(300, 300);
		inputtext.setMaxHeight(300);
	}

	private void setupCenter(GridPane center)
	{
		center.setPadding(new Insets(0,0,0,0));
		Text stat=new Text();
		stat.setText("Stats\n\nNumber of comparisons:      ");
		Text stat2=new Text();
		stat2.setText("\n\nNumber of array accesses:      ");
		Text stat3=new Text();
		stat3.setText("\n\nExecution time:      ");
		StackPane p1=new StackPane();
		p1.setPadding(new Insets(0,20,20,100));
		p1.getChildren().add(stat);
		StackPane p2=new StackPane();
		p2.setPadding(new Insets(0,20,20,20));
		p2.getChildren().add(stat2);
		StackPane p3=new StackPane();
		p3.setPadding(new Insets(0,20,20,20));
		p3.getChildren().add(stat3);
		GridPane.setConstraints(p1, 0, 0);
		GridPane.setConstraints(p2, 1, 0);
		GridPane.setConstraints(p3, 2, 0);
		center.getChildren().addAll(p1,p2,p3);
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
		Image backImg=new Image("img/back.png");
		Image stopImg=new Image("img/stop.png");
		Image pauseImg=new Image("img/pause.png");
		Image playImg=new Image("img/play.png");
		
		Button back=new Button("",new ImageView(backImg));
		Button next=new Button("",new ImageView(nextImg));
		Button stop=new Button("", new ImageView(stopImg));
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
		menu.getChildren().addAll(back,stop,play,pause,next);
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
		left.setPadding(new Insets(0,0,10,40));
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
			f.close();}catch (Exception e){AlertBox.display("Fatal Error", "File not found");}
		
		left.getChildren().addAll(code,merge);
		
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