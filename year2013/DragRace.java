package year2013;

import year2013.NXTApp.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class DragRace extends NXTApp
{
	boolean Started = false;
	boolean Exit = false;
	
	public DragRace()
	{
		super(0);
		
		Motors.Initialize(SensorPort.S1);
	}
	
	protected void Update()
	{
		if (!Started)
		{
			LCD.drawString("Press any button\nto start the\ndrag race.", 0, 0);

			if (Button.Left.Pressed() || Button.Right.Pressed() 
					|| Button.Enter.Pressed() || Button.Escape.Pressed())
			{
				Motors.Left.backward();
				Motors.Right.forward();
				Motors.Front.stop();
				Motors.Back.stop();
				LCD.clearDisplay();
				Started = true;
			}
		}
		else
		{
			LCD.drawString("Press any button\nto end the race.", 0, 0);
			if (Button.Left.Pressed() || Button.Right.Pressed() 
					|| Button.Enter.Pressed() || Button.Escape.Pressed())
			{
				Motors.Left.stop();
				Motors.Right.stop();
				Motors.Front.stop();
				Motors.Back.stop();
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
