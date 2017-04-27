package visualizer;

import java.util.HashMap;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Visualizer extends Application
{
    static final int WIDTH = 1000, HEIGHT = 750;
    static Group root = new Group();
    static Scene s;
    
    static final Integer LENGTH = 10000;

    static IndexedEntry<Integer>[] A = (IndexedEntry<Integer> [])new IndexedEntry[LENGTH];
    static HashMap<Integer,Rectangle> rectangles = new HashMap<Integer,Rectangle>();

    final static double width = ((double)WIDTH) / LENGTH;

    private static Timeline timeline;
    private AnimationTimer timer;
    
    @Override
    public void start(Stage primaryStage) 
    {
	
	fillArray(A);

	ArrayMergeSort(A);
	
	fillR(A, HEIGHT, width);

	
	root.getChildren().addAll(rectangles.values());

	s = new Scene(root, WIDTH, HEIGHT);

	s.setFill(Color.BLACK);
	
	primaryStage.setTitle("ALGA");
	primaryStage.setScene(s);
	primaryStage.show();
	
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
	   A[i] = new IndexedEntry<Integer>(i,r.nextInt());
	}
	
    }
    
    public static int ArrayMax(IndexedEntry<Integer>[] A)
    {
	int max = A[0].getValue();
	
	for (int i = 1; i < A.length; i++)
	{
	   if(A[i].compareTo(max) > 0) 
	       max = A[i].getValue();
	}
	
	return max;
    }
    
    public static void fillR(IndexedEntry<Integer>[] A, Integer HEIGHT, double WIDTH)
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

    public <T extends Comparable<T>> void ArrayMergeSort(T[] A) 
    {
	if (A.length > 1)
	{
	    Integer first = 0;
	    Integer last = A.length - 1;
	    Integer mid = (first + last) / 2;
	    ArrayMergeSort(A, first, mid);
	    ArrayMergeSort(A, mid + 1, last);
	    Merge(A, first, mid, last);
	}
    }

    public <T extends Comparable<T>> void ArrayMergeSort(T[] A, Integer first, Integer last) 
    {
	if (first < last)
	{
	    Integer mid = (first + last) / 2;
	    ArrayMergeSort(A, first, mid);
	    ArrayMergeSort(A, mid + 1, last);
	    Merge(A, first, mid, last);
	}
    }

    private <T extends Comparable<T>> void Merge(T[] A, Integer first, Integer mid, Integer last) 
    {
	Integer i, j, k, h;
	T[] B = (T[]) new Comparable[last];
	i = first;
	j = mid + 1;
	k = first;
	while(i <= mid && j <= last)
	{
	    if ((A[i].compareTo(A[j])) <= 0)
	    {
		B[k] = A[i];
		i++;
	    }
	    else
	    {
		B[k] = A[j];
		j++;
	    }
	    
	    k++;
	}
	
	j = last;

	for(h = mid; h >= i; h--)
	{
	    A[j] = A[h];
	    j--;
	}
	
	for(i = first; i < k; i++)
	{
	    A[i] = B[i];
	}
    }
    
}