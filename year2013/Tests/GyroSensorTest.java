package year2013.Tests;
import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class GyroSensorTest extends NXTApp
{
	GyroSensor Gyro;
	
	public GyroSensorTest()
	{
		super(50);
		Gyro = new GyroSensor(SensorPort.S3);
	}
	
	public void Update()
	{
		Gyro.Update();
		
		LCD.clearDisplay();
		LCD.drawString("a: " + Gyro.GetAngle(), 0, 0);
		LCD.drawString("w: " + Gyro.GetAngularVelocity(), 0, 1);
	}

	public boolean ShouldExit()
	{
		return Button.Enter.IsDown() || Button.Escape.IsDown()
			|| Button.Left.IsDown() || Button.Right.IsDown();
	}
	
	public static void main(String... args) throws Exception
	{
		new GyroSensorTest().Run();
	}
}