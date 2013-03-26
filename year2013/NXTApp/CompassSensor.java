package year2013.NXTApp;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

public class CompassSensor extends Sensor
{
	private CompassHTSensor Compass;
	private int Angle;
	
	public CompassSensor(SensorPort port)
	{
		super(-1, -1, -1, port);
		Compass = new CompassHTSensor(port);
	}
	
	protected void UpdateData()
	{
		this.Angle = (int)Compass.getDegreesCartesian();// % 360;
	}
	
	public int GetAngle()
	{
		return this.Angle;
	}
}
