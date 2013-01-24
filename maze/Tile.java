package maze;

public class Tile
{
	private int Column;
	private int Row;
	private Tile lastTile;
	
	public Tile(int row, int column, Tile last)
	{
		Column = column;
		Row = row;
		lastTile = last;
	}
	
	public Tile(int row, int column)
	{
		Column = column;
		Row = row;
	}
	
	public int getColumn()
	{
		return Column;
	}
	
	public int getRow()
	{
		return Row;
	}
	
	public Tile getLastTile()
	{
		return lastTile;
	}
}
