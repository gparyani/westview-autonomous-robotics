package year2012;

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
	}
}
