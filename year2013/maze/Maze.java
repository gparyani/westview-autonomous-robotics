package year2013.maze;

import java.util.List;

public class Maze
{
	public static void main(String[] args)
	{
		Maze maze = new Maze();
		maze.move(Movement.Forward);
		maze.move(Movement.TurnLeft);
		maze.move(Movement.Forward);
		maze.move(Movement.TurnRight);
		maze.move(Movement.Forward);
		maze.move(Movement.TurnRight);
		maze.move(Movement.Forward);
		maze.move(Movement.TurnRight);
		maze.move(Movement.Forward);
		maze.move(Movement.TurnLeft);
		maze.move(Movement.Forward);
		
		maze.prune();
		for (Movement movement : maze.getMovements())
		{
			System.out.println(movement);
		}
	}
	
	Path path;
	Dir dir;
	
	public Maze()
	{
		dir = null;
		path = new Path();
		path.add(new Coord(0, 0));
	}
	
	public void move(Movement movement)
	{
		switch (movement)
		{
			case Forward:
				if (dir == null)
					// any random direction
					dir = Dir.Right;
				// add next coordinate
				path.add(path.getLastCoord().inDir(dir));
				break;
			case TurnLeft:
				dir = dir.rotateLeft();
				break;
			case TurnRight:
				dir = dir.rotateRight();
				break;
			default:
				throw new Error("Should never get here... stupid javacc can't figure out that I have exhausted the pattern match :(");
		}
	}
	
	public void prune()
	{
		path.prune();
	}
	
	public List<Movement> getMovements()
	{
		return path.getMovements();
	}
}
