package year2013;

import year2013.NXTApp.*;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

public class DragRace extends NXTApp
{
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
		
		Menu.show("DragRace", "Quit");
		
		Motors.Left.stop();
		Motors.Right.stop();
	}
	
	protected void Update() { }
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
