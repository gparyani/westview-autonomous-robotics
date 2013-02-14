package year2012;

import java.util.*;

// implements an interpolated data lookup-table
// that uses a linear spline to interpolate unknown
// datapoints
public class LookupTable
{
	private TreeMap<Double, Double> map;
	
	public LookupTable()
	{
		map = new TreeMap<Double, Double>();
	}
	
	// adds the datapoint to the lookup table
	public void set(double x, double y)
	{
		map.put(x, y);
	}
	
	// removes a given datapoint from the lookup table.
	// returns true if there was such a datapoint (and it was then removed).
	public boolean remove(double x)
	{
		return map.remove(x) != null;
	}
	
	public double get(double x)
	{
		if (map.size() < 2)
			throw new IllegalStateException(
					"Must have 2 datapoints before requesting data.");
		Double left = map.floorKey(x),
			   right = map.ceilingKey(x);
	    if (left == null)
	    {
	    	
	    }
	    else if (right == null)
	    {
	    	
	    }
	    else
	    {
	    	
	    }
	}
	
	private double interp(double x, double leftX, double rightX, double leftY, double rightY)
	{
		// "I have only proven this code correct; not tested it."
		return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
	}
}
