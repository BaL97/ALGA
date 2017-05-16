package visualizer;

/**
 * This class creates a pair where the first elements is of a given Comparable type,
 * the second is an integer which works as an "index".</br> 
 * It's useful in that it helps to link a Rectangle to a numeric value in an array, as 
 * two values in the array could have the same numeric value, but be represented by two
 * different rectangles; what they differ on is their index
 * @author Andrea
 *
 * @param <T> The type parameter must be Comparable; this class overloads the CompareTo method
 */
public class IndexedEntry<T extends Comparable<T>> implements Comparable<IndexedEntry<T>>
{
    private Integer index;
    private T value;

    /**
     * Constructs an IndexedEntry given an index and a value
     * @param index The index of the IndexedEntry
     * @param value The value of the IndexedEntry
     */
    public IndexedEntry(Integer index, T value)
    {
	super();
	this.index = index;
	this.value = value;
    }

    @Override
    public int compareTo(IndexedEntry<T> o)
    {
	return value.compareTo(o.getValue());
    }

    /**
     * Getter for the index
     * @return the index of the IndexedEntry
     */
    public Integer getIndex()
    {
        return index;
    }

    /**
     * Setter for the index
     * @param index the new index for the IndexedEntry
     */
    public void setIndex(Integer index)
    {
        this.index = index;
    }

    /**
     * Getter for the value
     * @return the value of the IndexedEntry
     */
    public T getValue()
    {
        return value;
    }

    /**
     * Setter for the value
     * @param n the new index for the IndexedEntry
     */
    public void setValue(T n)
    {
        this.value = n;
    }
}
