package year2013;

import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class GoldRush extends NXTApp
{
	boolean Started = false;
	
	DigitalSensorArray shortRange;
	BeaconSensor beacon;
	final int NUM_SHORT_RANGE = 8;
	
	boolean wasHit;
	boolean backing, turning;
	final int 
		DELAY_TIME = 150, // time to keep moving forward after hitting something
		BACKING_TIME = 300, // time to back away after delaying after hitting something
		TURNING_TIME = 400, // time to turn after backing away
		SAFE_TIME = 500; // time to not go for the beacon after turning
	
	static long initialStartTime;
	final int INITIAL_WAIT_TIME = 3000;
	
	final int BEACON_MOTOR_POWER = 30;
	
	private long startTime;
	
	final double ANGLE_MULT = 10;
	
	public GoldRush()
	{
		super(1); // update time of 1 ms
		
		Motors.Initialize(SensorPort.S1);
		
		Motors.Left.setReverse(true);
		
		shortRange = new DigitalSensorArray(SensorAddresses.B, SensorPort.S2, SensorPort.S3);
		
		beacon = new BeaconSensor(SensorPort.S4, BeaconSensor.DC);
		
		wasHit = backing = turning = false;
	}
	
	public void Update()
	{
		shortRange.Update();

		LCD.clearDisplay();
		LCD.drawString("Was hit? " + (wasHit? "yes" : "no"), 0, 5);
		//LCD.drawString("Gryo Angle: " + gyro.GetAngle(), 0, 6);
		//LCD.drawString("IR Angle: " + beacon.getAngle(), 0, 7);
		
		if (!Started)
		{
			startMessage();
		}
		else
		{
			LCD.drawString("Press any button\nto end Gold Rush.", 0, 0);
			
			tick();
		}
	}
	void startMessage()
	{
		LCD.drawString("Press any button\nto start the\nGold Rush.", 0, 0);

		if (Button.Left.Pressed() || Button.Right.Pressed() 
				|| Button.Enter.Pressed() || Button.Escape.Pressed())
		{
			Motors.Left.backward();
			Motors.Right.forward();
			Motors.Front.stop();
			Motors.Back.stop();
			LCD.clearDisplay();
			Started = true;
			initialStartTime = System.currentTimeMillis();
		}
	}
	void tick()
	{
		if (colliding() && !wasHit && System.currentTimeMillis() - initialStartTime >= INITIAL_WAIT_TIME)
		{
			//System.out.println("just collided");
			// if we hit something, then:
			//   reset the timer
			//   mark that we are moving backwards
			startTime = System.currentTimeMillis();
			wasHit = true;
			backing = true;
			turning = false;
		}
		if (wasHit)
		{
			// perform evasion maneuvers!
			if (backing)
			{
				if (System.currentTimeMillis() - startTime < DELAY_TIME)
				{
					//System.out.println("was hit; delaying");
				}
				else
				{
					//System.out.println("was hit; backing up");
					if (System.currentTimeMillis() - startTime >= DELAY_TIME + BACKING_TIME)
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
			}
			if (turning)
			{
				//System.out.println("was hit; turning");
				if (System.currentTimeMillis() - startTime >= DELAY_TIME + BACKING_TIME + TURNING_TIME)
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
				//System.out.println("was hit; moving foward again");
				// third stage
				forward();
			}
			
			if (System.currentTimeMillis() - startTime >= DELAY_TIME + BACKING_TIME + TURNING_TIME + SAFE_TIME)
			{
				//System.out.println("was hit; going for beacon");
				// hit timer timed out; go for the beacon again after this.
				wasHit = false;
				backing = false;
				turning = false;
			}
		}
		else
		{
			System.out.println("going for beacon; angle=" + beacon.getAngle());
			// go for the beacon!
			aimAtBeacon();
			forward();
		}
	}
	// move forward!
	void forward()
	{
		if (Float.isNaN(beacon.getAngle()))
		{
//			Motors.Left.forward();
//			Motors.Right.forward();
			Motors.Left.setPower(100);
			Motors.Right.setPower(100);
		}
		else
		{
			Motors.Left.setPower(BEACON_MOTOR_POWER);
			Motors.Right.setPower(BEACON_MOTOR_POWER);
		}
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
		float angle = beacon.getAngle();
		if (angle == Float.NaN) return;
		Motors.Front.setPower((int)(angle * ANGLE_MULT));
		Motors.Back.setPower((int)(angle * ANGLE_MULT));
	}
	// is the robot colliding with anything?
	boolean colliding()
	{
		boolean[] data = new boolean[]
		{
			shortRange.getData(DigitalSensorArray.LeftFront),
			shortRange.getData(DigitalSensorArray.FrontLeft),
			shortRange.getData(DigitalSensorArray.FrontRight),
			shortRange.getData(DigitalSensorArray.RightFront)
		};
		for (int i = 0; i < data.length; i++)
			if (data[i])
				return true;
		return false;
	}
	
	public boolean ShouldExit()
	{
		if (Button.Left.Pressed() || Button.Right.Pressed() || Button.Escape.Pressed())
		{
			Motors.Left.stop();
			Motors.Right.stop();
			Motors.Front.stop();
			Motors.Back.stop();
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception
	{
		new GoldRush().Run();
	}
}
