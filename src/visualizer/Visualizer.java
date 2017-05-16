package visualizer;

import java.util.HashMap;
import java.util.Random;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import mergesort.Array;

/**
 * Contains all the methods that handle the Rectangle animation
 * @author Andrea
 *
 */
public class Visualizer extends Application
{

    @Override
    public void start(Stage primaryStage) 
    {
	prepareStage(primaryStage);
    }
    
    /**
     * Prepares the stage and handles the animation part
     * @param stage The stage to be handled, (primaryStage)
     */
    public void prepareStage(Stage stage)
    {
	// Keeps all the nodes in the Scene
        Group root = new Group();
        SequentialTransition sequence = new SequentialTransition();
	final Integer LENGTH = 1000;
        final int WIDTH = 1000, HEIGHT = 750;
        @SuppressWarnings("unchecked")
	IndexedEntry<Integer>[] A = (IndexedEntry<Integer> [])new IndexedEntry[LENGTH];
        // Dictionary that keeps all the rectangles in the scene
        HashMap<Integer,Rectangle> rectangles = new HashMap<Integer,Rectangle>();
        Scene s;

        // A is filled with random values
	fillArray(A);

	// R is filled with rectangles corresponding to the values of A
	fillR(rectangles,A, HEIGHT, WIDTH);

	// The rectangles are added to root group
	root.getChildren().addAll(rectangles.values());

	s = new Scene(root, WIDTH, HEIGHT);

	s.setFill(Color.BLACK);

	stage.setTitle("ALGA");
	stage.setScene(s);
	stage.show();

	// All the Transitions from the mergesort are retrieved and added to sequence
        sequence.getChildren().add(mergesort.Array.mergesort(A, rectangles));
        sequence.play();
        // Sends a message if the array is sorted, just debug stuff, to be removed
        if(Array.isSorted(A))
            System.out.println("The Array is sorted");
    }
    
    
    public static void main(String[] args)
    {
	launch(args);
    }
    
    /**
     * Fills the array A with random values and gives each an index corresponding with
     * the order of generation
     * @param A The array of IndexedEntry to be filled
     */
    public void fillArray(IndexedEntry<Integer>[] A)
    {
	Random r = new Random();

	for (int i = 0; i < A.length; i++)
	{
	   A[i] = new IndexedEntry<Integer>(i,r.nextInt(1000));
	}
	
    }
    
    /**
     * Returns the max value in an array of Comparable items
     * @param <T> The type of the elements of the array; must extend Comparable interface
     * @param A The array of Comparable items 
     * @return The maximum value in the array
     */
    public <T extends Comparable<T>> T ArrayMax(T[] A)
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
     * Fills a dictionary of Integer-Rectangles with Rectangles corresponding to values in
     * an array passed as parameter; The type of the array must be a numeric type with a total order
     * relationship
     * @param rectangles The dictionary of Integer-Rectangles to be filled
     * @param A The array of numeric values to represent with rectangles
     * @param HEIGHT The height of the area in which the values are represented
     * @param WIDTH The width of the area in which the values are represented
     */
    public void fillR(HashMap<Integer,Rectangle> rectangles,IndexedEntry<Integer>[] A, Integer HEIGHT, Integer WIDTH)
    {
	
	double width = ((double) WIDTH)/A.length;
	int max = ArrayMax(A).getValue();

	// the start position is 0
	double xPosition = 0;
	
	for (int i = 0; i < A.length; i++)
	{
	    /*
	     * The height of each rectangle is defined by the ratio between the value
	     * considered and the max value in the array times the height of the area,
	     * so that values are represented with a Rectangle proportional to its 
	     * magnitude in the array
	     */
	    double height = A[i].getValue() / (double)max * HEIGHT;
	    // The y position of the top-left corner of the rectangle
	    double yPosition = (HEIGHT - height);

	    Rectangle r = new Rectangle(xPosition, yPosition, width, height);
	    r.setFill(Color.WHITE);
	    /*
	     * Each rectangles is inserted in the dictionary with the index of the value
	     * it represents, so that a value and a rectangle are uniquely linked
	     */
	    rectangles.put(A[i].getIndex(), r);

	    // The position is always increased by the width of the rectangles
	    xPosition += width;
	}
	
    }
    
    /**
     * Given the indexes of two Rectangles and the dictionary that contains them, this method colors
     * both red and then white, so that a comparison between values is "highlighted", and returns a SequentialTransition
     * that holds the animation
     * @param i The index of the first Rectangle
     * @param j The index of the second Rectangle
     * @param rectangles The dictionary of rectangles that contains the rectangles
     * @return A SequentialTransition with the coloring of the two rectangles, which happen in parallel for
     * both the Rectangle when they transition from white to red and viceversa, but the two moments are separated
     * (the first happens before the second)
     * 
     */
    public static SequentialTransition colorRectangles(Integer i, Integer j, HashMap<Integer, Rectangle> rectangles)
    {

	SequentialTransition s = new SequentialTransition();
	Duration d = Duration.millis(25);
	// from white to red for the first rectangle
	FillTransition f = new FillTransition(d,rectangles.get(i),Color.WHITE,Color.RED);
	// viceversa
	FillTransition f2 = new FillTransition(d,rectangles.get(i),Color.RED,Color.WHITE);
	// from white to red for the second rectangle
	FillTransition f3 = new FillTransition(d,rectangles.get(j),Color.WHITE,Color.RED);
	// viceversa
	FillTransition f4 = new FillTransition(d,rectangles.get(j),Color.RED,Color.WHITE);
	// composition of the white-red transition for both rectangles
	ParallelTransition p1 = new ParallelTransition(f,f3);
	// composition of the red-white transition for both rectangles
	ParallelTransition p2 = new ParallelTransition(f2,f4);
	s.getChildren().addAll(p1,p2);
	return s;
    }

    /**
     * Creates a SequentialTransition which contains the animation of the movement of the rectangles to the position
     * they occupy in the sorted part of the array in a particular moment
     * @param a The array of values sorted
     * @param first The first index of the range over which to "update" the position of the rectangles
     * @param last The second index of the range over which to "update" the position of the rectangles
     * @param rectangles The dictionary of rectangles that are nodes in the scene
     * @param copy The copy dictionary for the logical changes in the animation
     * @return A SequentialTransition which contains the movement of the rectangles to their position in the sorted
     * version of the part of array contained by the first and the last indexes
     */
    public static <T extends Comparable<T>> SequentialTransition updatePosition(IndexedEntry<T>[] a,Integer first,Integer last,HashMap<Integer,Rectangle> rectangles, HashMap<Integer,Rectangle> copy)
    {
	SequentialTransition s = new SequentialTransition();
	// The starting point of the repositioning is the leftmost in the range
	Double xPosition = leftmost(a,first,last,copy);
	// The shift from the starting point
	Double dx = copy.get(a[first].getIndex()).getWidth();
	for(int i = first; i <= last; i++)
	{
	    // Duration is one millisecond so that the animation is almost instantaneous
	    TranslateTransition t = new TranslateTransition(Duration.ONE);
	    // The node to be animated is retrieved from the dictionary of rectangles in the scene
	    t.setNode(rectangles.get(a[i].getIndex()));
	    // The "from" position is retrieved from the copy dictionary, which will be modified over the course of the animation
	    t.setFromX(copy.get(a[i].getIndex()).getX());
	    // The "to" position is calculated from the starting point with a shift that adds up
	    t.setToX(xPosition);
	    copy.get(a[i].getIndex()).setX(xPosition);
	    
	    s.getChildren().add(t);
	    
	    xPosition += dx;
	}
	return s;
    }

    /**
     * Returns the X position of the leftmost Rectangle in a range
     * @param A The array of values linked to the rectangles
     * @param first The first index of the range considered
     * @param last The second index of the range considered
     * @param rectangles The dictionary of rectangles which contains the rectangles linked to the values in A
     * @return A Double value with the X coordinate of the leftmost Rectangle in the range
     */
    private static <T extends Comparable<T>> Double leftmost(IndexedEntry<T>[] A, Integer first, Integer last,
	    HashMap<Integer, Rectangle> rectangles)
    {
	// The first is assumed to be the leftmost
	Double start = rectangles.get(A[first].getIndex()).getX();
	for(int i = first + 1; i <= last; i++)
	{
	    // if a Rectangle is more to the left than the picked one, the substitution is made
	    if(rectangles.get(A[i].getIndex()).getX() < start)
		start = rectangles.get(A[i].getIndex()).getX(); 
	}
	return start;
    }

}