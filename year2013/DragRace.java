package year2013;

import year2013.NXTApp.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class DragRace extends NXTApp
{
	DigitalSensor ShortRange;
	public DragRace() throws Exception
	{
		super(0);
		
		Motors.Initialize(SensorPort.S1);
		
		int choice = Menu.show("DragRace", "Run", "Quit");
		if (choice != 0)
			return;
		
		Motors.Left.forward();
		Motors.Right.backward();
		Motors.Front.stop();
		Motors.Back.stop();
		
		ShortRange = new DigitalSensor(SensorAddresses.B, 0, SensorAddresses.Superpro, SensorPort.S2);
	}
	
	protected void Update()
	{
		ShortRange.Update();
		if (ShortRange.GetData())
		{
			Motors.Left.stop();
			Motors.Right.stop();
			Motors.Front.stop();
			Motors.Back.stop();
		}
	}
	protected boolean ShouldExit()
	{
		return Button.Left.IsDown() || Button.Right.IsDown()
			|| Button.Enter.IsDown() || Button.Escape.IsDown();
	}
	
	public static void main(String[] args) throws Exception
	{
		new DragRace().Run();
	}
}
