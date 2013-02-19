package year2012;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.tetrix.TetrixControllerFactory;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;
import lejos.nxt.addon.tetrix.TetrixMotorController;

public class MotorRotateTest
{
	private static TetrixMotorController controller1, controller2;
	private static final int MOTOR_LEFT = 3,
							 MOTOR_RIGHT = 1,
							 MOTOR_FRONT = 0,
							 MOTOR_BACK = 2;
	private static final int UPDATE_WAIT = 1; // ms
	
	private static TetrixEncoderMotor getMotor(int motorID)
	{
		TetrixMotorController controller = motorID < 2 ? controller1 : controller2;
		int motor = motorID % 2 == 0 ? TetrixMotorController.MOTOR_1 : TetrixMotorController.MOTOR_2;
		return controller.getEncoderMotor(motor);
	}
	
	public static void main(String[] args) throws Exception
	{		
		TetrixControllerFactory factory = new TetrixControllerFactory(SensorPort.S1);
		controller1 = factory.newMotorController();
		controller2 = factory.newMotorController();
		
		// left motor goes in wrong direction... set it straight!
		//getMotor(MOTOR_LEFT).setReverse(true);

		while (true)
		{
			// exit menu if both buttons are down
			if (Button.ENTER.isDown() && Button.ESCAPE.isDown())
			{
				int choice = Menu.show("Menu", "Quit", "Don't Quit");
				if (choice == 0)
					break;
				else
					continue;
			}
			
			if (Button.LEFT.isDown())
			{
				// rotate left: LEFT backward, RIGHT forward
				getMotor(MOTOR_LEFT).forward();
				getMotor(MOTOR_RIGHT).forward();
				getMotor(MOTOR_FRONT).forward();
				getMotor(MOTOR_BACK).forward();
			}
			else if (Button.RIGHT.isDown())
			{
				// rotate right: LEFT forward, RIGHT backward
				getMotor(MOTOR_LEFT).backward();
				getMotor(MOTOR_RIGHT).backward();
				getMotor(MOTOR_FRONT).backward();
				getMotor(MOTOR_BACK).backward();
			}
			else if (Button.ENTER.isDown())
			{
				// move forward: LEFT forward, RIGHT forward
				getMotor(MOTOR_LEFT).backward();
				getMotor(MOTOR_RIGHT).forward();
				getMotor(MOTOR_FRONT).stop();
				getMotor(MOTOR_BACK).stop();
			}
			else if (Button.ESCAPE.isDown())
			{
				// move backward: LEFT backward, RIGHT backward
				getMotor(MOTOR_LEFT).forward();
				getMotor(MOTOR_RIGHT).backward();
				getMotor(MOTOR_FRONT).stop();
				getMotor(MOTOR_BACK).stop();
			}
			else
			{
				// stop rotating
				getMotor(MOTOR_LEFT).stop();
				getMotor(MOTOR_RIGHT).stop();
				getMotor(MOTOR_FRONT).stop();
				getMotor(MOTOR_BACK).stop();
			}
			
			// wait for next loop
			Thread.sleep(UPDATE_WAIT);
		}
	}
}
