package year2012;
import lejos.nxt.addon.GyroSensor;
import lejos.nxt.SensorPort;

public class GyroSensorTest {
	
	public static void main(String... args) throws Exception
	{
		GyroSensor sensor = new GyroSensor(SensorPort.S3);
		while(true)
		{
			System.out.println(sensor.getAngularVelocity());
			Thread.sleep(50);
		}
	}

}