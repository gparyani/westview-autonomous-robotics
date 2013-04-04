package year2013.NXTApp.Sensors;

//represents the slope and offset of a best-fit line
public class FitLine
{
	public double slope, offset;
	
	public FitLine(double slope, double offset)
	{
		this.slope = slope;
		this.offset = offset;
	}
}