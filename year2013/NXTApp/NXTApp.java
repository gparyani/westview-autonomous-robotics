package year2013.NXTApp;

import lejos.nxt.Button;

public abstract class NXTApp
{
	private int UpdateInterval;
	protected static class Button
	{
		public static NXTButton Left, Right, Enter, Escape;
	}
	
	protected NXTApp(int updateInterval)
	{
		this.UpdateInterval = updateInterval;
		
		Button.Left = new NXTButton(lejos.nxt.Button.LEFT.isDown());
		Button.Right = new NXTButton(lejos.nxt.Button.RIGHT.isDown());
		Button.Enter = new NXTButton(lejos.nxt.Button.ENTER.isDown());
		Button.Escape = new NXTButton(lejos.nxt.Button.ESCAPE.isDown());
	}
	
	protected void Run() throws InterruptedException
	{
		while (!ShouldExit())
		{
			Update();
			
			Thread.sleep(UpdateInterval);
			
			Button.Left.Update(lejos.nxt.Button.LEFT.isDown());
			Button.Right.Update(lejos.nxt.Button.RIGHT.isDown());
			Button.Enter.Update(lejos.nxt.Button.ENTER.isDown());
			Button.Escape.Update(lejos.nxt.Button.ESCAPE.isDown());
		}
	}
	
	protected abstract void Update();
	protected abstract boolean ShouldExit();
}
