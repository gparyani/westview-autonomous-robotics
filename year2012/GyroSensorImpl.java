/**
 * 
 */
package year2012;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.GyroSensor;


/**
 * Implementation for Gyro Sensor
 * @author Gaurav Paryani
 *
 */
public class GyroSensorImpl
{
	private class GetDirection implements Runnable
	{
		@Override
		public void run()
		{
			while(true)
			{
				currentVelocity = sensor.getAngularVelocity();
			}
		}
	}
	
	private GyroSensor sensor;
	private float currentVelocity;
	private Thread updateVelocity;
	
	public GyroSensorImpl(SensorPort port)
	{
		sensor = new GyroSensor(port);
		sensor.recalibrateOffset();
		updateVelocity = new Thread(new GetDirection());
		updateVelocity.setDaemon(true);	//terminate this thread when all others have terminated
		updateVelocity.setName("GyroSensor Get Velocity");
		updateVelocity.start();
	}
	
	public float getAngularVelocity()
	{
		return currentVelocity;
	}
	
	public static void main(String... args)
	{
		GyroSensorImpl sensor = new GyroSensorImpl(SensorPort.S1);
		while(Button.ESCAPE.isUp())
		{
			LCD.clearDisplay();
			LCD.drawString(String.valueOf(sensor.getAngularVelocity()), 0, 0);
			try { Thread.sleep(50); }
			catch (InterruptedException e) { }
		}
	}
}
