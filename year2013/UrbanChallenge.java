package year2013;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;

public class UrbanChallenge extends NXTApp
{
	DigitalSensorArray shortRangeSensors;
	public UrbanChallenge()
	{
		super(50);
		
		shortRangeSensors = new DigitalSensorArray(SensorAddresses.B, SensorPort.S2);
		
		LCD.drawString("Press any button\nto start the\nUrban Challenge.", 0, 0);
		Button.WaitForKeyPress();
		LCD.clearDisplay();
		
		Motors.Initialize(SensorPort.S1);
	}

	public void Update()
	{
		LCD.clearDisplay();
		switch (shortRangeSensors.getDirection())
		{
			case DigitalSensorArray.OriginalDir: LCD.drawChar('^', 8, 0); break;
			case DigitalSensorArray.CWFromOriginal: LCD.drawChar('>', 15, 4); break;
			case DigitalSensorArray.HalfCircleFromOriginal: LCD.drawChar('v', 8, 7); break;
			case DigitalSensorArray.CCWFromOriginal: LCD.drawChar('<', 0, 4); break;
		}
				
		shortRangeSensors.Update();
		
		if (bothFrontSensors())
			this.turnRight();
		else if (neitherLeftSensor())
			this.turnLeft();
	}
	
	private void turnRight()
	{
		this.rotateCW();
	}
	private void turnLeft()
	{
		final double MOVE_FORWARD_TIME = .1;
		
		this.rotateCCW();
		
		moveForwardForTime(MOVE_FORWARD_TIME);
		
		if (eitherLeftSensor())
			return;
		
		// now we know that it's a u-turn
		
		shortRangeSensors.rotateCounterClockwise();

		moveForwardForTime(MOVE_FORWARD_TIME);
		
		
		this.strafeLeft();
		{
			while (!bothLeftSensors())
				;
		}
		this.stopStrafeLeft();
	}
	
	private boolean bothLeftSensors()
	{
		return leftBackSensorData() && leftFrontSensorData();
	}
	private boolean eitherLeftSensor()
	{
		return leftBackSensorData() || leftFrontSensorData();
	}
	private boolean neitherLeftSensor()
	{
		return !eitherLeftSensor();
	}
	
	private boolean bothFrontSensors()
	{
		return shortRangeSensors.getData(DigitalSensorArray.FrontLeft)
			&& shortRangeSensors.getData(DigitalSensorArray.FrontRight);
	}
	
	private boolean leftBackSensorData()
	{ return shortRangeSensors.getData(DigitalSensorArray.LeftBack); }
	private boolean leftFrontSensorData()
	{ return shortRangeSensors.getData(DigitalSensorArray.LeftFront); }
	private boolean frontLeftSensorData()
	{ return shortRangeSensors.getData(DigitalSensorArray.FrontLeft); }
	private boolean frontRightSensorData()
	{ return shortRangeSensors.getData(DigitalSensorArray.FrontRight); }
	
	private void rotateCW()
	{
		shortRangeSensors.rotateClockwise();
		Motors.rotateClockwise();
	}
	private void rotateCCW()
	{
		shortRangeSensors.rotateCounterClockwise();
		Motors.rotateCounterClockwise();
	}
	
	private void moveForwardForTime(double seconds)
	{
		Motors.Left.forward();
		Motors.Right.forward();
		
		try { Thread.sleep((long)(seconds * 1000)); }
		catch (InterruptedException e) { }
		
		Motors.Left.stop();
		Motors.Right.stop();
	}
	private void strafeLeft()
	{
		Motors.Front.backward();
		Motors.Back.forward();
	}
	private void stopStrafeLeft()
	{
		Motors.Front.stop();
		Motors.Back.stop();
	}

	public boolean ShouldExit()
	{
		return Button.Left.IsDown() || Button.Right.IsDown() || Button.Enter.IsDown() || Button.Escape.IsDown();
	}

	public static void main(String[] args)
	{
		new UrbanChallenge().Run();
	}
}
