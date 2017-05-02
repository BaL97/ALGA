package visualizer;

import java.util.HashMap;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Visualizer extends Application
{
    @Override
    public void start(Stage primaryStage) 
    {
	prepareStage(primaryStage);
	primaryStage.show();
    }
    
    public void prepareStage(Stage stage)
    {
        Timeline timeline = new Timeline();
	final Integer LENGTH = 10000;
        final int WIDTH = 1000, HEIGHT = 750;
        final double width = ((double)WIDTH) / LENGTH;
        Group root = new Group();
        IndexedEntry<Integer>[] A = (IndexedEntry<Integer> [])new IndexedEntry[LENGTH];
        HashMap<Integer,Rectangle> rectangles = new HashMap<Integer,Rectangle>();
        Scene s;

	fillArray(A);

	fillR(rectangles,A, HEIGHT, width);

	root.getChildren().addAll(rectangles.values());

	s = new Scene(root, WIDTH, HEIGHT);

	s.setFill(Color.BLACK);

	
	stage.setTitle("ALGA");
	stage.setScene(s);
	stage.show();

	timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().addAll(mergesort.Array.mergesort(A, rectangles));
        timeline.play();
	
    }
    
    
    public static void main(String[] args)
    {
	launch(args);
    }
    
    public void fillArray(IndexedEntry<Integer>[] A)
    {
	Random r = new Random();

	for (int i = 0; i < A.length; i++)
	{
	   A[i] = new IndexedEntry<Integer>(i,r.nextInt(1000));
	}
	
    }
    
    public int ArrayMax(IndexedEntry<Integer>[] A)
    {
	int max = A[0].getValue();
	
	for (int i = 1; i < A.length; i++)
	{
	   if(A[i].compareTo(max) > 0) 
	       max = A[i].getValue();
	}
	
	return max;
    }
    
    public void fillR(HashMap<Integer,Rectangle> rectangles,IndexedEntry<Integer>[] A, Integer HEIGHT, double WIDTH)
    {
	int max = ArrayMax(A);

	double xPosition = 0;
	
	for (int i = 0; i < A.length; i++)
	{
	    double height = A[i].getValue() / (double)max * HEIGHT;
	    int yPosition = (int) (HEIGHT - height);

	    Rectangle r = new Rectangle(xPosition, yPosition, WIDTH, height);
	    r.setFill(Color.WHITE);
	    rectangles.put(A[i].getIndex(), r);

	    xPosition += WIDTH;
	}
	
    }
    
    public static KeyFrame swapRectangles(Integer i, Integer j, HashMap<Integer,Rectangle> rectangles)
    {
	KeyValue kv = new KeyValue(rectangles.get(i).xProperty(), rectangles.get(j).getX());
	KeyValue kv2 = new KeyValue(rectangles.get(j).xProperty(), rectangles.get(i).getX());
	Duration d = Duration.millis(1000);
	KeyFrame kf = new KeyFrame(d,kv,kv2);

	Double xtmp = rectangles.get(i).getX();
	rectangles.get(i).setX(rectangles.get(j).getX());
	rectangles.get(j).setX(xtmp);

	return kf;
    }


    
}