package year2012;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.tetrix.TetrixControllerFactory;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;
import lejos.nxt.addon.tetrix.TetrixMotorController;

public class MotorTest
{
	public static void main(String[] args) throws Exception
	{
		TetrixControllerFactory factory = new TetrixControllerFactory(SensorPort.S1);
		TetrixMotorController controller = factory.newMotorController();
		TetrixEncoderMotor motor = controller.getEncoderMotor(TetrixMotorController.MOTOR_1);
		motor.forward();
		int motorMoving = 1;
		boolean motorForward = true;
		while (Button.ESCAPE.isUp())
		{
			LCD.clear();
			LCD.drawString("Motor " + motorMoving + (motor.isMoving() ? " " : " Not ")
					+ "Moving..", 0, 0);
			LCD.drawString(motorForward ? "Forward" : "Backward", 0, 1);
			
			if (Button.RIGHT.isDown())
			{
				motor.flt();
				if (motorMoving == 1)
				{
					motorMoving = 2;
					motor = controller.getEncoderMotor(TetrixMotorController.MOTOR_2);
					motorForward = true;
				}
				else if (motorMoving == 2)
				{
					motorMoving = 1;
					motor = controller.getEncoderMotor(TetrixMotorController.MOTOR_1);
				}
				motor.forward();
				motorForward = true;
			}
			if (Button.LEFT.isDown())
			{
				if (motorForward) motor.backward();
				else motor.forward();
				motorForward = !motorForward;
			}
			
			Thread.sleep(500);
		}
	}
}
