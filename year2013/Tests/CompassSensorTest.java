package year2013.Tests;

import year2013.NXTApp.NXTApp;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.CompassSensor;

public class CompassSensorTest extends NXTApp
{
	CompassSensor Compass;
	
	public CompassSensorTest()
	{
		super(50);
		
		Compass = new CompassSensor(SensorPort.S3);
	}
	
	protected void Update()
	{
		int degree = Compass.GetAngle();
		LCD.drawString("Deg: " + degree, 0, 0);
	}
	
	protected boolean ShouldExit()
	{
		return false;
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new CompassSensorTest().Run();
	}
}
