package year2013.NXTApp.Sensors;

import lejos.nxt.SensorPort;

public class DigitalSensorArray extends Sensor
{
	private final int NUM_SENSORS = 8;
	
	public final int FrontLeft = 0, FrontRight = 1,
					  RightFront = 2, RightBack = 3,
					  BackRight = 4, BackLeft = 5,
					  LeftBack = 6, LeftFront = 7;
	
	private DigitalSensor[] sensors;
	
	public DigitalSensorArray(int readBufferAddress, SensorPort port)
	{
		super(-1, -1, -1, port);
		
		this.sensors = new DigitalSensor[NUM_SENSORS];
		
		this.sensors[FrontLeft] = new DigitalSensor(readBufferAddress, FrontLeft, port);
		this.sensors[FrontRight] = new DigitalSensor(readBufferAddress, FrontRight, port);
		this.sensors[RightFront] = new DigitalSensor(readBufferAddress, RightFront, port);
		this.sensors[RightBack] = new DigitalSensor(readBufferAddress, RightBack, port);
		this.sensors[BackRight] = new DigitalSensor(readBufferAddress, BackRight, port);
		this.sensors[BackLeft] = new DigitalSensor(readBufferAddress, BackLeft, port);
		this.sensors[LeftBack] = new DigitalSensor(readBufferAddress, LeftBack, port);
		this.sensors[LeftFront] = new DigitalSensor(readBufferAddress, LeftFront, port);
	}
	
	protected void UpdateData()
	{
		for (Sensor sensor : this.sensors)
			sensor.Update();
	}
	
	public void setInvert(boolean invert)
	{
		for (DigitalSensor sensor : this.sensors)
			sensor.InvertData(invert);
	}
	
	public boolean[] getData()
	{
		boolean[] data = new boolean[NUM_SENSORS];
		for (int sensor = 0; sensor < NUM_SENSORS; sensor++)
			data[sensor] = this.sensors[sensor].GetData();
		return data;
	}
}
