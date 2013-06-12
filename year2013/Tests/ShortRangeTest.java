package year2013.Tests;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.NXTApp;
import year2013.NXTApp.Sensors.DigitalSensorArray;
import year2013.NXTApp.Sensors.SensorAddresses;

public class ShortRangeTest extends NXTApp
{
	DigitalSensorArray sensors;
	
	public ShortRangeTest()
	{
		super(50);
		
		sensors = new DigitalSensorArray(SensorAddresses.B, SensorPort.S2, SensorPort.S3);
	}
	
	public void Update()
	{
		sensors.Update();
		
		boolean[] data = sensors.getData();
		LCD.drawString("FL: " + data[DigitalSensorArray.FrontLeft] + " ", 0, 0);
		LCD.drawString("FR: " + data[DigitalSensorArray.FrontRight] + " ", 0, 1);
		LCD.drawString("RF: " + data[DigitalSensorArray.RightFront] + " ", 0, 2);
		LCD.drawString("RB: " + data[DigitalSensorArray.RightBack] + " ", 0, 3);
		LCD.drawString("BR: " + data[DigitalSensorArray.BackRight] + " ", 0, 4);
		LCD.drawString("BL: " + data[DigitalSensorArray.BackLeft] + " ", 0, 5);
		LCD.drawString("LB: " + data[DigitalSensorArray.LeftBack] + " ", 0, 6);
		LCD.drawString("LF: " + data[DigitalSensorArray.LeftFront] + " ", 0, 7);
	}
	public boolean ShouldExit()
	{
		return Button.Left.IsDown() || Button.Right.IsDown() || Button.Enter.IsDown() || Button.Escape.IsDown();
	}
	
	public static void main(String[] args)
	{
		new ShortRangeTest().Run();
	}
}
