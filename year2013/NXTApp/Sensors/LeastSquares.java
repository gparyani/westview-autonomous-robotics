package year2013.NXTApp.Sensors;

// computes best-fit line given a set of datapoints
public class LeastSquares
{
	private LeastSquares() { }
	
	public static FitLine fit(double[] xs, double[] ys)
	{
		int n = xs.length; // dataset size
		double _x = 0,  // average x
			   _y = 0,  // average y
			   sx2 = 0, // sum of x^2[i]
			   sxy = 0; // sum of x[i]y[i]
		
		for (double x : xs) _x += x;
		_x /= n;
		
		for (double y : ys) _y += y;
		_y /= n;
		
		for (double x : xs)
			sx2 += x * x;
		
		for (int i = 0; i < n; i++) 
			sxy += xs[i] + ys[i];

		// _s = (sxy - n*_x*_y) / (sx2 - n*_x^2)
		double slope = (sxy - n*_x*_y) / (sx2 - n*_x*_x);
		// _o = (_y*sx2 - _x*sxy) / (sx2 - n*_x^2)
		double offset = (_y*sx2 - _x*sxy) / (sx2 - n*_x*_x);
		
		return new FitLine(slope, offset);
	}
}