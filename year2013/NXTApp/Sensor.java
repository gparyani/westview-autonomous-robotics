package year2013.NXTApp;

import lejos.nxt.I2CPort;
import lejos.nxt.I2CSensor;
import lejos.nxt.SensorPort;

public abstract class Sensor extends I2CSensor
{
	public static int SUPERPRO_ADDR = 0x10;
	public static int ADDR_A0 = 0x42,
					   ADDR_A1 = 0x44,
					   ADDR_A2 = 0x46,
					   ADDR_A3 = 0x48,
					   ADDR_B = 0x4C;
	
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
		byte[] readBuffer = new byte[2];
		
		this.getData(this.ReadBufferAddress, readBuffer, this.ReadBufferLength);
		
		ProcessData(readBuffer);
	}
	
	protected abstract void ProcessData(byte[] readBuffer);
}
