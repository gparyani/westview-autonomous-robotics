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
		
		A0 = new DistanceSensor(Sensor.ADDR_A0, Sensor.SUPERPRO_ADDR, SensorPort.S2);
		A1 = new DistanceSensor(Sensor.ADDR_A1, Sensor.SUPERPRO_ADDR, SensorPort.S2);
		B = new DigitalSensor(Sensor.ADDR_B, 0, Sensor.SUPERPRO_ADDR, SensorPort.S2);
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
	}
	
	protected boolean ShouldExit()
	{
		return Left.IsDown() || Right.IsDown() || Enter.IsDown() || Escape.IsDown();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new SensorTest().Run();
	}
}
