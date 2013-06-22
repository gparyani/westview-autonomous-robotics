package year2013.Tests;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.Motors;
import year2013.NXTApp.NXTApp;
import year2013.NXTApp.Sensors.DigitalSensorArray;
import year2013.NXTApp.Sensors.SensorAddresses;

public class RobotRotateTest extends NXTApp
{
	DigitalSensorArray sensors;
	
	public RobotRotateTest()
	{
		super(50);
		
		Motors.Initialize(SensorPort.S1);
		sensors = new DigitalSensorArray(SensorAddresses.B, SensorPort.S2, SensorPort.S3);
		setMotorDirections();
	}

	protected void Update()
	{	
		sensors.Update();
		
		if (Button.Left.Pressed())
		{
			sensors.rotateCounterClockwise();
			Motors.rotateCounterClockwise();
			setMotorDirections();
		}
		else if (Button.Right.Pressed())
		{
			sensors.rotateClockwise();
			Motors.rotateClockwise();
			setMotorDirections();
		}
		
		Motors.Left.setPower(100);
		Motors.Right.setPower(100);
		Motors.Front.setPower(0);
		Motors.Back.setPower(0);
		
		LCD.clearDisplay();
		LCD.drawChar(sensors.getData(DigitalSensorArray.FrontLeft) ? 't' : 'f',  1, 0);
		LCD.drawChar(sensors.getData(DigitalSensorArray.FrontRight) ? 't' : 'f', 14, 0);
		LCD.drawChar(sensors.getData(DigitalSensorArray.RightFront) ? 't' : 'f', 15, 1);
		LCD.drawChar(sensors.getData(DigitalSensorArray.RightBack) ? 't' : 'f',  15, 6);
		LCD.drawChar(sensors.getData(DigitalSensorArray.LeftFront) ? 't' : 'f',  0, 1);
		LCD.drawChar(sensors.getData(DigitalSensorArray.LeftBack) ? 't' : 'f',   0, 6);
		LCD.drawChar(sensors.getData(DigitalSensorArray.BackLeft) ? 't' : 'f',   1, 7);
		LCD.drawChar(sensors.getData(DigitalSensorArray.BackRight) ? 't' : 'f',  14, 7);
		
		switch (sensors.getDirection())
		{
		case DigitalSensorArray.OriginalDir: LCD.drawChar('^', 8, 3); break;
		case DigitalSensorArray.CWFromOriginal: LCD.drawChar('>', 8, 3); break;
		case DigitalSensorArray.CCWFromOriginal: LCD.drawChar('<', 8, 3); break;
		case DigitalSensorArray.HalfCircleFromOriginal: LCD.drawChar('v', 8, 3); break;
		}
	}
	void setMotorDirections()
	{
//		boolean f = false, b = false, l = false, r = false;
//		switch (sensors.getDirection())
//		{
//		case DigitalSensorArray.OriginalDir: l = b = true; break;
//		case DigitalSensorArray.CCWFromOriginal: l = b = true; break;
//		case DigitalSensorArray.CWFromOriginal: l = b = true; break;
//		case DigitalSensorArray.HalfCircleFromOriginal: break;
//		}
//		Motors.Front.setReverse(f);
//		Motors.Right.setReverse(r);
//		Motors.Left.setReverse(l);
//		Motors.Back.setReverse(b);
		Motors.Front.setReverse(false);
		Motors.Right.setReverse(false);
		Motors.Left.setReverse(true);
		Motors.Back.setReverse(true);
	}

	protected boolean ShouldExit()
	{
		if (Button.Escape.IsDown() || Button.Enter.IsDown())
		{
			Motors.stopAllMotors();
			return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		new RobotRotateTest().Run();
	}
}
