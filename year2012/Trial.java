package year2012;

public class Trial {

	public static void main(String... args) throws Exception
	{
		throw new MyOwnError();
	}
}

class MyOwnError extends Error
{
	public MyOwnError()
	{
		super();
	}
	
	public MyOwnError(String message)
	{
		super(message);
	}
}