package year2013.NXTApp;

import year2013.LookupTable;
import lejos.nxt.SensorPort;

public class DistanceSensor extends Sensor
{
	private int Voltage;
	
	public DistanceSensor(int readBufferAddress, int superproAddress, SensorPort port)
	{
		super(readBufferAddress, 2, superproAddress, port);
		this.Voltage = 0;
	}
	
	protected void ProcessData(byte[] readBuffer)
	{
		int inputData = (0xff & (int) readBuffer[0]) * 4
				+ (0xff & (int) readBuffer[1]);
		
		this.Voltage /= 2;
		this.Voltage += inputData;
	}
	
	public int GetVoltage()
	{
		return this.Voltage;
	}
	
	// return value is in cm
	public static double GetDistance(DistanceSensor longRangeSensor, DistanceSensor mediumRangeSensor, DigitalSensor shortRangeSensor)
	{
		boolean shortRange = shortRangeSensor.GetData();
		double mediumRange = mediumRangeSensor.GetVoltage();
		double longRange = longRangeSensor.GetVoltage();
		
		if (shortRange)
			return LookupTable.MediumRangeData.get(mediumRange);
		else
			return LookupTable.LongRangeData.get(longRange);
	}
}
