package year2013.NXTApp.Sensors;

import lejos.nxt.SensorPort;

public class BeaconSensor extends Sensor
{
	public final boolean
		AC = true,
		DC = false;
	private final byte
		//DC_DIR_ADDRESS = 0x42,
		AC_DIR_ADDRESS = 0x49,
		//DC_SIGNALS_ADDRESS = 0x43,
		AC_SIGNALS_ADDRESS = 0x4A;
	
	byte[] ac_signals;
	byte[] dc_signals;
	byte ac_dir;
	//byte dc_dir;
	
	public BeaconSensor(int readBufferAddress, int superproAddress, SensorPort port)
	{
		super(readBufferAddress, 0x4E, superproAddress, port);
		
		ac_signals = new byte[5];
		//dc_signals = new byte[5];
	}

	@Override
	protected void UpdateData()
	{
		byte[] buffer = this.GetReadBuffer();
		//dc_dir = buffer[DC_DIR_ADDRESS];
		ac_dir = buffer[AC_DIR_ADDRESS];
		for (int i = 0; i < 5; i++)
		{
			//dc_signals[i] = buffer[DC_SIGNALS_ADDRESS + i];
			ac_signals[i] = buffer[AC_SIGNALS_ADDRESS + i];
		}
	}
	
	public int getDirection()
	{
		return ac_dir - 5;
	}
}
