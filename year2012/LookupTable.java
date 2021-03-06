package year2012;

import java.util.*;
import java.util.Map.Entry;

// implements an interpolated data lookup-table
// that uses a cosine interpolation to guesstimate unknown
// datapoints
public class LookupTable
{
	public static void main(String[] args)
	{
		LookupTable table = new LookupTable();
		table.set(0, 0);
		table.set(3, 0);
		table.set(5, 2);
		table.set(6, 1);
		
		table.test(-1, 0);
		table.test(0, 0);
		table.test(1, 0);
		table.test(3, 0);
		table.test(4, 1);
		table.test(5, 2);
		table.test(5.5, 1.5);
		table.test(6, 1);
		table.test(6.5, 0.5);
		table.test(7, 0);
		int passed = tests - failed;
		System.out.println(passed  + "/" + tests + " passed, " + failed + " failed.");
	}
	private static int tests = 0;
	private static int failed = 0;
	public void test(double x, double y)
	{
		tests++;
		double found = this.get(x);
		if (found == y)
			System.out.println(x + " -> " + y);
		else
		{
			System.out.println(x + " -> " + found + ", should be " + y);
			failed++;
		}
	}
	
	private TreeMap<Double, Double> map;
	// smallest number > 0
	private final double EPSILON = 0.0000000000001;
	
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
		
		// see if a datapoint exists exactly for x
		Double exact = map.get(x);
		if (exact != null)
			return exact;
		
		// get the entry just to the left and just to the right of x
		Entry<Double, Double> left = map.floorEntry(x),
						      right = map.ceilingEntry(x);

		boolean outOfBounds = (left == null) || (right == null);
	    if (left == null)
	    {
	    	// desired point is to the left of all data
	    	// so get the two left-most datapoints, and interpolate from there
	    	left = right;
	    	right = map.ceilingEntry(left.getKey() + EPSILON);
	    }
	    else if (right == null)
	    {
	    	// desired point is to the right of all data
	    	// so get the two right-most datapoints, and interpolate
	    	right = left;
	    	left = map.floorEntry(right.getKey() - EPSILON);
	    }
	    
	    if (outOfBounds)
	    	// use a linear interpolation out of bounds
	    	// because cos interpolation will oscillate infinitely
	    	return linear_interp(x,
	    			left.getKey(), right.getKey(),
	    			left.getValue(), right.getValue());
	    // in bounds, use a cosine interpolation for better smoothing
    	return cos_interp(x, 
    			left.getKey(), right.getKey(), 
    			left.getValue(), right.getValue());
	}
	
	private double linear_interp(double x, 
			double leftX, double rightX, 
			double leftY, double rightY)
	{
		// "I have only proven this code correct; not tested it."
		// - but I'm pretty sure it works!
		return leftY + (rightY - leftY) / (rightX - leftX) * (x - leftX);
	}
	
	private double cos_interp(double x, double leftY, double rightY)
	{
		double u = (1 - Math.cos(x * Math.PI))/ 2;
		return leftY * (1 - u) + rightY * u;
	}
	private double cos_interp(double x, 
			double leftX, double rightX, 
			double leftY, double rightY)
	{
		return cos_interp((x - leftX) / (rightX - leftX), leftY, rightY);
	}
}
