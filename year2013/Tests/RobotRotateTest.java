package year2013.Tests;

import lejos.nxt.SensorPort;
import year2013.NXTApp.Motors;
import year2013.NXTApp.NXTApp;
import year2013.NXTApp.Sensors.DigitalSensorArray;
import year2013.NXTApp.Sensors.SensorAddresses;

public class RobotRotateTest extends NXTApp
{
	DigitalSensorArray sensors;
	
	public RobotRotateTest()
	{
		super(50);
		
		Motors.Initialize(SensorPort.S1);
		sensors = new DigitalSensorArray(SensorAddresses.Superpro, SensorPort.S2, SensorPort.S3);
	}

	protected void Update()
	{
		if (Button.Left.Pressed())
		{
			sensors.rotateCounterClockwise();
			Motors.rotateCounterClockwise();
		}
		else if (Button.Right.Pressed())
		{
			sensors.rotateClockwise();
			Motors.rotateClockwise();
		}
		
		Motors.Front.setPower(100);
		Motors.Left.setPower(50);
		Motors.Right.setPower(50);
		Motors.Back.stop();
	}

	protected boolean ShouldExit()
	{
		if (Button.Escape.IsDown() || Button.Enter.IsDown())
		{
			Motors.stopAllMotors();
			return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		new RobotRotateTest().Run();
	}
}
