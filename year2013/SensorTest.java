package year2013;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.*;

public class SensorTest extends NXTApp
{
	private DistanceSensor A0, A1;
	private DigitalSensor B;
	
	public SensorTest()
	{
		super(50);
		
		LookupTable.LoadSensorData();
		
		A0 = new DistanceSensor(SensorAddresses.A0, SensorAddresses.Superpro, SensorPort.S2);
		A1 = new DistanceSensor(SensorAddresses.A1, SensorAddresses.Superpro, SensorPort.S2);
		B = new DigitalSensor(SensorAddresses.B, 0, SensorAddresses.Superpro, SensorPort.S2);
	}
	
	protected void Update()
	{
		A0.Update();
		A1.Update();
		B.Update();
		
		LCD.clearDisplay();
		LCD.drawString("Accum0: " + A0.GetVoltage(), 0, 0);
		LCD.drawString("Accum1: " + A1.GetVoltage(), 0, 1);
		LCD.drawString("SR Read: " + B.GetData(), 0, 2);
		
		LCD.drawString("Distance: " + DistanceSensor.GetDistance(A0, A1, B), 0, 4);
	}
	
	protected boolean ShouldExit()
	{
		return Button.Escape.IsDown();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new SensorTest().Run();
	}
}
