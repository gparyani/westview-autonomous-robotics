package maze;

import java.util.ArrayList;

public class Map {
	
	private int column;
	private int row;
	public int rowShift;
	public int columnShift;
	private Tile[][] maize;
	private int differential;
	private ArrayList<Integer> directions;
	private Tile lastTile;
	//private Robot robot;
	public Map(int numRow, int numCol, int spacing)
	{
		maize = new Tile[numCol][numRow];
		differential = spacing;
		column = numCol;
		row = numRow;
		columnShift=column/2;
		rowShift= row/2;
		maize[rowShift][columnShift] = new Tile(row/2,column/2); 
		lastTile = maize[rowShift][columnShift];
		//for(int row = 0; row < numRow; row++)
			//for(int column = 0; column < numCol; column++)
	}
	public void initTile(int theRow, int theCol)
	{
		maize[theRow+rowShift][theCol+columnShift]= new Tile(theRow,theCol, lastTile);
		lastTile = maize[theRow+columnShift][theCol+rowShift];
	}
	// 
	public void addDirection(int direction)
	{
		directions.add(direction);
	}
}
