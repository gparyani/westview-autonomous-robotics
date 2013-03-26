package year2013.Tests;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;

public class SensorTest extends NXTApp
{
	private DistanceSensor A0, A1;
	private DigitalSensor B;
	
	public SensorTest()
	{
		super(50);
		
		//DistanceSensor.LoadSensorData();
		
		A0 = new DistanceSensor(SensorAddresses.A0, SensorAddresses.Superpro, SensorPort.S2);
		A1 = new DistanceSensor(SensorAddresses.A1, SensorAddresses.Superpro, SensorPort.S2);
		B = new DigitalSensor(SensorAddresses.B, 0, SensorAddresses.Superpro, SensorPort.S2);
	}
	
	double maxA0 = -1, maxA1 = -1;
	
	protected void Update()
	{
		A0.Update();
		A1.Update();
		B.Update();
		
		if (A0.GetVoltage() > maxA0)
			maxA0 = A0.GetVoltage();
		if (A1.GetVoltage() > maxA1)
			maxA1 = A1.GetVoltage();
		
		LCD.clearDisplay();
		LCD.drawString("Long: " + A0.GetVoltage(), 0, 0);
		LCD.drawString("Medium: " + A1.GetVoltage(), 0, 1);
		LCD.drawString("Short: " + B.GetData(), 0, 2);
		//if (maxA0 != -1)
		//	LCD.drawString("Max Long: " + maxA0, 0, 4);
		//if (maxA1 != -1)
		//	LCD.drawString("Max Medium: " + maxA1, 0, 5);
		
		LCD.drawString("Distance: " + DistanceSensor.GetDistance(/*A0, */A1, B) + "cm", 0, 7);
	}
	
	protected boolean ShouldExit()
	{
		return Button.Escape.IsDown();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new SensorTest().Run();
	}
}
