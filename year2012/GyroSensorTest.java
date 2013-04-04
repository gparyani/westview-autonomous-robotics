package year2012;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.GyroDirectionFinder;
import lejos.nxt.addon.GyroSensor;

public class GyroSensorTest {
	
	public static void main(String... args) throws Exception
	{
		GyroDirectionFinder finder = new GyroDirectionFinder(new GyroSensor(SensorPort.S3), true);
		finder.resetCartesianZero();
		while((Button.ENTER.isUp() && Button.ESCAPE.isUp()))
		{
			LCD.clearDisplay();
			LCD.drawString(String.valueOf(finder.getAngularVelocity()), 0, 0);
			Thread.sleep(50);
		}
	}
}