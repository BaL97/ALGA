package mergesort;

import java.util.HashMap;

import javafx.scene.shape.Rectangle;
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
public class Merge
{
    static Rectangle[] C;

    /**
     * This method takes an array of a Comparable type T and sorts it.</br>
     * It calls another mergesort method which takes both the array and
     * the range over which the array must be sorted. It's convenient in that it requires
     * just the array to be sorted as a parameter, unlike the other mergesort method
     * @param <T>
     * @param <T> The type of the array elements; must extend the Comparable interface
     * @param A An array of type T
     */
    public static <T extends Comparable<T>> void mergesort(T[] A) 
    {
	if (A.length > 1)
	{
	    @SuppressWarnings("unchecked")
	    T[] B = (T[]) new Comparable[A.length];
	    Integer first = 0;
	    Integer last = A.length - 1;
	    mergesort(A, first, last,B);
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
    public static <T extends Comparable<T>> void mergesort(T[] A, Integer first, Integer last,T[] B) 
    {
	/* When the two indexes coincide the part to be sorted has one element,
	 * thus it's already sorted
	 */
	if (first < last)
	{
	    Integer mid = (first + last) / 2;
	    mergesort(A, first, mid,B);
	    mergesort(A, mid + 1, last,B);
	    merge(A, first, mid, last,B);
	}
    }

    /**
     * This method merges two sorted parts of an array, limited by first and mid and mid + 1 and last, into 
     * a sorted array
     * @param A The array where the merge occurs
     * @param first The first index of the first part to be merged
     * @param mid The second index of the first part to be merged; mid + 1 is the first index for the second part
     * @param last The second index of the second part to be merged
     */
    private static <T extends Comparable<T>> void merge(T[] A, Integer first, Integer mid, Integer last,T[] B) 
    {
	Integer i, j, k, h;
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
     * Same as {@link mergesort.Merge#mergesort(Comparable[])} in functionality. </br>
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
    public static void mergesort(Rectangle[] rectangles) 
    {
	/* Any modifications to the Nodes added to the scene reflects on the scene
	 * In order to "register" the changes in the rectangles without directly changing their position
	 * a copy of the dictionary is made and passed as a parameter along with the others
	 */
	//Rectangle[] copy = new Rectangle[rectangles.length];
	//debug.printMaps(rectangles, copy);

	if (rectangles.length > 1)
	{
	    C = new Rectangle[rectangles.length];
	    Integer first = 0;
	    Integer last = rectangles.length - 1;
	    /*
	     * Each part of the sorting process generates a SequentialTransition
	     * The result, which is returned, is a combination of the parts
	     */
	    mergesort(rectangles,first, last);
	}
    }


    /**
     * Same as {@link mergesort.Merge#mergesort(Comparable[], Integer, Integer)} in functionality, 
     * differs from it for the same reason {@link mergesort.Merge#mergesort(IndexedEntry[], HashMap)} 
     * differs from {@link mergesort.Merge#mergesort(Comparable[])} (see {@link mergesort.Merge#mergesort(IndexedEntry[], HashMap)}
     * for details)
     * @param A	The array of IndexedEntry to be sorted
     * @param first The first end of the range the array must be sorted on
     * @param last The last end of the range the array must be sorted on
     * @param rectangles A dictionary containing the Rectangles which are node of the scene in the animation
     * @param copy A copy-dictionary of the above parameter used for modifications that affect the logic of the animation,
     * but not the animation itself (at least not directly)
     * @return A SequentialTransition which contains the parts of the animation, which are generated and collected in the 
     * {@link mergesort.Merge#merge(IndexedEntry[], Integer, Integer, Integer, HashMap, HashMap)} method 
     * (could return null if no sequences are collected)
     */
    public static <T extends Comparable<T>> void mergesort(Rectangle[] A, Integer first, Integer last) 
    {
	if (first < last)
	{
	    Integer mid = (first + last) / 2;
	    mergesort(A, first, mid);
	    mergesort(A,mid + 1, last);
	    merge(A,first,mid,last);
	}
    }

    /**
     * Same as {@link Merge#merge(Comparable[], Integer, Integer, Integer)} in functionality; differs from it in that it generates Transition Objects
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
    private static <T extends Comparable<T>> void merge(Rectangle[] A, Integer first, Integer mid, Integer last) 
    {
	// For notes of the merge part of this method, see merge, at line 77
	Integer i, j, k, h;
	Double leftmost = A[first].getTranslateX();
	i = first;
	j = mid + 1;
	k = first;
	while(i <= mid && j <= last)
	{
	    // When there's a comparison between A[i] and A[j], the corresponding Rectangles are colored Red, and then White again
	    Visualizer.colorRectangles(A[i],A[j]);
	    if (A[i].getHeight() <= A[j].getHeight())
	    {
		C[k] = A[i];
		i++;
	    }
	    else
	    {
		C[k] = A[j];
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
	    A[i] = C[i];
	}
	/* After the merge is over, the position of the Rectangles is changed
	 * In order to show that, an animation showing the "update" of the part of the
	 * Array where the merge occurred is generated by the Visualizer.updatePosition method
	 * and added to the SequentialTransition to be returned
	 */
	Visualizer.updatePosition(A,first,last,leftmost);
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

}
