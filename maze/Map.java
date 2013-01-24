package maze;

import java.util.ArrayList;

public class Map {
	
	private int column;
	private int row;
	public int rowShift;
	public int columnShift;
	private Tile[][] maze;
	private int differential;
	private ArrayList<Integer> directions;
	private Tile lastTile;
	//private Robot robot;
	
	public Map(int numRow, int numCol, int spacing)
	{
		maze = new Tile[numCol][numRow];
		differential = spacing;
		column = numCol;
		row = numRow;
		columnShift = column / 2;
		rowShift= row / 2;
		maze[rowShift][columnShift] = new Tile(row / 2, column / 2); 
		lastTile = maze[rowShift][columnShift];
		//for(int row = 0; row < numRow; row++)
			//for(int column = 0; column < numCol; column++)
	}
	
	public void initTile(int row, int col)
	{
		maze[row + rowShift][col + columnShift]= new Tile(row, col, lastTile);
		lastTile = maze[row + columnShift][col + rowShift];
	}
	// 
	// ^^^ random empty comment? lol - Jacob Posner
	public void addDirection(int direction)
	{
		directions.add(direction);
	}
}
