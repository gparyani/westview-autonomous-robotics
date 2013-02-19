package year2013;

import lejos.nxt.Button;
import lejos.nxt.I2CPort;
import lejos.nxt.I2CSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class HelloSensorMain extends I2CSensor {

	private static final int ADDR_A0 = 0x42;
	private static final int ADDR_A1 = 0x44;
	private static final int ADDR_B = 0x4C;
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
		HelloSensorMain sensors = new HelloSensorMain(SensorPort.S2);
		while (!Button.ESCAPE.isDown())
		{
			UpdateA0(sensors);
			UpdateA1(sensors);
			UpdateB(sensors);
			
			DisplayData();
			
			try
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	static int accum0 = 0, accum1 = 0;
	static boolean b0 = false;
	static void UpdateA0(HelloSensorMain sensors)
	{
		byte[] a0_buffer = new byte[2];
		
		sensors.getData(ADDR_A0, a0_buffer, 2);
		int inputData = (0xff & (int)a0_buffer[0]) * 4
				+ (0xff & (int)a0_buffer[1]);
		accum0 = accum0 / 2 + inputData;
	}
	static void UpdateA1(HelloSensorMain sensors)
	{
		byte[] a1_buffer = new byte[2];
		
		sensors.getData(ADDR_A1, a1_buffer, 2);
		int inputData = (0xff & (int)a1_buffer[0]) * 4
				+ (0xff & (int)a1_buffer[1]);
		accum1 = accum1 / 2 + inputData;
	}
	static void UpdateB(HelloSensorMain sensors)
	{
		byte[] shortRangeReadBuffer = new byte[1];

		sensors.getData(ADDR_B, shortRangeReadBuffer, 1);
		b0 = (shortRangeReadBuffer[0] & 0x01) == 0;
	}
	
	static void DisplayData()
	{
		LCD.clearDisplay();
		
		LCD.drawString("Accum0: " + accum0, 0, 0);
		LCD.drawString("Accum1: " + accum1, 0, 1);
		LCD.drawString("SR Read: " + b0, 0, 2);
	}
}