package year2013.maze;

class Coord
{
	public int x, y;
	
	public Coord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Coord other)
	{
		return x == other.x && y == other.y;
	}
	
	public String toString()
	{
		return "[" + x + "," + y + "]";
	}
	
	public Coord inDir(Dir dir)
	{
		switch (dir)
		{
			case Left: return new Coord(x - 1, y);
			case Up: return new Coord(x, y - 1);
			case Right: return new Coord(x + 1, y);
			case Down: return new Coord(x, y + 1);
		}
		throw new Error("Should never get here!");
	}
	
	public Dir dirToVonNeumannNeighbor(Coord coord)
	{
		int dx = coord.x - x,
			dy = coord.y - y;
		if (Math.abs(dx) > 1 || Math.abs(dy) > 1 || Math.abs(dx) == Math.abs(dy))
			throw new Error("Cannot handle non-Von-Neumann-neighbors.");
		if (dx == 1) return Dir.Right;
		else if (dx == -1) return Dir.Left;
		else if (dy == -1) return Dir.Up;
		else /* if (dy == 1)*/ return Dir.Down;
	}
}
