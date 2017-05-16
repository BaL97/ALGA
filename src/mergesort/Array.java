package mergesort;

import java.util.HashMap;

import javafx.animation.SequentialTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import visualizer.IndexedEntry;
import visualizer.Visualizer;

/**
 * This class implements the mergesort algorithm for an Array; there are 3 main methods:
 * 	<p>- mergesort, which takes a single array as input and sorts it, calling itself a
 * 	  mergesort methods which takes in input the range over which the array must be
 * 	  sorted </p>
 * 	<p>- mergesort, which takes an array and 2 parameters which specify the range of the
 * 	  array to be sorted; to sort the entire array, the first mergesort method can be
 * 	  used, as it calls this method with boundaries 0 and array length - 1; </p>
 * 	<p>- merge, which takes an array and three parameters which specify the parts of the
 * 	  array to merge (these parts need to be sorted);</p>
 * 
 * There are two version of the above methods: one which takes an array of IndexedEntry
 * and one which takes an array of a generic type T which extends Comparable&ltT&gt
 * The first is tweaked to let the changing of the array be animated, the latter simply sorts
 * the supplied array
 * 
 * 
 * @author Andrea
 *
 */
public class Array
{

    /**
     * This method takes an array of a Comparable type T and sorts it.</br>
     * It calls another mergesort method which takes both the array and
     * the range over which the array must be sorted. It's convenient in that it requires
     * just the array to be sorted as a parameter, unlike the other mergesort method
     * @param <T> The type of the array elements; must extend the Comparable interface
     * @param A An array of type T
     */
    public <T extends Comparable<T>> void mergesort(T[] A) 
    {
	if (A.length > 1)
	{
	    Integer first = 0;
	    Integer last = A.length - 1;
	    Integer mid = (first + last) / 2;
	    mergesort(A, first, mid);
	    mergesort(A, mid + 1, last);
	    merge(A, first, mid, last);
	}
    }

    /**
     * This method sorts the array over the specified range with the mergesort algorithm.
     * </br>It basically splits the array in two parts, orders them and then merges them; an array of 1 element is considered ordered.
     * It has complexity &Theta;(nlogn)
     * @param A The array to be sorted; the type T of the array elements must extend the Comparable interface
     * @param first The first index of the range over which the array will be sorted
     * @param last The last index of the range over which the array will be sorted
     */
    public <T extends Comparable<T>> void mergesort(T[] A, Integer first, Integer last) 
    {
	/* When the two indexes coincide the part to be sorted has one element,
	 * thus it's already sorted
	 */
	if (first < last)
	{
	    Integer mid = (first + last) / 2;
	    mergesort(A, first, mid);
	    mergesort(A, mid + 1, last);
	    merge(A, first, mid, last);
	}
    }

    private <T extends Comparable<T>> void merge(T[] A, Integer first, Integer mid, Integer last) 
    {
	Integer i, j, k, h;
	@SuppressWarnings("unchecked")
	T[] B = (T[]) new Comparable[last];
	i = first;
	j = mid + 1;
	k = first;
	/* up until one of the two parts the array was split in has been completely 
	 * scanned, put the elements in a second array, orderly
	 */
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
	
	/*
	 * If the first part that was completely scanned was the second part, the 
	 * remaining elements in the first part belong to the end of the merged
	 * array, so they are put, with the for cycle, to the end of the array
	 * in the same order as they are in the first split
	 */
	j = last;

	for(h = mid; h >= i; h--)
	{
	    A[j] = A[h];
	    j--;
	}
	
	// In the end, the elements are copied from the second array to the original
	for(i = first; i < k; i++)
	{
	    A[i] = B[i];
	}
    } 

    /**
     * Same as {@link mergesort.Array#mergesort(Comparable[])} in functionality. </br>
     * It differs from it in that there are some modifications necessary to animate the mergesort algorithm.
     * @param A The array of IndexedEntry to be sorted. The parameter passed to IndexedEntry must be 
     * Comparable for the method to work, but it's needed that they are numeric with a total 
     * order relation to be animated (This requirement by the way must be already satisfied during the 
     * generation of the Rectangles in the Visualizer Class)
     * @param rectangles A dictionary which holds the generated Rectangles in the class Visualizer from the generation
     * of the array to be sorted (NOTE: could be a global variable, not sure about it yet)
     * @return a SequentialTransition which holds the transitions for the movement of the rectangles during the sorting process,
     * in the order they occurred
     */
    public static <T extends Comparable<T>> SequentialTransition mergesort(IndexedEntry<T>[] A,HashMap<Integer,Rectangle> rectangles) 
    {
	/* Any modifications to the Nodes added to the scene reflects on the scene
	 * In order to "register" the changes in the rectangles without directly changing their position
	 * a copy of the dictionary is made and passed as a parameter along with the others
	 */
	HashMap<Integer,Rectangle> copy = new HashMap<Integer,Rectangle>();
	for(int i = 0; i < A.length; i++)
	{
	    /* A new Rectangle with the same attributes as the one in the first dictionary
	     * is created, so that they are not bound to the nodes of the scene (even though
	     * all we need is the X position of a rectangle and its index, as the first is the only
	     * thing that changes during the sorting process
	     */
	    Rectangle R = new Rectangle(rectangles.get(i).getX(), rectangles.get(i).getY(), rectangles.get(i).getWidth(), rectangles.get(i).getHeight());
	    R.setFill(Color.WHITE);
	    copy.put(i,R);
	}

        SequentialTransition s, s1 = null, s2 = null, s3 = null;
	if (A.length > 1)
	{
	    Integer first = 0;
	    Integer last = A.length - 1;
	    Integer mid = (first + last) / 2;
	    /*
	     * Each part of the sorting process generates a SequentialTransition
	     * The result, which is returned, is a combination of the parts
	     */
	    s1 = mergesort(A,first, mid, rectangles,copy);
	    s2 = mergesort(A,mid + 1, last,rectangles,copy);
	    s3 = merge(A,first,mid,last,rectangles,copy);
	}
	s = composeSequences(s1,s2,s3);
	return s;
    }

    /**
     * Same as {@link mergesort.Array#mergesort(Comparable[], Integer, Integer)} in functionality, 
     * differs from it for the same reason {@link mergesort.Array#mergesort(IndexedEntry[], HashMap)} 
     * differs from {@link mergesort.Array#mergesort(Comparable[])} (see {@link mergesort.Array#mergesort(IndexedEntry[], HashMap)}
     * for details)
     * @param A	The array of IndexedEntry to be sorted
     * @param first The first end of the range the array must be sorted on
     * @param last The last end of the range the array must be sorted on
     * @param rectangles A dictionary containing the Rectangles which are node of the scene in the animation
     * @param copy A copy-dictionary of the above parameter used for modifications that affect the logic of the animation,
     * but not the animation itself (at least not directly)
     * @return A SequentialTransition which contains the parts of the animation, which are generated and collected in the 
     * {@link mergesort.Array#merge(IndexedEntry[], Integer, Integer, Integer, HashMap, HashMap)} method 
     * (could return null if no sequences are collected)
     */
    public static <T extends Comparable<T>> SequentialTransition mergesort(IndexedEntry<T>[] A, Integer first, Integer last,HashMap<Integer,Rectangle> rectangles,HashMap<Integer,Rectangle> copy) 
    {
        SequentialTransition s = null;
        SequentialTransition s1 = null, s2 = null, s3 = null;
	if (first < last)
	{
	    Integer mid = (first + last) / 2;
	    s1 = mergesort(A, first, mid, rectangles,copy);
	    s2 = mergesort(A,mid + 1, last,rectangles,copy);
	    s3 = merge(A,first,mid,last,rectangles,copy);
	}
	if(composeSequences(s1,s2,s3) != null)
	{
	    s = new SequentialTransition();
            s.getChildren().add(composeSequences(s1,s2,s3));
	}
	
	return s;
    }

    /**
     * Same as {@link Array#merge(Comparable[], Integer, Integer, Integer)} in functionality; differs from it in that it generates Transition Objects
     * and collects them in a SequentialTransition which is ultimately returned; an animation is generated to represent the comparison of two values,
     * another is generated to show the movement of the Rectangles from their position to the one they occupy in the sorted array (or in the sorted part
     * of it)
     * 
     * @param A The array of IndexedEntry to be sorted
     * @param first The first index for the first part of the split
     * @param mid The mid index, which is the end of the first part of the array; mid + 1 is the index for the beginning of the second part of the array
     * @param last The last index, which defines the end of the second part
     * @param rectangles The dictionary of Rectangles which are nodes of the Scene
     * @param copy The copy dictionary needed for modifications in the logic of the animation
     * @return a SequentialTransition which gathers the animations from the comparison and the animations from the "update" of the state of the part
     * of the array after the merge
     */
    private static <T extends Comparable<T>> SequentialTransition merge(IndexedEntry<T>[] A, Integer first, Integer mid, Integer last,HashMap<Integer,Rectangle> rectangles, HashMap<Integer,Rectangle> copy ) 
    {
	// For notes of the merge part of this method, see merge, at line 77
	SequentialTransition s = null;
	Integer i, j, k, h;
	@SuppressWarnings("unchecked")
	IndexedEntry<T>[] B = new IndexedEntry[last];
	i = first;
	j = mid + 1;
	k = first;
	while(i <= mid && j <= last)
	{
	    // Whene there's a comparison between A[i] and A[j], the corresponding Rectangles are colored Red, and then White again
	    s = new SequentialTransition(Visualizer.colorRectangles(A[i].getIndex(),A[j].getIndex(),rectangles));
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
	/* After the merge is over, the position of the Rectangles is changed
	 * In order to show that, an animation showing the "update" of the part of the
	 * Array where the merge occurred is generated by the Visualizer.updatePosition method
	 * and added to the SequentialTransition to be returned
	 */
	s.getChildren().add(Visualizer.updatePosition(A,first,last,rectangles,copy));
	return s;
    }

    /**
     * Simple method that returns true if the given array is sorted
     * @param <T> The type of the Array must extend the Comparable interface
     * @param A The array to be checked
     * @return true if the given array is sorted, false otherwise
     */
    public static <T extends Comparable<T>> boolean isSorted(T[] A)
    {
	for(int i = 0; i < A.length - 1; i++)
	{
	    if(A[i].compareTo(A[i + 1]) > 0) 
		return false;
	}
	return true;
    } 

    /**
     * Given 3 SequentialTransition Objects (even null), it composes orderly them and returns
     * the composition; if all of them are null, the return value is null as well
     * @param s1 The first SequentialTransition in the composition
     * @param s2 The second SequentialTransition in the composition
     * @param s3 The third SequentialTransition in the composition
     * @return The composition of the sequences, in the order they're given, if at least one non-null argument is passed; null otherwise
     */
    private static SequentialTransition composeSequences(SequentialTransition s1, SequentialTransition s2,
	    SequentialTransition s3)
    {
	/* By default, the composition is empty; if at least one non-null SequentialTransition
	 * is passed, this boolean flag is set to false
	 */
	Boolean empty = true;
	SequentialTransition s = new SequentialTransition();
	if(s1 != null)
	{
	    s.getChildren().add(s1);
	    empty = false;
	}
	if(s2 != null)
	{
	    s.getChildren().add(s2);
	    empty = false;
	}
	if(s3 != null)
	{
	    s.getChildren().add(s3);
	    empty = false;
	}
	if(!empty)
	    return s;
	else
	    return null;
    }
}
