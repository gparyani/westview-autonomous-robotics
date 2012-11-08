package year2012;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.GyroSensor;

public class GyroSensorTest {
	
	public static void main(String... args) throws Exception
	{
		GyroSensor sensor = new GyroSensor(SensorPort.S3);
		while(true)
		{
			LCD.clearDisplay();
			LCD.drawString(Float.toString(sensor.getAngularVelocity()), 0, 0);
			Thread.sleep(50);
		}
	}
}