package year2013.NXTApp.Sensors;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.*;
import lejos.nxt.addon.IRSeekerV2.Mode;

public class BeaconSensor
{
	public final boolean
		AC = true,
		DC = false;
	private IRSeekerV2 seeker;
	private boolean mode;
	
	public BeaconSensor(SensorPort port, boolean mode)
	{		
		this.mode = mode;
		seeker = new IRSeekerV2(port, getLejosMode(mode));
	}
	
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
}
