package visualizer;

import java.util.HashSet;
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
    static final int WIDTH = 1000, HEIGHT = 750;
    static Group root = new Group();
    static Scene s;
    
    static final Integer LENGTH = 10000;

    static Integer[] A = new Integer[LENGTH];
    static RectangleArray R = new RectangleArray(LENGTH);

    final static double width = ((double)WIDTH) / LENGTH;

    private static Timeline timeline;
    private AnimationTimer timer;
    
    @Override
    public void start(Stage primaryStage) 
    {
	
	fillArray(A);

	fillR(A, HEIGHT, width);

	
	root.getChildren().addAll(R.rectangles());

	s = new Scene(root, WIDTH, HEIGHT);

	s.setFill(Color.BLACK);
	
	primaryStage.setTitle("ALGA");
	primaryStage.setScene(s);
	primaryStage.show();
	
	timeline = new Timeline();
	timeline.setCycleCount(1);
	timeline.setAutoReverse(false);
	timeline.play();
	timer = new AnimationTimer()
		{

		    @Override
		    public void handle(long now)
		    {
		    }
	    
		};
	timer.start();
    }
    
    
    public static void main(String[] args)
    {
	launch(args);
    }
    
    public void fillArray(Integer[] A)
    {
	Random r = new Random();

	for (int i = 0; i < A.length; i++)
	{
	   A[i] = r.nextInt(1000);
	}
	
    }
    
    public static int ArrayMax(Integer[] A)
    {
	int max = A[0];
	
	for (int i = 1; i < A.length; i++)
	{
	   if(A[i] > max) 
	       max = A[i];
	}
	
	return max;
    }
    
    public static void fillR(Integer[] A, Integer HEIGHT, double WIDTH)
    {
	int max = ArrayMax(A);

	double xPosition = 0;
	
	for (int i = 0; i < A.length; i++)
	{
	    double height = A[i] / (double)max * HEIGHT;
	    int yPosition = (int) (HEIGHT - height);

	    Rectangle r = new Rectangle(xPosition, yPosition, WIDTH, height);
	    r.setFill(Color.WHITE);
	    R.getRectangles()[i] = new RectangleEntry(A[i],r);

	    xPosition += WIDTH;
	}
	
    }

    public static void ArrayMergeSort(RectangleArray A) 
    {
	if (A.length() > 1)
	{
	    Integer first = 0;
	    Integer last = A.length() - 1;
	    Integer mid = (first + last) / 2;
	    ArrayMergeSort(A, first, mid);
	    ArrayMergeSort(A, mid + 1, last);
	    Merge(A, first, mid, last);
	}
    }

    public static void ArrayMergeSort(RectangleArray A, Integer first, Integer last) 
    {
	if (first < last)
	{
	    Integer mid = (first + last) / 2;
	    ArrayMergeSort(A, first, mid);
	    ArrayMergeSort(A, mid + 1, last);
	    Merge(A, first, mid, last);
	}
    }

    private static void Merge(RectangleArray A, Integer first, Integer mid, Integer last) 
    {
	Integer i, j, k, h;
	RectangleArray B = new RectangleArray(last);
	i = first;
	j = mid + 1;
	k = first;
	while(i <= mid && j <= last)
	{
	    if (A.getRectangles()[i].compareTo(A.getRectangles()[j]) <= 0)
	    {
		B.getRectangles()[k] = A.getRectangles()[i];
		i++;
	    }
	    else
	    {
		B.getRectangles()[k] = A.getRectangles()[j];
		j++;
	    }
	    
	    k++;
	}
	
	j = last;

	for(h = mid; h >= i; h--)
	{
	    A.getRectangles()[j] = A.getRectangles()[h];
	    j--;
	}
	
	for(i = first; i < k; i++)
	{
	    A.getRectangles()[i] = B.getRectangles()[i];
	}
    }
    
}