package year2013;

import lejos.nxt.Button;
import lejos.nxt.I2CPort;
import lejos.nxt.I2CSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class HelloSensorMain extends I2CSensor {

	private static final int READ_BUFFER_ADDRESS = 0x42;
	private static final int SUPERPRO_ADDRESS = 0x10;

	public HelloSensorMain(I2CPort port)
	{
		super(port, SUPERPRO_ADDRESS, I2CPort.LEGO_MODE, TYPE_LOWSPEED_9V);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		HelloSensorMain hsm = new HelloSensorMain(SensorPort.S1);
		byte[] readBuffer = new byte[2];
		int accum = 0;
		while (!Button.ESCAPE.isDown())
		{
			try
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			hsm.getData(READ_BUFFER_ADDRESS, readBuffer, 2);
			int inputData = (0xff & (int) readBuffer[0]) * 4
					+ (0xff & (int) readBuffer[1]);
			accum = accum / 2 + inputData;
			LCD.clearDisplay();
			LCD.drawString("Input:   " + inputData, 0, 0);
			LCD.drawString("Read[0]: " + readBuffer[0], 0, 1);
			LCD.drawString("Read[1]: " + readBuffer[1], 0, 2);
			LCD.drawString("Accum/2: " + accum / 2, 0, 3);
		}
	}

}