package Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.application.Application;
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

/**
 * Primary Stage, Graphical Interface for the application
 * @author b_a_l
 *
 */
public class Main extends Application{
	Stage window;
	Scene scene;
	ArrayList<Number> input=new ArrayList<Number>();
	static ArrayList<Double> doubleInput=new ArrayList<Double>();
	static ArrayList<Integer> integerInput=new ArrayList<Integer>();
	GridPane animation=new GridPane();
	FileReader f;
	BufferedReader b;
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
		scene=new Scene(s,880,850);
		window.setScene(scene);
		window.show();
		
	}
	
	/**
	 * Sets up the layout for the bottom panel passed as a parameter
	 * @param bottom The BorderPane to set up
	 */
	private void setupBottom(BorderPane bottom)
	{
		Rectangle divisor=new Rectangle();
		divisor.setWidth(850);
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

	/**
	 * Sets up the layout for the left part of the main window
	 * @param inputtext The TextArea to set up
	 */
	private void setupLeft(TextArea inputtext)
	{
		inputtext.setEditable(false);
		inputtext.setText("Data:\n");
		inputtext.setPrefSize(300, 300);
		inputtext.setMaxHeight(300);
		inputtext.setPadding(new Insets(10,10,10,10));
	}

	/**
	 * Sets up the central part of the main window
	 * @param center An HBox to be set up for the central part of the main window
	 * @param inputtext A TextArea to be added to the central part
	 */
	private void setupCenter(HBox center, TextArea inputtext)
	{
		Image sample=new Image("img/mergesort.png");
		ImageView img=new ImageView(sample);
		center.getChildren().addAll(inputtext,img);
		center.setPadding(new Insets(10,10,10,10));
	}

	/**
	 * Sets up the layout for the MenuBar
	 * @param inputtext A text area containing the input for the application
	 * @return A fully set up MenuBar
	 */
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
                MenuItem git=new MenuItem("Github...");
                help.getItems().add(git);
                about.setOnAction(e->About.display());
                git.setOnAction(e->webPage.display(link, "GitHub"));
                 
                //main Menù Bar
                MenuBar menu=new MenuBar();	
                menu.getMenus().addAll(file,help);
		return menu;
	}
	
	/**
	 * Stops the application and closes all the windows related to it
	 */
	private void closeProgram(){
		boolean x=ConfirmBox.display("Exit", "Are you sure?");
		if(x)
			System.exit(0);
	}
	
	/**
	 * Sets up the flow control bar
	 * @return The fully set up flow control bar
	 */
	public HBox setFlowControl(){
		
		Image nextImg=new Image("img/next.png");		
		Image stopImg=new Image("img/stop.png");
		Image pauseImg=new Image("img/pause.png");
		Image playImg=new Image("img/play.png");
		
		Button next=new Button("",new ImageView(nextImg));
		Button stop=new Button("", new ImageView(stopImg));
		Button play=new Button("",new ImageView(playImg));
		Button pause=new Button("", new ImageView(pauseImg));
		
		play.setOnAction(e->{
                            if(!Visualizer.isPlaying())
                            {
                                Stage animation=new Stage();
                                createNewAnimation(animation);
                            }
                            else
                            {
                                Settings.setMode(true);
								Visualizer.play();
                            }
                });
		
		
		next.setOnAction(e->{
		    if(!Visualizer.isPlaying())
		    {
			Stage animation = new Stage();
			Settings.setMode(false);
			createNewAnimation(animation);
		    }
		    else
		    {
			Settings.setMode(false);
                        Visualizer.play();
		    }
		});
		
		pause.setOnAction(e->{
		    Settings.setMode(false);
		    Visualizer.pause();
		});

		stop.setOnAction(e->{
		    Visualizer.stopAnimation();
		});
		
		HBox menu=new HBox();
		menu.getChildren().addAll(stop,play,pause,next);
		return menu;
	}

	/**
	 * Given an empty, but initialized Stage (not empty), it creates a new window 
	 * for the sort animation. If the input is void or the speed settings aren't set,
	 * no animation is set up and an error message is printed
	 * @param animation The stage to use for the animation
	 */
	private void createNewAnimation(Stage animation)
	{
            if(input.size()!=0)
            {
                if(inputSelector.doubleflag)
                {
                    fillDouble(input);
                    Visualizer.handleDoubleArray(doubleInput);
                }
                else
                {
                    fillInteger(input);
                    Visualizer.handleIntArray(integerInput);
                }

                if(Settings.getSpeed() == null)
                {
                    if (Settings.getCompDelay() == null && Settings.getSwapDelay() == null)
                        AlertBox.display("Error, delay not set", "Please set the delay for the comparison animation and for the rectangle movemnt animation in the settings menu");
                    else if(Settings.getCompDelay() == null)
                        AlertBox.display("Error, delay not set", "Please set the delay for the comparison animation in the settings menu");
                    else if(Settings.getSwapDelay() == null)
                        AlertBox.display("Error, delay not set", "Please set the delay for the rectangle movement animation in the settings menu");
                    else
                        Visualizer.prepareStage(animation);
                }
                else
                    Visualizer.prepareStage(animation);
            }
            else
                AlertBox.display("Input Error", "Empty Input");
       }	

	/**
	 * Given an ArrayList of Numbers, it converts them to Integer and puts them
	 * in an ArrayList used to pass the input to the animation
	 * @param input2 The ArrayList of Numbers containing the input
	 */
	private void fillInteger(ArrayList<Number> input2) {
		doubleInput.clear();
		integerInput.clear();
		for(Number v:input2){
			integerInput.add(Integer.parseInt(v.toString()));}
	}

	/**
	 * Given an ArrayList of Numbers, it converts them to Double and puts them
	 * in an ArrayList used to pass the input to the animation
	 * @param input2 The ArrayList of Numbers containing the input
	 */
	private void fillDouble(ArrayList<Number> input2) {
		doubleInput.clear();
		integerInput.clear();
		for(Number v:input2){
			doubleInput.add(Double.parseDouble(v.toString()));
		}
	}

	/**
	 * Sets up the bottom left part of the main window
	 * @return An HBox containing a description of the algorithm 
	 * and the pseudocode for the algorithm
	 */
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
	
	/**
	 * Updates the comparisons counter adding 1 to it
	 */
	public static void updateComps()
	{
	   Integer c = Integer.parseInt(comps.getText());
	   c++;
	   comps.setText(c.toString()); 
	}

	/**
	 * Updates the array accesses counter adding 1 to it
	 */
	public static void updateAccesses()
	{
	   Integer a = Integer.parseInt(accs.getText());
	   a++;
	   accs.setText(a.toString()); 
	    
	}

	/**
	 * Clears the stats, setting them to 0
	 */
	public static void clearStats()
	{
	   comps.setText("0"); 
	   accs.setText("0"); 
	}
}
