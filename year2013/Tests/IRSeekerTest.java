package year2013.Tests;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.Motors;
import year2013.NXTApp.NXTApp;
import year2013.NXTApp.Sensors.BeaconSensor;

public class IRSeekerTest extends NXTApp
{
	BeaconSensor beaconSensor;
	
	public IRSeekerTest()
	{
		super(50);
		
		beaconSensor = new BeaconSensor(SensorPort.S3, BeaconSensor.AC);
		
		LCD.drawString("Press any button\nto begin.\n\nMake sure the\nmotors are\nturned on!", 0, 0);
		Button.WaitForKeyPress(); 
		
		Motors.Initialize(SensorPort.S1);
	}
	protected void Update()
	{
		beaconSensor.Update();
		
		int highestIndex = beaconSensor.getIndexOfMostIntensity();
		if (highestIndex > 3)
		{
			Motors.Front.forward();
			Motors.Back.forward();
			Motors.Left.forward();
			Motors.Right.forward();
		}
		else if (highestIndex < 3)
		{
			Motors.Front.backward();
			Motors.Back.backward();
			Motors.Left.backward();
			Motors.Right.backward();
		}
		else
		{
			Motors.Front.stop();
			Motors.Back.stop();
			Motors.Left.backward();
			Motors.Right.forward();
		}
		
		LCD.clearDisplay();
		int[] beaconValues = beaconSensor.getBackingSeeker().getSensorValues();
		String valuesStr = "[" + beaconValues[0];
		for (int i = 1; i < beaconValues.length; i++)
			valuesStr += "," + beaconValues[i];
		valuesStr += "]";
		LCD.drawString("Vals:" + valuesStr, 0, 0);
		LCD.drawString("Highest:" + beaconSensor.getBackingSeeker().getSensorValue(highestIndex) + " at " + highestIndex, 0, 1);
	}
	
	
	protected boolean ShouldExit()
	{
		if (Button.Escape.IsDown() || beaconSensor.isCloseToBeacon())
		{
			Motors.Front.stop();
			Motors.Back.stop();
			Motors.Left.stop();
			Motors.Right.stop();
			return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		new IRSeekerTest().Run();
	}
}
