package year2013.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public final class Maze
{
	public static void main(String[] args)
	{
		Maze maze = new Maze();

		Movement L = Movement.TurnLeft,
				 F = Movement.Forward,
				 R = Movement.TurnRight;
		
		maze.move(F, L, F, R, F, R, F, L, F, F, R, F, R, F, R, F, L, L, F, R, F, R, F, L, L, F, R, F, F);
		for (Movement movement : maze.getMovements())
			System.out.println(movement);
	}
	
	List<Movement> path;
	
	public Maze()
	{
		path = new ArrayList<Movement>();
	}
	
	@SafeVarargs final // stupid eclipse!
	private void move(Movement... movements)
	{
		for (Movement movement : movements)
			move(movement);
	}
	public void move(Movement movement)
	{
		path.add(movement);
		while (pruneEnd()) ;
	}
	
	boolean pruneEnd()
	{
		// yay regexps!
		String pathStr = movementsToStr(path);
		
		Pattern leftPattern = Pattern.compile("L-R(?<f>-+)R-L$");
		java.util.regex.Matcher leftMatcher = leftPattern.matcher(pathStr);
		if (leftMatcher.find())
		{
			int numForward = leftMatcher.group("f").length();
			pathStr = pathStr.substring(0, leftMatcher.start());
			while (numForward --> 0)
				pathStr += "-";
			return true;
		}
		
		Pattern rightPattern = Pattern.compile("R-L(?<f>-+)L-R$");
		Matcher rightMatcher = rightPattern.matcher(pathStr);
		if (rightMatcher.find())
		{
			int numForward = rightMatcher.group("f").length();
			pathStr = pathStr.substring(0, rightMatcher.start());
			while (numForward --> 0)
				pathStr += "-";
			return true;
		}
		
		return false;
	}
	
	@SafeVarargs final // stupid eclipse!
	<T> List<T> list(T... items)
	{
		List<T> result = new ArrayList<T>(items.length);
		for (T item : items)
			result.add(item);
		return result;
	}
	boolean convertEnd(List<Movement> from, List<Movement> to)
	{
		if (path.size() < from.size())
			return false;
		for (int i = 0; i < from.size(); i++)
			if (path.get(i + path.size() - from.size()) != from.get(i))
				return false;
		for (int i = 0; i < from.size(); i++)
			path.remove(path.size() - 1);
		path.addAll(to);
		return true;
	}
	
	public List<Movement> getMovements()
	{
		return path;
	}
	
	String movementsToStr(List<Movement> movements)
	{
		String str = "";
		for (Movement movement : movements)
			str += movement.c;
		return str;
	}
	List<Movement> strToMovements(String str)
	{
		List<Movement> movements = new ArrayList<Movement>();
		for (char c : str.toCharArray())
			movements.add(Movement.fromChar(c));
		return movements;
	}
}