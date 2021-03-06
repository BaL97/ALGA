package visualizer;

import java.util.ArrayList;
import java.util.HashMap;

import Graphics.Main;
import Graphics.Settings;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import mergesort.Merge;

/**
 * Contains all the methods that handle the Rectangle animation
 * @author Andrea
 *
 */
public class Visualizer extends Application
{
    private static SequentialTransition sequence; 
    private static Double doubleArray[];
    private static Integer integerArray[];
    private static Boolean doubleFlag;
    private static Duration swapDelay;
    private static Duration compDelay;
    private static boolean playFlag;
    private static Rectangle[] rectangles;
    private static HashMap<Rectangle,Double> copy;

    @Override
    public void start(Stage primaryStage) 
    {
	prepareStage(primaryStage);
    }
    
    /**
     * Prepares the stage and handles the animation part
     * @param stage The stage to be handled
     */
    public static void prepareStage(Stage stage)
    {
	final Integer LENGTH = doubleFlag ? doubleArray.length : integerArray.length;
        final int WIDTH = 1000, HEIGHT = 750;
        Group root = new Group();
        
        rectangles = new Rectangle[LENGTH];
        copy = new HashMap<Rectangle,Double>();
        Scene s;
        sequence = new SequentialTransition();
        
	HBox stats = new HBox();
	setUpStats(stats);
	
        // The rectangles array is filled using the data passed
        fillR(rectangles, HEIGHT, WIDTH);
        makeACopy();

	// The rectangles are added to root group
	root.getChildren().addAll(rectangles);

	s = new Scene(root, WIDTH, HEIGHT);
	

	root.getChildren().add(stats);
	s.setFill(Color.BLACK);

	stage.setTitle("Mergesort");
	stage.setScene(s);
	stage.show();
	
	setDelays(LENGTH);
        Merge.mergesort(rectangles);
        sequence.play();
        Visualizer.playFlag = true;
        stage.setOnCloseRequest(e->{
            sequence.getChildren().clear();
            Main.clearStats(true);
            Visualizer.playFlag = false;
        });
    }
    
    /**
     * Makes a copy of the rectangles array, in order to reset the scene if needed
     */
    private static void makeACopy()
    {
	for(int i = 0; i < rectangles.length; i++)
	    copy.put(rectangles[i], rectangles[i].getTranslateX());
    }

    /**
     * Given an empty HBox, it generates the stats displayer and puts in the supplied
     * HBox
     * @param stats An empty, but initialized, HBox (not null)
     */
    private static void setUpStats(HBox stats)
    {
	stats.setLayoutX(0);
	stats.setLayoutY(0);
	stats.setSpacing(20);
	
	HBox comps = new HBox();
	Text comparisons = new Text("Comparisons: ");
	comparisons.setFill(Color.WHITE);
	Text C = new Text();
	C.setFill(Color.WHITE);
	C.textProperty().bind(Main.comps.textProperty());
	comps.getChildren().addAll(comparisons,C);
	
	HBox accs = new HBox();
	Text accesses = new Text("Array accesses: ");
	accesses.setFill(Color.WHITE);
	Text A = new Text();
	A.setFill(Color.WHITE);
	A.textProperty().bind(Main.accs.textProperty());
	accs.getChildren().addAll(accesses,A);
	
	stats.getChildren().addAll(comps,accs);
	
    }

    /**
     * Sets the static delay variables to the corresponding one defined in the Settings
     * class
     * @param lENGTH The size of the array
     */
    private static void setDelays(Integer LENGTH)
    {
	if(Settings.getSpeed() == null)
	{
            swapDelay = Duration.millis(Settings.getSwapDelay());
            compDelay = Duration.millis(Settings.getCompDelay());
	}
        else
        {
            Integer c = Settings.getSpeed();
            if(LENGTH <= 50)
            {
        	switch(c)
        	{
        	case 0:
        	    swapDelay = Duration.millis(500);
        	    Settings.setSwapDelay(500.0);
        	    compDelay = Duration.millis(500);
        	    Settings.setCompDelay(500.0);
        	    break;
        	case 1:
        	    swapDelay = Duration.millis(75);
        	    compDelay = Duration.millis(200);
        	    Settings.setSwapDelay(75.0);
        	    Settings.setCompDelay(200.0);
        	    break;
        	case 2:
        	    swapDelay = Duration.millis(5);
        	    compDelay = Duration.millis(25);
        	    Settings.setSwapDelay(5.0);
        	    Settings.setCompDelay(25.0);
        	    break;

        	}
            }
            else if (LENGTH <= 200)
            {
        	switch(c)
        	{
        	case 0:
        	    swapDelay = Duration.millis(70);
        	    compDelay = Duration.millis(250);
        	    Settings.setSwapDelay(70.0);
        	    Settings.setCompDelay(250.0);
        	    break;
        	case 1:
        	    swapDelay = Duration.millis(35);
        	    compDelay = Duration.millis(100);
        	    Settings.setSwapDelay(35.0);
        	    Settings.setCompDelay(100.0);
        	    break;
        	case 2:
        	    swapDelay = Duration.millis(1);
        	    compDelay = Duration.millis(10);
        	    Settings.setSwapDelay(1.0);
        	    Settings.setCompDelay(10.0);
        	    break;

        	}
            }
            else
            {
        	switch(c)
        	{
        	case 0:
        	    swapDelay = Duration.millis(35);
        	    compDelay = Duration.millis(70);
        	    Settings.setSwapDelay(35.0);
        	    Settings.setCompDelay(70.0);
        	    break;
        	case 1:
        	    swapDelay = Duration.millis(10);
        	    compDelay = Duration.millis(10);
        	    Settings.setSwapDelay(10.0);
        	    Settings.setCompDelay(10.0);
        	    break;
        	case 2:
        	    swapDelay = Duration.millis(1);
        	    compDelay = Duration.millis(1);
        	    Settings.setSwapDelay(1.0);
        	    Settings.setCompDelay(1.0);
        	    break;

        	}
        	
            }
        }
        	
    }

    /**
     * If the input choice was for Double, this method copied the input to the
     * array used for generating the rectangles in the animation
     * @param L An ArrayList of Double that contains the input for the sort animation
     */
    public static void handleDoubleArray(ArrayList<Double> L)
    {
    	doubleArray = new Double[L.size()];
    	int i = 0;
    	for(Double d : L)
    	{
    		doubleArray[i] = d;
    		i++;
    	}
    	doubleFlag = true;
    }
    
    /**
     * If the input choice was for Integer, this method copied the input to the
     * array used for generating the rectangles in the animation
     * @param L An ArrayList of Integer that contains the input for the sort animation
     */
    public static void handleIntArray(ArrayList<Integer> L)
    {
    	integerArray = new Integer[L.size()];
    	int i = 0;
    	for(Integer d : L)
    	{
    		integerArray[i] = d;
    		i++;
    	}
    	doubleFlag = false;
    }
    
    /**
     * Returns the max value in an array of Comparable items
     * @param <T> The type of the elements of the array; must extend Comparable interface
     * @param A The array of Comparable items 
     * @return The maximum value in the array
     */
    public static <T extends Comparable<T>> T ArrayMax(T[] A)
    {
	T max = A[0];
	
	for (int i = 1; i < A.length; i++)
	{
	   if(A[i].compareTo(max) > 0) 
	       max = A[i];
	}
	
	return max;
    }
    
    /**
     * Fills an array of Rectangles with Rectangles corresponding to the values in
     * an array passed as parameter; The type of the array must be a numeric type with a total order
     * relationship
     * @param rectangles The array of Rectangles to be filled
     * @param HEIGHT The height of the area in which the values are represented
     * @param WIDTH The width of the area in which the values are represented
     */
    public static void fillR(Rectangle[] rectangles, Integer HEIGHT, Integer WIDTH)
    {
	
	int l = doubleFlag ? doubleArray.length : integerArray.length;
	Double width = ((double) WIDTH)/l;
	double doubleMax = 0;
	int intMax = 0;
	if(doubleFlag)
	{
            doubleMax = ArrayMax(doubleArray);
        }
	else
	{
	    intMax = ArrayMax(integerArray);
	}

	// the start position is 0
	Double xPosition = 0.0;
	
	for (int i = 0; i < l; i++)
	{
	    /*
	     * The height of each rectangle is defined by the ratio between the value
	     * considered and the max value in the array times the height of the area,
	     * so that values are represented with a Rectangle proportional to its 
	     * magnitude in the array
	     */
	    Double height;
	    // 20 is subtracted to leave room for the stats bar
	    if(doubleFlag)
		height = doubleArray[i] / doubleMax * (HEIGHT - 20);
	    else
		height = integerArray[i] / (double) intMax * (HEIGHT - 20);
	    // The y position of the top-left corner of the rectangle
	    Double yPosition = (HEIGHT - height);

	    Rectangle r = new Rectangle(width, height);
	    r.setTranslateX(xPosition);
	    r.setTranslateY(yPosition);
	    r.setFill(Color.WHITE);
	    /*
	     * Each rectangles is inserted in the dictionary with the index of the value
	     * it represents, so that a value and a rectangle are uniquely linked
	     */
	    rectangles[i]= r;
	    // The position is always increased by the width of the rectangles
	    xPosition += width;
	}
	
    }
    
    /**
     * Given two Rectangles, this method colors
     * both red and then white, so that a comparison between values is "highlighted", and adds the animation to the main SequentialTransition
     * that holds the animation
     * @param R The first Rectangle
     * @param P The second Rectangle
     * 
     */
    public static void colorRectangles(Rectangle R,Rectangle P)
    {
	// from white to red for the first rectangle
	Color prevColor1 = (Color) R.getFill();
	Color prevColor2 = (Color) P.getFill();
	FillTransition f = new FillTransition(compDelay,R);
	f.setToValue(Color.RED);
	// viceversa
	FillTransition f2 = new FillTransition(compDelay,R);
	f2.setToValue(prevColor1);
	// from white to red for the second rectangle
	FillTransition f3 = new FillTransition(compDelay,P);
	f3.setToValue(Color.RED);
	// viceversa
	FillTransition f4 = new FillTransition(compDelay,P);
	f4.setToValue(prevColor2);
	ParallelTransition p1 = new ParallelTransition(f,f3);
	ParallelTransition p2 = new ParallelTransition(f2,f4);
	// composition of the red-white transition for both rectangles
	SequentialTransition s = new SequentialTransition(p1,p2);
	s.setOnFinished(e->{
	    Main.updateComps();
	    Main.updateAccesses();
	    Main.updateAccesses();
	    if(Settings.getMode() == false)
		sequence.pause();
	});
	sequence.getChildren().add(s);
    }

    /**
     * Creates a SequentialTransition which contains the animation of the movement of the rectangles to the position
     * they occupy in the sorted part of the array in a particular moment
     * @param A The array of Rectangles to be sorted
     * @param first The first index of the range over which to "update" the position of the rectangles
     * @param last The second index of the range over which to "update" the position of the rectangles
     */
    public static void updatePosition(Rectangle[] A,Integer first,Integer last)
    {
	Double xPosition = leftmost(A,first,last);
	Double dx = A[first].getWidth();
	for(int i = first; i <= last; i++)
	{
            // Duration is one millisecond so that the animation is almost instantaneous
            // The node to be animated is retrieved from the dictionary of rectangles in the scene
            TranslateTransition t = new TranslateTransition(swapDelay,A[i]);
            // The "to" position is calculated from the starting point with a shift that adds up
            t.setToX(xPosition);
            
            t.setOnFinished(e->{
        	if(Settings.getMode() == false)
        	    sequence.pause();
            });

            sequence.getChildren().add(t);

	    xPosition += dx;
	}
    }

    /**
     * Returns the X position of the leftmost Rectangle in a range
     * @param A The array of Rectangles
     * @param first The first index of the range considered
     * @param last The second index of the range considered
     */
    private static Double leftmost(Rectangle[] R, Integer first, Integer last)
    {
	// The first is assumed to be the leftmost
	Double start = R[first].getTranslateX();
	for(int i = first + 1; i <= last; i++)
	{
	    // if a Rectangle is more to the left than the picked one, the substitution is made
	    if(R[i].getTranslateX() < start)
		start = R[i].getTranslateX();
	}
	return start;
    }

    /**
     * Updates the counter for array accesses in the Main class
     */
    public static void accessesUpdate()
    {
	
	/*
	 * This is a fake transition, which lasts one second, that has no visible
	 * effect but is added to the sequence of the animation and, upon being finished,
	 * updates the array accesses counter, so that the counter  increases during the 
	 * animation
	 */
	Transition t = new Transition()
	{
	    
	    @Override
	    protected void interpolate(double frac)
	    {
		
	    }
	};
	t.setDelay(Duration.ONE);
	t.setOnFinished(e->{
	    Main.updateAccesses();
	});
	sequence.getChildren().add(t);
	
    }

    /**
     * Plays the animation
     */
    public static void play()
    {
	sequence.play();
    }

    /**
     * Returns the state of the current animation, if an animation is running
     * @return true if an animation is currently running, false otherwise
     */
    public static boolean isPlaying()
    {
	return Visualizer.playFlag;
    }

    /**
     * Pauses the animation
     */
    public static void pause()
    {
        sequence.pause();
    }

    /**
     * Stops the animation, sets the rectangles back to their original positions,
     * clears the stats and puts the animation in step-by-step mode
     */
    public static void stopAnimation()
    {
        sequence.stop();
        Settings.setMode(false);
        Main.clearStats(false);
        reset();
    }

    /**
     * Puts all the rectangles in the animation in their original position before sorting
     */
    private static void reset()
    {
	for(int i = 0; i < rectangles.length; i++)
	{
	    rectangles[i].setTranslateX(copy.get(rectangles[i]));
            rectangles[i].setFill(Color.WHITE);
	}
	
    }
}