package year2012;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.tetrix.TetrixControllerFactory;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;
import lejos.nxt.addon.tetrix.TetrixMotorController;

public class DragRace
{
	private static TetrixMotorController controller1, controller2;
	private static final int MOTOR_LEFT = 3,
							 MOTOR_RIGHT = 1,
							 MOTOR_FRONT = 0,
							 MOTOR_BACK = 2;
	
	private static TetrixEncoderMotor getMotor(int motorID)
	{
		TetrixMotorController controller = motorID < 2 ? controller1 : controller2;
		int motor = motorID % 2 == 0 ? TetrixMotorController.MOTOR_1 : TetrixMotorController.MOTOR_2;
		return controller.getEncoderMotor(motor);
	}
	
	public static void main(String[] args) throws Exception
	{		
		int choice = Menu.show("DragRace", "Run", "Quit");
		if (choice != 0)
			return;
		
		TetrixControllerFactory factory = new TetrixControllerFactory(SensorPort.S1);
		controller1 = factory.newMotorController();
		controller2 = factory.newMotorController();
		
		// move forward: LEFT backward, RIGHT forward
		getMotor(MOTOR_LEFT).backward();
		getMotor(MOTOR_RIGHT).forward();
		getMotor(MOTOR_FRONT).stop();
		getMotor(MOTOR_BACK).stop();
		
		Menu.show("DragRace", "Quit");
	}
}
