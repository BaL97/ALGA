package mergesort;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.scene.shape.Rectangle;
import visualizer.IndexedEntry;
import visualizer.Visualizer;

public class Array
{
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

    public <T extends Comparable<T>> void mergesort(T[] A, Integer first, Integer last) 
    {
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

    public static <T extends Comparable<T>> Queue<KeyFrame> mergesort(IndexedEntry<T>[] A,HashMap<Integer,Rectangle> rectangles) 
    {
        Queue<KeyFrame> q = new LinkedList<KeyFrame>();
	if (A.length > 1)
	{
	    Integer first = 0;
	    Integer last = A.length - 1;
	    Integer mid = (first + last) / 2;
	    q.addAll(mergesort(A, first, mid, rectangles));
	    q.addAll(mergesort(A, mid + 1, last, rectangles));
	    q.addAll(merge(A, first, mid, last, rectangles));
	}
	return q;
    }

    public static <T extends Comparable<T>> Queue<KeyFrame> mergesort(IndexedEntry<T>[] A, Integer first, Integer last,HashMap<Integer,Rectangle> rectangles) 
    {
        Queue<KeyFrame> q = new LinkedList<KeyFrame>();
	if (first < last)
	{
	    Integer mid = (first + last) / 2;
	    q.addAll(mergesort(A, first, mid, rectangles));
	    q.addAll(mergesort(A, mid + 1, last, rectangles));
	    q.addAll(merge(A, first, mid, last, rectangles));
	}
	return q;
    }

    private static <T extends Comparable<T>> Queue<KeyFrame> merge(IndexedEntry<T>[] A, Integer first, Integer mid, Integer last,HashMap<Integer,Rectangle> rectangles) 
    {
	Queue<KeyFrame> q = new LinkedList<KeyFrame>();
	Queue<KeyFrame> q2 = new LinkedList<KeyFrame>();
	Queue<KeyFrame> q1 = new LinkedList<KeyFrame>();
	Integer i, j, k, h;
	IndexedEntry<T>[] B = new IndexedEntry[last];
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
	    q2.add(Visualizer.swapRectangles(A[j].getIndex(),A[h].getIndex(),rectangles));
	}
	
	for(i = first; i < k; i++)
	{
	    A[i] = B[i];
	    q1.add(Visualizer.swapRectangles(A[i].getIndex(),B[i].getIndex(),rectangles));
	}
        q.addAll(q1);
        q.addAll(q2);
	return q;
    } 
}
