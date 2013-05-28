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
	
	public DigitalSensorArray(int readBufferAddress, SensorPort port)
	{
		super(-1, -1, -1, port);
		
		this.sensors = new DigitalSensor[NUM_SENSORS];
		
		this.sensors[FrontLeft] = new DigitalSensor(readBufferAddress, 7, port);
		this.sensors[FrontRight] = new DigitalSensor(readBufferAddress, 6, port);
		this.sensors[RightFront] = new DigitalSensor(readBufferAddress, 5, port);
		this.sensors[RightBack] = new DigitalSensor(readBufferAddress, 4, port);
		this.sensors[BackRight] = new DigitalSensor(readBufferAddress, 3, port);
		this.sensors[BackLeft] = new DigitalSensor(readBufferAddress, 2, port);
		this.sensors[LeftBack] = new DigitalSensor(readBufferAddress, 1, port);
		this.sensors[LeftFront] = new DigitalSensor(readBufferAddress, 0, port);
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
		
		this.direction--;
		if (this.direction < 0)
			this.direction += 4;
	}
	public void rotateClockwise()
	{
		rotateCounterClockwise();
		rotateCounterClockwise();
		rotateCounterClockwise(); // yay.
	}
	
	public int getDirection()
	{
		return this.direction;
	}
}