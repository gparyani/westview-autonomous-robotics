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
	
	private static TetrixEncoderMotor getMotor(int motorID)
	{
		return controller.getEncoderMotor(motorID);
	}
	
	public static void main(String[] args) throws Exception
	{
		TetrixControllerFactory factory = new TetrixControllerFactory(SensorPort.S1);
		controller = factory.newMotorController();

		while (Button.ESCAPE.isUp())
		{
			if (Button.LEFT.isDown())
			{
				getMotor(MOTOR_LEFT).backward();
				getMotor(MOTOR_RIGHT).forward();
			}
			else if (Button.RIGHT.isDown())
			{
				getMotor(MOTOR_LEFT).forward();
				getMotor(MOTOR_RIGHT).backward();
			}
			
			Thread.sleep(500);
		}
	}
}
