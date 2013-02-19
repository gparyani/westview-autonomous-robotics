package year2013.NXTApp;

public class NXTButton
{
	private boolean isDown, wasDown;
	
	public NXTButton(boolean isDown)
	{
		this.isDown = isDown;
		wasDown = false;
	}
	
	public void Update(boolean isDown)
	{
		wasDown = this.isDown;
		this.isDown = isDown;
	}
	
	public boolean IsDown()
	{
		return isDown;
	}
	public boolean WasDown()
	{
		return wasDown;
	}
	public boolean IsUp()
	{
		return !isDown;
	}
	public boolean WasUp()
	{
		return !wasDown;
	}
	public boolean Pressed()
	{
		return IsDown() && WasUp();
	}
	public boolean Released()
	{
		return IsUp() && WasDown();
	}
}
