package year2013.NXTApp.Sensors;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.*;
import lejos.nxt.addon.IRSeekerV2.Mode;

public class BeaconSensor extends Sensor
{
	public static final boolean
		AC = true,
		DC = false;
	private IRSeekerV2 seeker;
	private boolean mode;
	
	public BeaconSensor(SensorPort port, boolean mode)
	{
		super(-1, -1, -1, port);
		
		this.mode = mode;
		seeker = new IRSeekerV2(port, getLejosMode(mode));
	}

	protected void UpdateData() { }
	
	private Mode getLejosMode(boolean mode)
	{
		return mode == AC ? Mode.AC : Mode.DC;
	}
	
	public void setMode(boolean mode)
	{
		this.mode = mode;
		seeker.setMode(getLejosMode(mode));
	}
	public void invertMode()
	{
		setMode(!mode);
	}
	public boolean getMode()
	{
		return mode;
	}
	
	public float getAngle()
	{
		return seeker.getAngle();
	}
	
	public IRSeekerV2 getBackingSeeker()
	{
		return this.seeker;
	}
	
	public boolean isCloseToBeacon()
	{
		int highestValue = Integer.MIN_VALUE;
		// The IRSeeker has 5 sensors: 1..5
		for (int i = 1; i <= 5; i++)
			highestValue = Math.max(highestValue, seeker.getSensorValue(i));
		// 40 seemed to be the highest value when the IRSeeker was close to the beacon.
		return highestValue >= 40;
	}
	public int getIndexOfMostIntensity()
	{
		int highestValue = Integer.MIN_VALUE, index = -1;
		for (int i = 1; i <= 5; i++)
		{
			int value = seeker.getSensorValue(i);
			if (value > highestValue)
			{
				highestValue = value;
				index = i;
			}
		}
		return index;
	}
	public int getMostIntenseSignal()
	{
		return seeker.getSensorValue(getIndexOfMostIntensity());
	}
}
