package visualizer;

import javafx.scene.shape.Rectangle;

public class RectangleEntry implements Comparable<RectangleEntry>
{
    private Rectangle r;
    private Integer n;

    public Rectangle getR()
    {
        return r;
    }

    public void setR(Rectangle r)
    {
        this.r = r;
    }

    public Integer getN()
    {
        return n;
    }

    public void setN(Integer n)
    {
        this.n = n;
    }

    @Override
    public int compareTo(RectangleEntry r)
    {
	if (n < r.n)
	    return - n/r.n * 100;	// percentage of how much smaller n is compared to r.n
	else if (n > r.n)
	    return n/r.n * 100; 
	else
	    return 0;
    }

    public RectangleEntry(Integer n, Rectangle r)
    {
	super();
	this.r = r;
	this.n = n;
    }
    
}
