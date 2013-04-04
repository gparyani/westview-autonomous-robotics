package year2013.NXTApp;

import lejos.nxt.addon.tetrix.TetrixEncoderMotor;

public class NXTMotor
{
	public static final int FULL_POWER = 100;
	
	private TetrixEncoderMotor motor;
	
	public NXTMotor(TetrixEncoderMotor motor)
	{
		this.motor = motor;
	}
	
	public void setReverse(boolean reverse)
	{
		motor.setReverse(reverse);
	}
	public void forward()
	{
		motor.setPower(FULL_POWER);
		motor.forward();
	}
	public void backward()
	{
		motor.setPower(FULL_POWER);
		motor.backward();
	}
	public void stop()
	{
		motor.stop();
	}
	public void floatToStop()
	{
		motor.flt();
	}
	public void setPower(int power)
	{
		if (power > FULL_POWER) power = FULL_POWER;
		if (power < -FULL_POWER) power = -FULL_POWER;
		motor.setPower(Math.abs(power));
		
		if (power > 0) motor.forward();
		else if (power < 0) motor.backward();
		else motor.stop();
	}
}
