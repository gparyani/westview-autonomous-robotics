package year2013.NXTApp;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;

public class NXTMotor
{
	private static final String MotorsOffException = NXTApp.splitString(
			"Motors are off. Please turn on the motors and then restart this program.");
	
	public static final int FULL_POWER = 100;
	
	private TetrixEncoderMotor motor;
	private static boolean motorsInitialized;
	
	public NXTMotor(TetrixEncoderMotor motor, boolean motorsInitialized)
	{
		this.motor = motor;
		NXTMotor.motorsInitialized = motorsInitialized;
	}
	
	public void setReverse(boolean reverse)
	{
		if (!motorsInitialized)
		{
			this.showMotorsOffException();
			return;
		}
		motor.setReverse(reverse);
	}
	public void forward()
	{
		if (!motorsInitialized)
		{
			this.showMotorsOffException();
			return;
		}
		motor.setPower(FULL_POWER);
		motor.forward();
	}
	public void forward(int power)
	{
		power = Math.abs(power);
		if (power > FULL_POWER)
			power = FULL_POWER;
		
		this.setPower(power);
	}
	public void backward()
	{
		if (!motorsInitialized)
		{
			this.showMotorsOffException();
			return;
		}
		motor.setPower(FULL_POWER);
		motor.backward();
	}
	public void backward(int power)
	{
		power = Math.abs(power);
		if (power > FULL_POWER)
			power = FULL_POWER;
		
		this.setPower(-power);
	}
	public void stop()
	{
		if (!motorsInitialized)
		{
			this.showMotorsOffException();
			return;
		}
		motor.stop();
	}
	public void floatToStop()
	{
		if (!motorsInitialized)
		{
			this.showMotorsOffException();
			return;
		}
		motor.flt();
	}
	public void setPower(int power)
	{
		if (!motorsInitialized)
		{
			this.showMotorsOffException();
			return;
		}
		
		if (power > FULL_POWER) power = FULL_POWER;
		if (power < -FULL_POWER) power = -FULL_POWER;
		motor.setPower(Math.abs(power));
		
		if (power > 0) motor.forward();
		else if (power < 0) motor.backward();
		else motor.stop();
	}
	
	private void showMotorsOffException()
	{
		LCD.clearDisplay();
		LCD.drawString(MotorsOffException, 0, 0);
		while (true)
			if (Button.LEFT.isDown() || Button.RIGHT.isDown() || Button.ENTER.isDown() || Button.ESCAPE.isDown())
				break;
	}
	
	private static final double WHEEL_RADIUS = 5; // cm
	public double getDistanceTraveled()
	{
		return WHEEL_RADIUS * motor.getTachoCount() * Math.PI / 180;
	}
	public void resetDistanceTraveled()
	{
		motor.resetTachoCount();
	}
}
