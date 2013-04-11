package year2013.Tests;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.NXTApp;
import year2013.NXTApp.Sensors.DigitalSensor;
import year2013.NXTApp.Sensors.SensorAddresses;

public class ShortRangeTest extends NXTApp
{
	DigitalSensor front, back, left, right;
	
	public ShortRangeTest()
	{
		super(50);
		
		front = new DigitalSensor(SensorAddresses.B, 0, SensorPort.S2);
		back = new DigitalSensor(SensorAddresses.B, 1, SensorPort.S2);
		left = new DigitalSensor(SensorAddresses.B, 2, SensorPort.S2);
		right = new DigitalSensor(SensorAddresses.B, 3, SensorPort.S2);
	}
	
	public void Update()
	{
		front.Update(); back.Update(); left.Update(); right.Update();
		
		LCD.drawString("F: " + front.GetData(), 0, 0);
		LCD.drawString("B: " + back.GetData(), 0, 1);
		LCD.drawString("L: " + left.GetData(), 0, 2);
		LCD.drawString("R: " + right.GetData(), 0, 3);
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
