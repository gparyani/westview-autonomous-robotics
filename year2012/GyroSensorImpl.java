/**
 * 
 */
package year2012;

import java.util.ArrayList;

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
		try
		{
			Thread.sleep(5000);
		}
		catch(InterruptedException e){}
	}
	
	public float getAngularVelocity()
	{
		return currentVelocity;
	}
}
