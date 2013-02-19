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
		TetrixEncoderMotor motor = controller
				.getEncoderMotor(TetrixMotorController.MOTOR_1);
		motor.forward();
		int motorMoving = 1;
		boolean motorForward = true;

		boolean leftButtonDown = false, rightButtonDown = false,
				escapeButtonDown = false, prevLeftButtonDown = false,
				prevRightButtonDown = false, prevEscapeButtonDown = false;
		while (true)
		{
			// Display:
			// Motor [1/2] [Not] Moving..
			// Forward/Backward
			LCD.clear();
			LCD.drawString("Motor " + motorMoving
					+ (motor.isMoving() ? " " : " Not ") + "Moving..", 0, 0);
			LCD.drawString(motorForward ? "Forward" : "Backward", 0, 1);

			boolean leftButtonPressed = leftButtonDown && !prevLeftButtonDown,
					rightButtonPressed = rightButtonDown && !prevRightButtonDown,
					escapeButtonPressed = escapeButtonDown && !prevEscapeButtonDown;

			// if the bottom button (below the orange one) is pressed, exit the
			// program.
			if (escapeButtonPressed) break;

			// if the left button is pressed, then switch which motor is moving.
			if (leftButtonPressed)
			{
				motor.stop();
				if (motorMoving == 1)
				{
					motorMoving = 2;
					motor = controller
							.getEncoderMotor(TetrixMotorController.MOTOR_2);
				}
				else if (motorMoving == 2)
				{
					motorMoving = 1;
					motor = controller
							.getEncoderMotor(TetrixMotorController.MOTOR_1);
				}
				motor.forward();
				motorForward = true;
			}
			// if the right button is pressed, reverse the current motor.
			if (rightButtonPressed)
			{
				if (motorForward)
					motor.backward();
				else
					motor.forward();
				motorForward = !motorForward;
			}

			prevLeftButtonDown = leftButtonDown;
			prevRightButtonDown = rightButtonDown;
			prevEscapeButtonDown = escapeButtonDown;

			Thread.sleep(50);

			leftButtonDown = Button.LEFT.isDown();
			rightButtonDown = Button.RIGHT.isDown();
			escapeButtonDown = Button.ESCAPE.isDown();
		}
	}
}
