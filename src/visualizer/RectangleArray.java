package visualizer;

import java.util.HashSet;

import javafx.scene.shape.Rectangle;

public class RectangleArray
{
    private RectangleEntry[] rectangles;
    
    public RectangleEntry[] getRectangles()
    {
        return rectangles;
    }

    public HashSet<Rectangle> rectangles()
    {
	HashSet<Rectangle> s = new HashSet<Rectangle>();

	for (RectangleEntry r : rectangles)
	{
	   s.add(r.getR());
	}

	return s;
    }

    public RectangleArray(int dim)
    {
	rectangles = new RectangleEntry[dim];
    }
    
    public Integer length()
    {
	return rectangles.length;
    }

}
