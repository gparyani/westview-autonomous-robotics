package year2013.Tests;

import year2013.NXTApp.NXTApp;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.Sensors.CompassSensor;

public class CompassTest extends NXTApp
{
	CompassSensor Compass;
	
	public CompassTest()
	{
		super(50);
		
		Compass = new CompassSensor(SensorPort.S3);
	}
	
	public void Update()
	{
		Compass.Update();
		int degree = Compass.GetAngle();
		LCD.clearDisplay();
		LCD.drawString("Deg: " + degree, 0, 0);
	}
	
	public boolean ShouldExit()
	{
		return false;
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new CompassTest().Run();
	}
}
