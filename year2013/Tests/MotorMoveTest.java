package year2013.Tests;

import lejos.nxt.SensorPort;
import year2013.NXTApp.Motors;
import year2013.NXTApp.NXTApp;

public class MotorMoveTest extends NXTApp
{
	protected MotorMoveTest()
	{
		super(0);
		
		Motors.Initialize(SensorPort.S1);
		
		Motors.Left.stop();
		Motors.Right.stop();
		Motors.Back.stop();
		Motors.Front.stop();
	}

	public static void main(String[] args)
	{
		new MotorMoveTest().Run();
	}
	
	protected void Update()
	{
		if (Button.Enter.Pressed())
			Motors.Front.forward();
		else if (Button.Enter.Released())
			Motors.Front.stop();
		if (Button.Escape.Pressed())
			Motors.Back.forward();
		else if (Button.Escape.Released())
			Motors.Back.stop();
		if (Button.Left.Pressed())
			Motors.Left.forward();
		else if (Button.Left.Released())
			Motors.Left.stop();
		if (Button.Right.Pressed())
			Motors.Right.forward();
		else if (Button.Right.Released())
			Motors.Right.stop();
	}
	
	protected boolean ShouldExit()
	{
		return false;
	}
}
