package year2013.maze;

enum Dir
{
	Left, Right, Up, Down;
	
	public Dir rotateLeft()
	{
		switch (this)
		{
			case Left: return Down;
			case Down: return Right;
			case Right: return Up;
			case Up: return Left;
		}
		throw new Error("Should never get here...");
	}
	public Dir rotateRight()
	{
		switch (this)
		{
			case Left: return Up;
			case Up: return Right;
			case Right: return Down;
			case Down: return Left;
		}
		throw new Error("Should never get here...");
	}
}
