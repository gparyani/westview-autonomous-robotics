package year2012;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Menu
{
	private Menu() throws Exception
	{
		throw new Exception("Cannot create Menu instance!");
	}
	
	public static int show(String title, String... options)
	{
		LCD.drawString(title, 0, 0);
		for (int opt = 0; opt < options.length; opt++)
			LCD.drawString(options[opt], opt + 1, 1);

		int choice = 1;
		LCD.drawChar('>', 0, 1);
		
		while (true)
		{
			if (Button.RIGHT.isDown())
				return choice - 1;
			else if (Button.LEFT.isDown())
				return -1;
			else if (Button.ENTER.isDown())
			{
				int prev = choice;
				choice--;
				if (choice < 1)
					choice = options.length;
				move(prev, choice);
			}
			else if (Button.ESCAPE.isDown())
			{
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
	private static void move(int prev, int cur)
	{
		LCD.drawChar(' ', 0, prev);
		LCD.drawChar('>', 0, cur);
	}
}
