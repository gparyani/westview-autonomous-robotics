package year2013.Tests;

import lejos.nxt.LCD;
import year2013.NXTApp.*;

public class NXTAppButtonTest extends NXTApp
{
	public NXTAppButtonTest()
	{
		super(500);
	}
	
	public void Update()
	{
		LCD.clearDisplay();
		
		LCD.drawString("B|D U P R", 0, 0);
		LCD.drawString("-+-------", 0, 1);
		
		LCD.drawString("L|" + (Button.Left.IsDown() ? "T" : "F") + " " + (Button.Left.IsUp() ? "T" : "F") + " " + (Button.Left.Pressed() ? "T" : "F") + " " + (Button.Left.Released() ? "T" : "F"), 0, 2);
		LCD.drawString("R|" + (Button.Right.IsDown() ? "T" : "F") + " " + (Button.Right.IsUp() ? "T" : "F") + " " + (Button.Right.Pressed() ? "T" : "F") + " " + (Button.Right.Released() ? "T" : "F"), 0, 3);
		LCD.drawString("U|" + (Button.Enter.IsDown() ? "T" : "F") + " " + (Button.Enter.IsUp() ? "T" : "F") + " " + (Button.Enter.Pressed() ? "T" : "F") + " " + (Button.Enter.Released() ? "T" : "F"), 0, 4);
		LCD.drawString("D|" + (Button.Escape.IsDown() ? "T" : "F") + " " + (Button.Escape.IsUp() ? "T" : "F") + " " + (Button.Escape.Pressed() ? "T" : "F") + " " + (Button.Escape.Released() ? "T" : "F"), 0, 5);
		
	}
	public boolean ShouldExit()
	{
		return false;
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new NXTAppButtonTest().Run();
	}
}
