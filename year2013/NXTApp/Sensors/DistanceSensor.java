package year2013.NXTApp.Sensors;

import lejos.nxt.SensorPort;

public class DistanceSensor extends Sensor
{
	static
	{
		DistanceSensor.calibrate(new double[] {
			13.5,
			28,
			44,
			58,
			74,
			91,
			110,
			105,
			133,
			155
		});
	}
	
	private double Voltage;
	
	public DistanceSensor(int readBufferAddress, SensorPort port)
	{
		super(readBufferAddress, 2, SensorAddresses.Superpro, port);
		this.Voltage = 0;
	}
	
	protected void UpdateData()
	{
		byte[] readBuffer = this.GetReadBuffer();
		int inputData = (0xff & (int) readBuffer[0]) * 4
				+ (0xff & (int) readBuffer[1]);
		
		//this.Voltage /= 2;
		//this.Voltage += inputData;
		final double INPUT_WEIGHT = .6;
		this.Voltage = this.Voltage * (1 - INPUT_WEIGHT) + inputData * INPUT_WEIGHT;
	}
	
	public double GetVoltage()
	{
		return this.Voltage;
	}
	
	private static double 
		calibrationMult = 6800, 
		calibrationOffset = 0;
	// takes a set of voltage levels,
	// where voltages[i] is the voltage at distance = i*10 cm
	// and then calibrates for optimal indicated distance results
	public static void calibrate(double[] voltages)
	{
		double[] xs = new double[voltages.length],
				 ys = new double[voltages.length];
		for (int i = 0; i < voltages.length; i++)
		{
			xs[i] = 10 * (i + 1);
			ys[i] = 1 / voltages[i];
		}
		FitLine line = LeastSquares.fit(xs, voltages);
		calibrationMult = line.slope;
		calibrationOffset = line.offset;
	}
	// return value is in cm
	public static double GetDistance(/*DistanceSensor longRangeSensor, */DistanceSensor mediumRangeSensor, DigitalSensor shortRangeSensor)
	{
		boolean shortRange = shortRangeSensor.GetData();
		double mediumRange = mediumRangeSensor.GetVoltage();
		//double longRange = longRangeSensor.GetVoltage();
		
		if (shortRange)
			return 0;//MediumRangeData.get(mediumRange);
		else
			return calibrationMult / mediumRange + calibrationOffset;
				//LongRangeData.get(longRange);
		// take a look at http://www.pololu.com/file/download/gp2y0a21yk0f.pdf?file_id=0J85
	}
}
