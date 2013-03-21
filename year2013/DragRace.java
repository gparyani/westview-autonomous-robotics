package year2013;

import year2013.NXTApp.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class DragRace extends NXTApp
{
	DigitalSensor ShortRange;
	DistanceSensor MediumRange;
	boolean Exit = false;
	boolean Running = false;
	boolean WaitingToExit = false;
	
	public DragRace()
	{
		super(0);
		
		Motors.Initialize(SensorPort.S1);
		
		ShortRange = new DigitalSensor(SensorAddresses.B, 0, SensorAddresses.Superpro, SensorPort.S2);
		MediumRange = new DistanceSensor(SensorAddresses.A1, SensorAddresses.Superpro, SensorPort.S2);
	}
	
	protected void Update()
	{
		if (Running)
			LCD.drawString("Drag-Race\nin Progress.", 0, 0);
		else if (!WaitingToExit && !Exit)
		{
			LCD.drawString("Press any button\nto start the\ndrag-race.", 0, 0);

			if (Button.Left.IsDown() || Button.Right.IsDown() 
					|| Button.Enter.IsDown() || Button.Escape.IsDown())
			{
				LCD.drawString("Started running...", 0, 2);
				Running = true;
				Motors.Left.backward();
				Motors.Right.forward();
				Motors.Front.stop();
				Motors.Back.stop();
			}
		}
		
		
		ShortRange.Update();
		MediumRange.Update();
		LCD.drawString("Short-Range: " + ((Boolean)ShortRange.GetData()).toString().charAt(0), 0, 6);
		LCD.drawString("Medium-Range: " + MediumRange.GetVoltage(), 0, 7);
		if (Running && ShortRange.GetData())
		{
			Motors.Left.stop();
			Motors.Right.stop();
			Motors.Front.stop();
			Motors.Back.stop();
			WaitingToExit = true;
			Running = false;
		}
		
		if (WaitingToExit)
		{
			LCD.drawString("Press any button\nto exit.", 0, 0);
			if (Button.Left.IsDown() || Button.Right.IsDown() 
					|| Button.Enter.IsDown() || Button.Escape.IsDown())
			{
				WaitingToExit = false;
				Exit = true;
			}
		}
	}
	protected boolean ShouldExit()
	{
		return Exit;
	}
	
	public static void main(String[] args) throws Exception
	{
		new DragRace().Run();
	}
}
