package year2013;

import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class GoldRush extends NXTApp
{
	boolean Started = false;
	boolean Exit = false;
	
	UpdatingThread timer;
	DigitalSensor[] shortRange;
	GyroSensor gyro;
	BeaconSensor beacon;
	final int NUM_SHORT_RANGE = 8;
	
	boolean wasHit;
	boolean backing, turning;
	final int HIT_WAIT	   = 2000,
			  BACKING_TIME = 1000,
			  TURNING_TIME = 500;
	
	private static class UpdatingThread implements Runnable
	{
		private volatile long currentTime, originalTime;
		private static int instances;
		private int instanceNumber;
		
		public UpdatingThread()
		{
			instanceNumber = ++instances;
			reset();
			Thread toRun = new Thread(this);
			toRun.setDaemon(true);
			toRun.setName("Gold Rush Timer Thread " + instanceNumber);
			toRun.start();
		}
		
		@Override
		public void run()
		{
			while (true)
				currentTime = System.currentTimeMillis();
		}
		
		public void reset()
		{
			currentTime = originalTime = System.currentTimeMillis();
		}
		
		public long getTime()
		{
			return currentTime - originalTime;
		}
		
		@Override
		public boolean equals(Object o)
		{
			return instanceNumber == ((UpdatingThread)o).instanceNumber;
		}
		
		@Override
		public int hashCode()
		{
			return instanceNumber;
		}
		
		@Override
		protected void finalize()
		{
			instances--;
		}
	}
	
	public GoldRush()
	{
		super(1); // update time of 1 ms
		
		Motors.Initialize(SensorPort.S1);
		
		Motors.Left.setReverse(true);
		
		shortRange = new DigitalSensor[NUM_SHORT_RANGE];
		for (int i = 0; i < NUM_SHORT_RANGE; i++)
		{
			shortRange[i] = new DigitalSensor(SensorAddresses.B, i, SensorPort.S2);
		}
		
		gyro = new GyroSensor(SensorPort.S3);
		beacon = new BeaconSensor(SensorPort.S4, BeaconSensor.DC);
		timer = new UpdatingThread();
		
		wasHit = backing = turning = false;
	}
	
	protected void Update()
	{
		for (int i = 0; i < NUM_SHORT_RANGE; i++)
			shortRange[i].Update();
		gyro.Update();

		LCD.clearDisplay();
		LCD.drawString("Gryo Angle: " + gyro.GetAngle(), 0, 6);
		LCD.drawString("IR Angle: " + beacon.getAngle(), 0, 7);
		
		if (!Started)
		{
			startMessage();
		}
		else
		{
			LCD.drawString("Press any button\nto end Gold Rush.", 0, 0);
			
			tick();
			
			if (Button.Left.Pressed() || Button.Right.Pressed() || 
				Button.Enter.Pressed() || Button.Escape.Pressed())
			{
				Motors.Left.stop();
				Motors.Right.stop();
				Motors.Front.stop();
				Motors.Back.stop();
				Exit = true;
			}
		}
	}
	void startMessage()
	{
		LCD.drawString("Press any button\nto start the\nGold Rush.", 0, 0);
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
	}
	void tick()
	{
		if (colliding())
		{
			// if we hit something, then:
			//   reset the timer
			//   mark that we are moving backwards
			timer.reset();
			wasHit = true;
			backing = true;
			turning = false;
		}
		if (wasHit)
		{
			// perform evasion maneuvers!
			if (backing)
			{
				if (timer.getTime() >= BACKING_TIME)
				{
					// go to next stage: turning
					backing = false;
					turning = true;
				}
				else
				{
					backwards();
				}
			}
			if (turning)
			{
				if (timer.getTime() >= BACKING_TIME + TURNING_TIME)
				{
					// go to next stage: forward again
					turning = false;
				}
				else
				{
					turn();
				}
			}
			if (!backing && !turning)// moving forward again
			{
				// third stage
				forward();
			}
			
			if (timer.getTime() >= HIT_WAIT)
			{
				// hit timer timed out; go for the beacon again after this.
				wasHit = false;
				backing = false;
				turning = false;
			}
		}
		else
		{
			// go for the beacon!
			aimAtBeacon();
			forward();
		}
	}
	// move forward!
	void forward()
	{
		Motors.Left.forward();
		Motors.Right.forward();
		Motors.Front.stop();
		Motors.Back.stop();
	}
	// move backwards!
	void backwards()
	{
		Motors.Left.backward();
		Motors.Right.backward();
		Motors.Front.stop();
		Motors.Back.stop();
	}
	// perform the same turn every time
	void turn()
	{
		turnLeft();
	}
	void turnLeft()
	{
		Motors.Left.stop();
		Motors.Right.stop();
		Motors.Front.forward();
		Motors.Back.forward();
	}
	void turnRight()
	{
		Motors.Left.stop();
		Motors.Right.stop();
		Motors.Front.backward();
		Motors.Back.backward();
	}
	// turns the robot to aim at a beacon
	void aimAtBeacon()
	{
		double angle = beacon.getAngle();
		if (angle == Double.NaN) return;
		Motors.Front.setPower((int)angle);
		Motors.Back.setPower((int)angle);
	}
	// is the robot colliding with anything?
	boolean colliding()
	{
		boolean[] data = shortRangeData();
		// TODO: don't use back sensors
		for (int i = 0; i < NUM_SHORT_RANGE; i++)
			if (data[i])
				return true;
		return false;
	}
	
	boolean[] shortRangeData()
	{
		boolean[] data = new boolean[NUM_SHORT_RANGE];
		for (int i = 0; i < NUM_SHORT_RANGE; i++)
			data[i] = shortRange[i].GetData();
		return data;
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