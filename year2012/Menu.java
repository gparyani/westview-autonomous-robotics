package year2012;

import lejos.nxt.Button;
import lejos.nxt.LCD;

// a simple menu system
public class Menu
{
	private Menu() throws Exception
	{
		throw new Exception("Cannot create Menu instance!");
	}
	
	// shows a menu, and waits for the user to select an option
	public static int show(String title, String... options)
	{
		// draw title
		LCD.drawString(title, 0, 0);
		// and all the options
		for (int opt = 0; opt < options.length; opt++)
			LCD.drawString(options[opt], opt + 1, 1);

		int choice = 1;
		LCD.drawChar('>', 0, 1);
		
		while (true)
		{
			if (Button.RIGHT.isDown())
				// right -> select choice
				// choice is 1-based, so subtract 1 to make it 0-based
				return choice - 1;
			else if (Button.LEFT.isDown())
				// left -> exit/quit menu
				// -1 is error code
				return -1;
			else if (Button.ENTER.isDown())
			{
				// enter -> move pointer up
				// wraps around top to bottom
				int prev = choice;
				choice--;
				if (choice < 1)
					choice = options.length;
				move(prev, choice);
			}
			else if (Button.ESCAPE.isDown())
			{
				// escape -> move pointer down
				// wraps around bottom to top
				int prev = choice;
				choice++;
				if (choice > options.length)
					choice = 1;
				move(prev, choice);
			}
			
			try 
			{
				Thread.sleep(5);
			} 
			catch (InterruptedException e)
			{
			}
		}
	}
	// helper function to rewrite selection pointer
	private static void move(int prev, int cur)
	{
		LCD.drawChar(' ', 0, prev);
		LCD.drawChar('>', 0, cur);
	}
}
