package year2013.NXTApp;

import lejos.nxt.Button;

public abstract class NXTApp
{
	private int UpdateInterval;
	protected NXTButton Left, Right, Enter, Escape;
	
	protected NXTApp(int updateInterval)
	{
		this.UpdateInterval = updateInterval;
		
		Left = new NXTButton(Button.LEFT.isDown());
		Right = new NXTButton(Button.RIGHT.isDown());
		Enter = new NXTButton(Button.ENTER.isDown());
		Escape = new NXTButton(Button.ESCAPE.isDown());
	}
	
	protected void Run() throws InterruptedException
	{
		while (!ShouldExit())
		{
			Update();
			
			Thread.sleep(UpdateInterval);
			
			Left.Update(Button.LEFT.isDown());
			Right.Update(Button.RIGHT.isDown());
			Enter.Update(Button.ENTER.isDown());
			Escape.Update(Button.ESCAPE.isDown());
		}
	}
	
	protected abstract void Update();
	protected abstract boolean ShouldExit();
}
