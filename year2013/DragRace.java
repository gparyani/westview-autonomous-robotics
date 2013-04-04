package year2013;

import year2013.NXTApp.*;
import year2013.NXTApp.Sensors.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class DragRace extends NXTApp
{
	boolean Started = false;
	boolean Exit = false;
	
	DigitalSensor ShortRange;
	DistanceSensor MediumRange;
	GyroSensor Gyro;
	
	public DragRace()
	{
		super(50);
		
		Motors.Initialize(SensorPort.S1);
		
		ShortRange = new DigitalSensor(SensorAddresses.B, 0, SensorAddresses.Superpro, SensorPort.S2);
		MediumRange = new DistanceSensor(SensorAddresses.A1, SensorAddresses.Superpro, SensorPort.S2);
		Gyro = new GyroSensor(SensorPort.S3);
	}
	
	protected void Update()
	{
		ShortRange.Update();
		MediumRange.Update();
		Gyro.Update();

		LCD.clearDisplay();
		LCD.drawString("Angle: " + Gyro.GetAngle(), 0, 6);
		if (!Started)
		{
			LCD.drawString("Press any button\nto start the\ndrag race.", 0, 0);
			Gyro.ResetAngle();

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
			
			LCD.drawString("Dist: " + DistanceSensor.GetDistance(MediumRange, ShortRange) + "cm", 0, 7);
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
		double angle = Gyro.GetAngle();
		Motors.Front.setPower((int)angle);
		Motors.Back.setPower((int)angle);
	}
	
	protected boolean ShouldExit()
	{
		return Exit;
	}
	
	public static void main(String[] args) throws Exception
	{
		new DragRace().Run();
	}
}
