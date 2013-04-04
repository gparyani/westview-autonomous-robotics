package year2013.Tests;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.IRSeekerV2.Mode;
import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;

public class BeaconSensorTest extends NXTApp
{
	//private BeaconSensor beacon;
	lejos.nxt.addon.IRSeekerV2 seeker;
	boolean ac = true;
	
	public BeaconSensorTest()
	{
		super(50);
		
		seeker = new lejos.nxt.addon.IRSeekerV2(
				SensorPort.S4, Mode.AC);
		
		/*beacon = new BeaconSensor(
				SensorAddresses.A3, 
				SensorAddresses.Superpro, 
				SensorPort.S2);*/
	}
	
	protected void Update()
	{
		//beacon.Update();
		
		if (Button.Enter.IsDown() && !Button.Enter.WasDown())
			seeker.setMode((ac = !ac) ? Mode.AC : Mode.DC);
		
		LCD.clearDisplay();
		LCD.drawString("Mode: " + (ac ? "AC" : "DC"), 0, 0);
		int dir = seeker.getDirection();
		if (dir == 0)
			LCD.drawString("No sensor found.", 0, 1);
		else
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
