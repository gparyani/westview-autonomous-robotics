package year2013.maze;

public enum Movement 
{
	TurnLeft('L'), TurnRight('R'),
	Forward('-');
	
	public final char c;
	
	Movement(char c)
	{
		this.c = c;
	}
	
	public static Movement fromChar(char c)
	{
		if (c == TurnLeft.c)
			return TurnLeft;
		else if (c == TurnRight.c)
			return TurnRight;
		else if (c == Forward.c)
			return Forward;
		else throw new IllegalArgumentException("c = " + c);
	}
}
