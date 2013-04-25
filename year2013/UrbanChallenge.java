package year2013;

import lejos.nxt.LCD;
import year2013.NXTApp.NXTApp;

public class UrbanChallenge extends NXTApp
{
	
	protected UrbanChallenge()
	{
		super(50);
	}

	public void Update()
	{
		LCD.drawString("Welcome to the\nUrban Challenge!\nPress any button\nto win.", 0, 0);
	}

	public boolean ShouldExit()
	{
		return Button.Left.IsDown() || Button.Right.IsDown() || Button.Enter.IsDown() || Button.Escape.IsDown();
	}

	public static void main(String[] args)
	{
		new UrbanChallenge().Run();
	}
}
