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
		else
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
		
		ShortRange.Update();
		MediumRange.Update();
		LCD.drawString("Medium-Range: " + MediumRange.GetVoltage(), 0, 7);
		// 70 is just an arbitrary voltage to stop at.
		if (Running && (ShortRange.GetData() || MediumRange.GetVoltage() > 70))
		{
			Motors.Left.stop();
			Motors.Right.stop();
			Motors.Front.stop();
			Motors.Back.stop();
			Exit = true;
		}
	}
	protected boolean ShouldExit()
	{
		return false;//Exit;
	}
	
	public static void main(String[] args) throws Exception
	{
		new DragRace().Run();
	}
}
