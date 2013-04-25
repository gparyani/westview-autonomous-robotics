package year2013.Tests;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import year2013.NXTApp.*;

public class MotorPowerTest extends NXTApp
{
	private int PowerPercent;
	
	public MotorPowerTest()
	{
		super(50);
		PowerPercent = 0;
		
		Motors.Initialize(SensorPort.S1);
		
		Motors.Front.forward();
		Motors.Back.forward();
		Motors.Left.forward();
		Motors.Right.forward();
	}

	public void Update()
	{
		LCD.clearDisplay();
		LCD.drawString("% Power: " + this.PowerPercent, 0, 0);
		
		if (Button.Left.IsDown())
			this.PowerPercent--;
		if (Button.Right.IsDown())
			this.PowerPercent++;
		if (this.PowerPercent < 0)
			this.PowerPercent = 0;
		if (this.PowerPercent > 100)
			this.PowerPercent = 100;
		
		Motors.Front.setPower(this.PowerPercent);
		Motors.Back.setPower(this.PowerPercent);
		Motors.Left.setPower(this.PowerPercent);
		Motors.Right.setPower(this.PowerPercent);
	}
	public boolean ShouldExit()
	{
		return Button.Escape.IsDown();
	}
	
	
	public static void main(String[] args)
	{
		new MotorPowerTest().Run();
	}
}
