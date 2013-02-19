package year2013;
import lejos.nxt.Button;
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
			LCD.drawString("" + (sensor.getAngularVelocity() - 1023f), 0, 0);
			if (Button.ENTER.isDown() || Button.ESCAPE.isDown()) break;
			Thread.sleep(50);
		}
	}
}