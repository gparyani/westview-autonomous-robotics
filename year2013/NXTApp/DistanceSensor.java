package year2013.NXTApp;

import lejos.nxt.I2CPort;
import lejos.nxt.I2CSensor;
import lejos.nxt.SensorPort;

public class DistanceSensor extends I2CSensor
{
	private int ReadBufferAddress;
	private int Voltage;
	
	public DistanceSensor(int readBufferAddress, int superproAddress, SensorPort port)
	{
		super(port, superproAddress, I2CPort.LEGO_MODE, TYPE_LOWSPEED_9V);
		this.ReadBufferAddress = readBufferAddress;
		this.Voltage = 0;
	}
	
	public void Update()
	{
		byte[] readBuffer = new byte[2];
		
		this.getData(this.ReadBufferAddress, readBuffer, 2);
		
		int inputData = (0xff & (int) readBuffer[0]) * 4
				+ (0xff & (int) readBuffer[1]);
		
		this.Voltage /= 2;
		this.Voltage += inputData;
	}
	
	public int GetVoltage()
	{
		return this.Voltage;
	}
}
