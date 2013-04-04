package year2013.Tests;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;

public class BeaconSensorTest extends NXTApp
{
	private BeaconSensor beacon;
	
	public BeaconSensorTest()
	{
		super(50);
		
		beacon = new BeaconSensor(SensorPort.S4, BeaconSensor.DC);
	}
	
	protected void Update()
	{
		//beacon.Update();
		
		if (Button.Enter.IsDown() && !Button.Enter.WasDown())
			beacon.invertMode();
		
		LCD.clearDisplay();
		LCD.drawString("Mode: " + (beacon.getMode() ? "AC" : "DC"), 0, 0);
		float dir = beacon.getAngle();
		//if (dir == 0)
		//	LCD.drawString("No sensor found.", 0, 1);
		//else
		LCD.drawString("Direction: " + dir, 0, 1);
	}
	
	protected boolean ShouldExit()
	{
		return Button.Escape.IsDown();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new BeaconSensorTest().Run();
	}
}
