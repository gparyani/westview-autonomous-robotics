package year2013;

import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class DragRace extends NXTApp
{
	boolean Started = false;
	
	DigitalSensorArray sensors;
	
	private final boolean STOPPING_AT_WALL = false;
	private long timeNearWall = -1;
	private final long TIME_UNTIL_STOP = 500; // ms
	
	public DragRace()
	{
		super(50);
		
		Motors.Initialize(SensorPort.S1);
		
		sensors = new DigitalSensorArray(SensorAddresses.B, SensorPort.S2, SensorPort.S3);
		
		Motors.Left.setReverse(true);
		Motors.Back.setReverse(true);
	}
	
	public void Update()
	{
		sensors.Update();

		LCD.clearDisplay();
		if (!Started)
		{
			LCD.drawString(NXTApp.splitString("Press enter to start the drag race, or press escape to exit."), 0, 0);

			if (Button.Enter.IsDown())
			{
				Motors.Left.forward();
				Motors.Right.forward();
				Motors.Front.stop();
				Motors.Back.stop();
				LCD.clearDisplay();
				Started = true;
			}
		}
		else
		{
			LCD.drawString("Press any button\nto end the race.", 0, 0);
			
			UpdateMotors();
			
			if (STOPPING_AT_WALL)
				if (timeNearWall == -1 && sensors.getData(DigitalSensorArray.FrontLeft) && sensors.getData(DigitalSensorArray.FrontRight))
					timeNearWall = System.currentTimeMillis();
		}
	}
	
	private void UpdateMotors()
	{
		if (sensors.getData(DigitalSensorArray.LeftFront) || sensors.getData(DigitalSensorArray.LeftBack))
		{
			// strafe right
			Motors.Front.backward();
			Motors.Back.backward();
		}
		else if (sensors.getData(DigitalSensorArray.RightFront) || sensors.getData(DigitalSensorArray.RightBack))
		{
			// strafe left
			Motors.Front.forward();
			Motors.Back.forward();
		}
		else
		{
			Motors.Front.setPower(0);//-15);
			Motors.Back.stop();
		}
	}
	
	public boolean ShouldExit()
	{
		if (Button.Escape.IsDown() || (STOPPING_AT_WALL && timeNearWall != -1 && System.currentTimeMillis() - timeNearWall >= TIME_UNTIL_STOP))
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
		new DragRace().Run();
	}
}
