package visualizer;

public class IndexedEntry<T extends Comparable<T>> implements Comparable<IndexedEntry<T>>
{
    private Integer index;
    private T value;

    public IndexedEntry()
    {
	super();
    }

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

    public int compareTo(T o)
    {
	return value.compareTo(o);
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T n)
    {
        this.value = n;
    }
}
