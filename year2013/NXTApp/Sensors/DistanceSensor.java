package year2013.NXTApp.Sensors;

import lejos.nxt.SensorPort;

public class DistanceSensor extends Sensor
{
	private double Voltage;
	
	public DistanceSensor(int readBufferAddress, int superproAddress, SensorPort port)
	{
		super(readBufferAddress, 2, superproAddress, port);
		this.Voltage = 0;
	}
	
	protected void UpdateData()
	{
		byte[] readBuffer = this.GetReadBuffer();
		int inputData = (0xff & (int) readBuffer[0]) * 4
				+ (0xff & (int) readBuffer[1]);
		
		//this.Voltage /= 2;
		//this.Voltage += inputData;
		final double INPUT_WEIGHT = .4;
		this.Voltage = this.Voltage * (1 - INPUT_WEIGHT) + inputData * INPUT_WEIGHT;
	}
	
	public double GetVoltage()
	{
		return this.Voltage;
	}
	
//	private static LookupTable MediumRangeData, LongRangeData;
//	private static String[] MediumRangeReadings = new String[]
//	{
//		"5	846",
//		"10	811.5",
//		"15	505",
//		"20	343.3333333",
//		"25	283.6666667",
//		"30	257.8333333",
//		"35	237.5",
//		"40	239.5",
//		"45	237.5",
//		"50	243.8333333",
//		"55	240.3333333",
//		"60	245.1666667",
//		"65	242.6666667",
//		"70	235.5",
//		"75	237.8333333",
//		"80	233.6666667",
//		"85	235.3333333"
//	};
//	private static String[] LongRangeReadings = new String[]
//	{
//		"20	678.3333333",
//		"30	559",
//		"40	411",
//		"50	324",
//		"60	265",
//		"70	236.3333333",
//		"80	228",
//		"90	203.3333333",
//		"100 181.6666667",
//		"110 167.3333333",
//		"120 164",
//		"130 169",
//		"140 172.3333333",
//		"150 165"
//	};

	
//	public static void LoadSensorData()
//	{
//		MediumRangeData = new LookupTable();
//		LongRangeData = new LookupTable();
//		for (int i = 0; i < MediumRangeReadings.length; i++)
//			MediumRangeData.set(getEntry(MediumRangeReadings[i]));
//		for (int i = 0; i < LongRangeReadings.length; i++)
//			LongRangeData.set(getEntry(LongRangeReadings[i]));
//	}
//	private static MapEntry<Double, Double> getEntry(String line)
//	{
//		double distance = Double.parseDouble(line);
//		String afterDistance = line.substring(((Double)distance).toString().length());
//		double voltage = Double.parseDouble(afterDistance);
//		return new MapEntry<Double, Double>(voltage, distance);
//	}
	
	// return value is in cm
	public static double GetDistance(DistanceSensor longRangeSensor, DistanceSensor mediumRangeSensor, DigitalSensor shortRangeSensor)
	{
		boolean shortRange = shortRangeSensor.GetData();
		double mediumRange = mediumRangeSensor.GetVoltage();
		double longRange = longRangeSensor.GetVoltage();
		
		final double CALIBRATION_MULT = 11000;
		final double CALIBRATION_OFFSET = 0;
		
		if (shortRange)
			return 0;//MediumRangeData.get(mediumRange);
		else
			return CALIBRATION_MULT / mediumRange + CALIBRATION_OFFSET;//LongRangeData.get(longRange);
		// take a look at http://www.pololu.com/file/download/gp2y0a21yk0f.pdf?file_id=0J85
	}
}
