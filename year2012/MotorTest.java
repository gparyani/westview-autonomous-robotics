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
      
    boolean leftButtonDown = false, rightButtonDown = false, escapeButtonDown = false,
            prevLeftButtonDown = false, prevRightButtonDown = false, prevEscapeButtonDown = false;
		while (true)
		{
			LCD.clear();
			LCD.drawString("Motor " + motorMoving + (motor.isMoving() ? " " : " Not ")
					+ "Moving..", 0, 0);
			LCD.drawString(motorForward ? "Forward" : "Backward", 0, 1);
			
      boolean leftButtonPressed = leftButtonDown && !prevLeftButtonDown,
              rightButtonPressed = rightButtonDown && !prevRightButtonDown,
              escapeButtonPressed = escapeButtonDown && !prevEscapeButtonDown;
              
      if (escapeButtonPressed) break;
         
			if (leftButtonPressed)
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
			if (rightButtonPressed)
			{
				if (motorForward) motor.backward();
				else motor.forward();
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
