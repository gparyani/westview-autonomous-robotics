package year2013.Tests;

import lejos.nxt.SensorPort;
import year2013.NXTApp.Motors;
import year2013.NXTApp.NXTApp;

public class MotorTest extends NXTApp
{
	public MotorTest()
	{
		super(0);
		
		Motors.Initialize(SensorPort.S1);
				
		Motors.Left.forward();
		Motors.Right.forward();
		Motors.Front.forward();
		Motors.Back.forward();
	}

	public void Update() { }
	public boolean ShouldExit()
	{
		if (Button.Left.IsDown() || Button.Right.IsDown() || Button.Enter.IsDown() || Button.Escape.IsDown())
		{
			Motors.Left.stop();
			Motors.Right.stop();
			Motors.Back.stop();
			Motors.Front.stop();
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception
	{
		new MotorTest().Run();
	}
}
