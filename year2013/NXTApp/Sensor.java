package year2013.NXTApp;

import lejos.nxt.I2CPort;
import lejos.nxt.I2CSensor;
import lejos.nxt.SensorPort;

public abstract class Sensor extends I2CSensor
{	
	private int ReadBufferAddress;
	private int ReadBufferLength;
	
	protected Sensor(int readBufferAddress, int readBufferLength, int superproAddress, SensorPort port)
	{
		super(port, superproAddress, I2CPort.LEGO_MODE, TYPE_LOWSPEED_9V);
		this.ReadBufferAddress = readBufferAddress;
		this.ReadBufferLength = readBufferLength;
	}
	
	public void Update()
	{
		this.UpdateData();
	}
	
	protected byte[] GetReadBuffer()
	{
		byte[] readBuffer = new byte[ReadBufferLength];
		this.getData(this.ReadBufferAddress, readBuffer, this.ReadBufferLength);
		
		return readBuffer;
	}
	
	protected abstract void UpdateData();
}
