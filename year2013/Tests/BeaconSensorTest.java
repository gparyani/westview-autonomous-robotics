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
	
	public BeaconSensorTest()
	{
		super(50);
		
		seeker = new lejos.nxt.addon.IRSeekerV2(
				SensorPort.S3, Mode.AC);
		
		/*beacon = new BeaconSensor(
				SensorAddresses.A3, 
				SensorAddresses.Superpro, 
				SensorPort.S2);*/
	}
	
	protected void Update()
	{
		//beacon.Update();
		
		LCD.clearDisplay();
		LCD.drawString("Direction: " + seeker.getDirection(), 0, 0);
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
