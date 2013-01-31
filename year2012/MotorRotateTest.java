package year2012;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.tetrix.TetrixControllerFactory;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;
import lejos.nxt.addon.tetrix.TetrixMotorController;

public class MotorRotateTest
{
	private static TetrixMotorController controller;
	private static final int 
		MOTOR_LEFT = TetrixMotorController.MOTOR_1,
		MOTOR_RIGHT = TetrixMotorController.MOTOR_2;
	private static final int
		UPDATE_WAIT = 25; // ms
	
	private static TetrixEncoderMotor getMotor(int motorID)
	{
		return controller.getEncoderMotor(motorID);
	}
	
	public static void main(String[] args) throws Exception
	{
		LCD.drawString("Hello, NXT!", 5, 5);
		
		TetrixControllerFactory factory = new TetrixControllerFactory(SensorPort.S1);
		controller = factory.newMotorController();
		
		// right motor goes in wrong direction...
		getMotor(MOTOR_RIGHT).setReverse(true);

		// until ESCAPE button is pressed...
		while (Button.ESCAPE.isUp())
		{
			if (Button.LEFT.isDown())
			{
				// rotate left: LEFT backward, RIGHT forward
				getMotor(MOTOR_LEFT).backward();
				getMotor(MOTOR_RIGHT).forward();
			}
			else if (Button.RIGHT.isDown())
			{
				// rotate right: LEFT forward, RIGHT backward
				getMotor(MOTOR_LEFT).forward();
				getMotor(MOTOR_RIGHT).backward();
			}
			
			// wait for next loop
			Thread.sleep(UPDATE_WAIT);
		}
	}
}
