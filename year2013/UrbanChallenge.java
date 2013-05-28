package year2013;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;

public class UrbanChallenge extends NXTApp
{
	DigitalSensorArray shortRangeSensors;
	BeaconSensor beaconSensor;
	public UrbanChallenge()
	{
		super(50);
		
		shortRangeSensors = new DigitalSensorArray(SensorAddresses.B, SensorPort.S2);
		beaconSensor = new BeaconSensor(SensorPort.S3, BeaconSensor.AC);
		
		LCD.drawString("Press any button\nto start the\nUrban Challenge.\n\nMake sure that\nthe motors are\nturned on!", 0, 0);
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
		beaconSensor.Update();
		//LCD.drawInt(beaconSensor.getMostIntenseSignal(), 8, 3);
		
		if (bothFrontSensors())
			this.turnRight();
		else if (neitherLeftSensor())
			this.turnLeft();
		else this.straight();
		
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.FrontLeft) ? 't' : 'f', 4, 3);
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.FrontRight) ? 't' : 'f', 5, 3);
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.RightFront) ? 't' : 'f', 6, 3);
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.RightBack) ? 't' : 'f', 7, 3);
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.BackRight) ? 't' : 'f', 8, 3);
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.BackLeft) ? 't' : 'f', 9, 3);
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.LeftBack) ? 't' : 'f', 10, 3);
		LCD.drawChar(shortRangeSensors.getData(DigitalSensorArray.LeftFront) ? 't' : 'f', 11, 3);
	}
	
	private void straight()
	{
		Motors.Left.forward();
		Motors.Right.forward();
		Motors.Front.stop();
		Motors.Back.stop();
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
		return frontLeftSensorData() && frontRightSensorData();
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
		if (Button.Escape.IsDown() || beaconSensor.isCloseToBeacon())
		{
			Motors.Front.stop();
			Motors.Back.stop();
			Motors.Left.stop();
			Motors.Right.stop();
			return true;
		}
		return false;
	}

	public static void main(String[] args)
	{
		new UrbanChallenge().Run();
	}
}
