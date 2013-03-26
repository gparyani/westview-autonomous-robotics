package year2012;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.GyroSensor;


/**
 * Implementation for Gyro Sensor
 * This implementation uses a thread that constantly updates the velocity coming from the specified sensor. This is to make sure that the update method is called the required amount of times per second to make sure the results are accurate
 * @author Gaurav Paryani
 * @see lejos.nxt.addon.GyroSensor#getAngularVelocity()
 */
public class GyroSensorImpl
{
	/**
	 * Thread that keeps updating the direction.
	 * @author Gaurav Paryani
	 *
	 */
	private class GetDirection implements Runnable
	{
		@Override
		public void run()
		{
			while(true)
			{
				synchronized(this)
				{
					currentVelocity = sensor.getAngularVelocity();
				}
			}
		}
	}
	
	private GyroSensor sensor;
	private volatile float currentVelocity;
	private Thread updateVelocity;
	
	/**
	 * Constructs a new GyroSensorImpl instance. This takes 5 seconds to calibrate the sensor.
	 * @param port the port to be used
	 */
	public GyroSensorImpl(SensorPort port)
	{
		sensor = new GyroSensor(port);
		sensor.recalibrateOffsetAlt();
		updateVelocity = new Thread(new GetDirection());
		updateVelocity.setDaemon(true);	//terminate this thread when all others have terminated
		updateVelocity.setName("GyroSensor Get Velocity");
		updateVelocity.start();
	}
	/**
	 * Gets the current angular velocity from the updating thread.
	 * @return the current angular velocity from the updating thread
	 */
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
