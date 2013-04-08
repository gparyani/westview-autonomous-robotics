package year2013.NXTApp;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;

public class NXTMotor
{
	private static final String MotorsOffException = "Motors are off.\nPlease turn on\nthe motors and\nthen restart\nthis program.";
	
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
			this.showMotorsOffException();
		motor.setReverse(reverse);
	}
	public void forward()
	{
		if (!motorsInitialized)
			this.showMotorsOffException();
		motor.setPower(FULL_POWER);
		motor.forward();
	}
	public void backward()
	{
		if (!motorsInitialized)
			this.showMotorsOffException();
		motor.setPower(FULL_POWER);
		motor.backward();
	}
	public void stop()
	{
		if (!motorsInitialized)
			this.showMotorsOffException();
		motor.stop();
	}
	public void floatToStop()
	{
		if (!motorsInitialized)
			this.showMotorsOffException();
		motor.flt();
	}
	public void setPower(int power)
	{
		if (!motorsInitialized)
			this.showMotorsOffException();
		
		if (power > FULL_POWER) power = FULL_POWER;
		if (power < -FULL_POWER) power = -FULL_POWER;
		motor.setPower(Math.abs(power));
		
		if (power > 0) motor.forward();
		else if (power < 0) motor.backward();
		else motor.stop();
	}
	
	private void showMotorsOffException()
	{
		while (true)
		{
			LCD.clearDisplay();
			LCD.drawString(MotorsOffException, 0, 0);
			
			if (Button.LEFT.isDown() || Button.RIGHT.isDown() || Button.ENTER.isDown() || Button.ESCAPE.isDown())
				break;
			
			try { Thread.sleep(50); } catch (InterruptedException e1) { }
		}
	}
}
