package year2013.NXTApp;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.tetrix.TetrixControllerFactory;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;
import lejos.nxt.addon.tetrix.TetrixMotorController;

public class Motors
{
	private static final String MotorsOffException = "Motors are off.\nPlease turn on\nthe motors and\nthen press any\nbutton to retry.";
	
	private static boolean initialized;
	public static NXTMotor Front, Back, Left, Right;
	
	public static void Initialize(SensorPort motorPort)
	{
		try
		{
			if (initialized) return;
			
			TetrixControllerFactory factory = new TetrixControllerFactory(motorPort);
			TetrixMotorController c1 = factory.newMotorController(),
								  c2 = factory.newMotorController();
			// indices for the different motors--can change if the wires are redone
			int left = 3, right = 1, front = 0, back = 2;
			Front = new NXTMotor(getMotor(c1, c2, front));
			Back = new NXTMotor(getMotor(c1, c2, back));
			Left = new NXTMotor(getMotor(c1, c2, left));
			Right = new NXTMotor(getMotor(c1, c2, right));
			
			initialized = true;
		}
		catch (Exception e)
		{
			while (true)
			{
				LCD.clearDisplay();
				LCD.drawString(MotorsOffException, 0, 0);
				
				if (Button.LEFT.isDown() || Button.RIGHT.isDown() || Button.ENTER.isDown() || Button.ESCAPE.isDown())
					Initialize(motorPort);
				
				try { Thread.sleep(50); } catch (InterruptedException e1) { }
			}
		}
	}
	
	private static TetrixEncoderMotor getMotor(TetrixMotorController controller1, TetrixMotorController controller2, int motorID)
	{
		TetrixMotorController controller = motorID < 2 ? controller1 : controller2;
		int motor = motorID % 2 == 0 ? TetrixMotorController.MOTOR_1 : TetrixMotorController.MOTOR_2;
		return controller.getEncoderMotor(motor);
	}
}
