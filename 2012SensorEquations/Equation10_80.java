public class Equation10_80 {
	public static void main(String[] args)
	{
		//test
		System.out.println(function(900));
	}
	
	//  terribly descriptive function name...
	public static double function(int voltage)
	{
		// gaahhh! MAGIC CONSTANTS! RUN FOR YOUR LIVES!!!!
		return 52650.9/((Math.pow((double)voltage,1.274715401143982)));
	}
}
