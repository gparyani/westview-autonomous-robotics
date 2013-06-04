package year2013.maze;

import java.util.*;

class Path
{
	List<Coord> path;
	
	public Path()
	{
		path = new ArrayList<Coord>();
	}
	
	public void add(Coord coord)
	{
		path.add(coord);
	}
	
	public void prune()
	{
		// yay! triple loop!
		for (int i = 0; i < path.size(); i++)
		{
			for (int j = i + 1; j < path.size(); j++)
			{
				if (path.get(i).equals(path.get(j)))
				{
					while (j > i)
					{
						path.remove(i);
						j--;
					}
					// offset i++ in the outer for-loop
					i--;
				}
			}
		}
	}
	
	public Coord getLastCoord()
	{
		return path.get(path.size() - 1);
	}
	
	public List<Movement> getMovements()
	{
		if (path.size() < 2) 
			return new ArrayList<Movement>();
		
		List<Movement> movements = new ArrayList<Movement>();
		
		Dir cur = path.get(0).dirToVonNeumannNeighbor(path.get(1));
		for (int i = 1; i < path.size(); i++)
		{
			Dir partDir = path.get(i - 1).dirToVonNeumannNeighbor(path.get(i));
			if (partDir == cur)
				movements.add(Movement.Forward);
			else
			{
				if (cur.rotateRight() == partDir)
					movements.add(Movement.TurnRight);
				else if (cur.rotateLeft() == partDir)
					movements.add(Movement.TurnLeft);
				else throw new Error("There should be no u-turns.");
				cur = partDir;
			}
		}
		
		movements.add(Movement.Forward);
		return movements;
	}
}
