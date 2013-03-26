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
	
	private static final int 
		ENTER = 0, 
		ESCAPE = 1, 
		LEFT = 2, 
		RIGHT = 3;
	private static boolean[] prevButtons;
	private static boolean[] buttons;
	
	// refreshes button arrays
	private static void getButtons()
	{
		buttons = new boolean[] {
				Button.ENTER.isDown(),
				Button.ESCAPE.isDown(),
				Button.LEFT.isDown(),
				Button.RIGHT.isDown()
		};
	}
	private static void loadButtons()
	{
		prevButtons = buttons;
		getButtons();
	}
	
	// was the specified button pressed in the last update?
	private static boolean wasPressed(int button)
	{
		return buttons[button] && !prevButtons[button];
	}
	// was the specified button released in the last update?
	private static boolean wasReleased(int button)
	{
		return prevButtons[button] && !buttons[button];
	}
	
	// shows a menu, and waits for the user to select an option
	public static int show(boolean preclear, boolean postclear, String title, String... options)
	{
		if (options.length > 7)
			throw new IllegalArgumentException("Only 7 options are allowed.");
		
		if (preclear) LCD.clear();
		
		// draw title
		LCD.drawString(title, 0, 0);
		// and all the options
		for (int opt = 0; opt < options.length; opt++)
			LCD.drawString(options[opt], 1, opt + 1);

		int choice = 1;
		LCD.drawChar('>', 0, 1);
		
		getButtons();
		
		while (true)
		{
			loadButtons();
			
			if (wasPressed(RIGHT))
			{
				if (postclear) LCD.clear();
				// right -> select choice
				// choice is 1-based, so subtract 1 to make it 0-based
				return choice - 1;
			}
			else if (wasPressed(LEFT))
			{
				if (postclear) LCD.clear();
				// left -> exit/quit menu
				// -1 is error code
				return -1;
			}
			else if (wasPressed(ENTER))
			{
				// enter -> move pointer up
				// wraps around top to bottom
				int prev = choice;
				choice--;
				if (choice < 1)
					choice = options.length;
				move(prev, choice);
			}
			else if (wasPressed(ESCAPE))
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
	
	public static int show(String title, String... options)
	{
		return Menu.show(true, true, title, options);
	}
}
