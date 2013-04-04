package year2013;

import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class GoldRush extends NXTApp
{
	boolean Started = false;
	boolean Exit = false;
	
	DigitalSensor shortRange;
	GyroSensor gyro;
	lejos.nxt.addon.IRSeekerV2 beacon;
	
	private class UpdatingThread implements Runnable
	{
		private volatile long currentTime, originalTime;
		
		UpdatingThread()
		{
			currentTime = originalTime = System.currentTimeMillis();
			Thread toRun = new Thread(this);
			toRun.setDaemon(true);
			toRun.setName("Gold Rush Timer Thread");
			toRun.start();
		}
		
		@Override
		public void run()
		{
			while(true)
			{
					currentTime = System.currentTimeMillis();
			}
		}
		
		long getTime()
		{
			return currentTime - originalTime;
		}
	}
	
	public GoldRush()
	{
		super(50);
		
		Motors.Initialize(SensorPort.S1);
		
		shortRange = new DigitalSensor(SensorAddresses.B, 0, SensorAddresses.Superpro, SensorPort.S2);
		gyro = new GyroSensor(SensorPort.S3);
	}
	
	protected void Update()
	{
		shortRange.Update();
		MediumRange.Update();
		gyro.Update();

		LCD.clearDisplay();
		LCD.drawString("Angle: " + gyro.GetAngle(), 0, 6);
		if (!Started)
		{
			LCD.drawString("Press any button\nto start the\ndrag race.", 0, 0);
			gyro.ResetAngle();

			if (Button.Left.Pressed() || Button.Right.Pressed() 
					|| Button.Enter.Pressed() || Button.Escape.Pressed())
			{
				Motors.Left.backward();
				Motors.Right.forward();
				Motors.Front.stop();
				Motors.Back.stop();
				LCD.clearDisplay();
				Started = true;
			}
			
			LCD.drawString("Dist: " + DistanceSensor.GetDistance(MediumRange, shortRange) + "cm", 0, 7);
		}
		else
		{
			LCD.drawString("Press any button\nto end the race.", 0, 0);
			
			UpdateMotors();
			
			if (//DistanceSensor.GetDistance(MediumRange, ShortRange) < 20
					/*||*/ Button.Left.Pressed() || Button.Right.Pressed() 
					|| Button.Enter.Pressed() || Button.Escape.Pressed())
			{
				Motors.Left.stop();
				Motors.Right.stop();
				Motors.Front.stop();
				Motors.Back.stop();
				Exit = true;
			}
		}
	}
	
	private void UpdateMotors()
	{
		double angle = gyro.GetAngle();
		Motors.Front.setPower((int)angle);
		Motors.Back.setPower((int)angle);
	}
	
	protected boolean ShouldExit()
	{
		return Exit;
	}
	
	public static void main(String[] args) throws Exception
	{
		new GoldRush().Run();
	}
}
