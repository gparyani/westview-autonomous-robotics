package year2013.NXTApp;

import lejos.nxt.SensorPort;

public class DigitalSensor extends Sensor
{
	private int ReadBufferBit;
	private boolean Data;
	
	private boolean InvertData;
	
	public DigitalSensor(int readBufferAddress, int readBufferBit, int superproAddress, SensorPort port)
	{
		super(readBufferAddress, 1, superproAddress, port);
		this.ReadBufferBit = readBufferBit;
		this.Data = false;
		this.InvertData = false;
	}
	
	public void InvertData(boolean invert)
	{
		this.InvertData = invert;
	}
	
	protected void UpdateData()
	{
		byte[] readBuffer = this.GetReadBuffer();
		this.Data = (readBuffer[0] & (0x01 << ReadBufferBit)) == 0;
		
		if (this.InvertData)
			this.Data = !this.Data;
	}
	
	public boolean GetData()
	{
		return this.Data;
	}
}
