package year2013;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;
//import year2013.maze.*;

public class UrbanChallenge extends NXTApp
{
	final float POWER_FRACTION = .5f;
	final int MOTOR_POWER = (int)(NXTMotor.FULL_POWER * POWER_FRACTION);
	
	DigitalSensorArray shortRangeSensors;
	BeaconSensor beaconSensor;
//	Maze maze;
	int numTurns = 0;
	boolean useAlg1;
	
	public UrbanChallenge()
	{
		super(50);
		
		shortRangeSensors = new DigitalSensorArray(SensorAddresses.B, SensorPort.S2, SensorPort.S3);
		beaconSensor = new BeaconSensor(SensorPort.S3, BeaconSensor.AC);
//		maze = new Maze();
		
//		LCD.drawString("Press any button\nto start the\nUrban Challenge.\n\nMake sure that\nthe motors are\nturned on!", 0, 0);
//		Button.WaitForKeyPress();
//		LCD.clearDisplay();
		
		System.out.println("Please select\nyour algorithm:\nLeft for Posner\nRight for Gaurav");
		System.out.println("Back to Exit");
		boolean validButtonPressed = true;
		do
		{
			switch(lejos.nxt.Button.waitForAnyPress())
			{
				case 1:
					validButtonPressed = false;
					break;
				case 2:
					useAlg1 = validButtonPressed = true;
					break;
				case 4:
					useAlg1 = false;
					validButtonPressed = true;
					break;
				case 8:
					System.exit(1);
			}
		}
		while(!validButtonPressed);
		
		Motors.Initialize(SensorPort.S1);
		
		setMotorDirections();
	}

	void printDirection()
	{
		switch (shortRangeSensors.getDirection())
		{
//			case DigitalSensorArray.OriginalDir: LCD.drawChar('^', 8, 0); break;
//			case DigitalSensorArray.CWFromOriginal: LCD.drawChar('>', 15, 4); break;
//			case DigitalSensorArray.HalfCircleFromOriginal: LCD.drawChar('v', 8, 7); break;
//			case DigitalSensorArray.CCWFromOriginal: LCD.drawChar('<', 0, 4); break;
			case DigitalSensorArray.OriginalDir: System.out.println("^"); break;
			case DigitalSensorArray.CWFromOriginal: System.out.println(">"); break;
			case DigitalSensorArray.HalfCircleFromOriginal: System.out.println("v"); break;
			case DigitalSensorArray.CCWFromOriginal: System.out.println("<"); break;
		}
	}
	void setMotorDirections()
	{
		Motors.Front.setReverse(false);
		Motors.Right.setReverse(false);
		Motors.Left.setReverse(true);
		Motors.Back.setReverse(true);
	}
	
	public void Update()
	{
//		LCD.clearDisplay();
		printDirection();
//		LCD.drawString("# Turns: " + numTurns, 2, 3);
				
		shortRangeSensors.Update();
		beaconSensor.Update();
		//LCD.drawInt(beaconSensor.getMostIntenseSignal(), 8, 3);
		
		if(useAlg1)
			algorithm1();
		else
			algorithm2();
		
//		displaySensorValues();
	}
	/**
	 * Implements Grant and Jacob's left-wall-following algorithm.
	 */
	private void algorithm1()
	{
		if (bothFrontSensors())
			turnRight();
		else if (neitherLeftSensor())
			turnLeft();
		else
			straight();
	}
	
	/**
	 * Implements Gaurav's algorithm.
	 */
	private void algorithm2()
	{
		//Gaurav's algorithm:
		if(bothFrontSensors())	//wait until front sensor is on
		{
			if(!bothLeftSensors() && !bothRightSensors())	//if both left and right sensors are off
			{
//				if(Math.random() < 0.5)	//decide randomly whether to turn left or right
//					turnLeft();
//				else
//					turnRight();
				turnLeft(); // let's just turn left, instead of being random.
			}
			else if(bothLeftSensors())	//if only right sensor is off
				turnRight();	//go right
			else if(bothRightSensors())	//if only left sensor is off
				turnLeft();
			else if (!(bothLeftSensors() || bothRightSensors() || bothFrontSensors()))
				// four-way intersection ==> always pick the same direction (I choose Left)
				turnLeft();
			else
			{
				// dead end ==> turn around
				turnRight();
				turnRight();
			}
		}
	}
	
	/**
	 * Displays the current sensor values on the NXT display.
	 */
	private void displaySensorValues()
	{
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
		Motors.Left.forward(MOTOR_POWER);
		Motors.Right.forward(MOTOR_POWER);
		Motors.Front.stop();
		Motors.Back.stop();
	}
	private void turnRight()
	{
		Motors.stopAllMotors();
		this.rotateCW();
		System.out.println("Turned right.");
		printDirection();
	}
	private void turnLeft()
	{
		final double TIME_BEFORE_STOP = .25;
		final double MOVE_FORWARD_TIME = .75;
		final double UTURN_MOVE_FORWARD_TIME = .25;
		
		this.wait(TIME_BEFORE_STOP);
		Motors.stopAllMotors();
		System.out.println("Turned left.");
		this.rotateCCW();
		printDirection();
		
		System.out.println("Moving forward.");
		moveForwardForTime(MOVE_FORWARD_TIME);
		
		System.out.println("Left Sensors: " + (eitherLeftSensor() ? "t" : "f"));
		if (eitherLeftSensor())
			return;
		
		// now we know that it's a u-turn
		
		System.out.println("U-Turn!");
		
		moveForwardForTime(UTURN_MOVE_FORWARD_TIME);
		System.out.println("Turning left.");
		this.rotateCCW();
		printDirection();

		System.out.println("Moving forward.");
		moveForwardForTime(MOVE_FORWARD_TIME);
		
//		System.out.println("Strafing left.");
//		this.strafeLeft();
//		{
//			while (!bothLeftSensors())
//			{
//				System.out.println("LF: " + (leftFrontSensorData() ? 'T' : 'F') +  ", LB: " + (leftBackSensorData() ? 'T' : 'F'));
//				shortRangeSensors.Update();
//			}
//		}
//		this.stopStrafeLeft();
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
	
	private boolean bothRightSensors()
	{
		return rightFrontSensorData() && rightBackSensorData();
	}
	
	private boolean leftBackSensorData()
	{
		return shortRangeSensors.getData(DigitalSensorArray.LeftBack);
	}
	
	private boolean leftFrontSensorData()
	{
		return shortRangeSensors.getData(DigitalSensorArray.LeftFront);
	}
	
	private boolean frontLeftSensorData()
	{
		return shortRangeSensors.getData(DigitalSensorArray.FrontLeft);
	}
	
	private boolean frontRightSensorData()
	{
		return shortRangeSensors.getData(DigitalSensorArray.FrontRight);
	}
	
	private boolean rightFrontSensorData()
	{
		return shortRangeSensors.getData(DigitalSensorArray.RightFront);
	}
	
	private boolean rightBackSensorData()
	{
		return shortRangeSensors.getData(DigitalSensorArray.RightBack);
	}
	
	private void rotateCW()
	{
		shortRangeSensors.rotateClockwise();
		Motors.rotateClockwise();
//		maze.move(Movement.TurnRight);
		setMotorDirections();
	}
	private void rotateCCW()
	{
		shortRangeSensors.rotateCounterClockwise();
		Motors.rotateCounterClockwise();
//		maze.move(Movement.TurnLeft);
		setMotorDirections();
	}
	
	private void moveForwardForTime(double seconds)
	{
		Motors.Left.forward(MOTOR_POWER);
		Motors.Right.forward(MOTOR_POWER);
		
		try { Thread.sleep((long)(seconds * 1000)); }
		catch (InterruptedException e) { }
		
		Motors.Left.stop();
		Motors.Right.stop();
	}
	private void wait(double seconds)
	{
		try { Thread.sleep((long)(seconds * 1000)); }
		catch (InterruptedException e) { }
	}
//	private void strafeLeft()
//	{
//		Motors.Front.forward(MOTOR_POWER);
//		Motors.Back.forward(MOTOR_POWER);
//	}
//	private void stopStrafeLeft()
//	{
//		Motors.Front.stop();
//		Motors.Back.stop();
//	}

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
