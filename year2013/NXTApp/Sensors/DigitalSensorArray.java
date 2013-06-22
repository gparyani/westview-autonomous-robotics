package year2013.NXTApp.Sensors;

import lejos.nxt.SensorPort;

public class DigitalSensorArray extends Sensor
{
	private final int NUM_SENSORS = 8;
	
	public static final int FrontLeft = 0, FrontRight = 1,
					  RightFront = 2, RightBack = 3,
					  BackRight = 4, BackLeft = 5,
					  LeftBack = 6, LeftFront = 7;
	
	public static final int OriginalDir = 0,
			CWFromOriginal = 1,
			HalfCircleFromOriginal = 2,
			CCWFromOriginal = 3;
	
	private DigitalSensor[] sensors;
	private int direction = OriginalDir;
	
	public DigitalSensorArray(int readBufferAddress, SensorPort port1, SensorPort port2)
	{
		super(-1, -1, -1, port1);
		
		this.sensors = new DigitalSensor[NUM_SENSORS];
		
		this.sensors[FrontLeft] = new DigitalSensor(readBufferAddress, 1, port1);
		this.sensors[FrontRight] = new DigitalSensor(readBufferAddress, 0, port1);
		this.sensors[RightFront] = new DigitalSensor(readBufferAddress, 3, port2);
		this.sensors[RightBack] = new DigitalSensor(readBufferAddress, 0, port2);
		this.sensors[BackRight] = new DigitalSensor(readBufferAddress, 2, port2);
		this.sensors[BackLeft] = new DigitalSensor(readBufferAddress, 1, port2);
		this.sensors[LeftBack] = new DigitalSensor(readBufferAddress, 3, port1);
		this.sensors[LeftFront] = new DigitalSensor(readBufferAddress, 2, port1);
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
	public boolean getData(int sensorIndex)
	{
		return this.sensors[sensorIndex].GetData();
	}

	public void rotateClockwise()
	{
		for (int i = 0; i < 3; i++)
			rotateCounterClockwise(); // yay.
	}
	public void rotateCounterClockwise()
	{
		DigitalSensor tFrontLeft = sensors[FrontLeft];
		sensors[FrontLeft] = sensors[RightFront];
		sensors[RightFront] = sensors[BackRight];
		sensors[BackRight] = sensors[LeftBack];
		sensors[LeftBack] = tFrontLeft;
		
		DigitalSensor tFrontRight = sensors[FrontRight];
		sensors[FrontRight] = sensors[RightBack];
		sensors[RightBack] = sensors[BackLeft];
		sensors[BackLeft] = sensors[LeftFront];
		sensors[LeftFront] = tFrontRight;
		
		this.direction = (this.direction + 3) % 4;
	}
	
	public int getDirection()
	{
		return this.direction;
	}
}