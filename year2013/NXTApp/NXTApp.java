package year2013.NXTApp;

import java.util.ArrayList;
import java.util.List;

public abstract class NXTApp
{
	private int UpdateInterval;
	protected static class Button
	{
		public static NXTButton Left, Right, Enter, Escape;
		
		public static void WaitForKeyPress()
		{
			while (true)
			{
				updateButtons();
				if (Left.Pressed() || Right.Pressed() || Enter.Pressed() || Escape.Pressed())
					return;
			}
		}
		
		public static boolean anyAreDown()
		{
			return Left.IsDown() || Right.IsDown() || Enter.IsDown() || Escape.IsDown();
		}
		public static boolean anyAreUp()
		{
			return Left.IsUp() || Right.IsUp() || Enter.IsUp() || Escape.IsUp();
		}
		public static boolean anyWerePressed()
		{
			return Left.Pressed() || Right.Pressed() || Enter.Pressed() || Escape.Pressed();
		}
		public static boolean anyWereReleased()
		{
			return Left.Released() || Right.Released() || Enter.Released() || Escape.Released();
		}
	}
	
	protected NXTApp(int updateInterval)
	{
		this.UpdateInterval = updateInterval;
		
		Button.Left = new NXTButton(lejos.nxt.Button.LEFT.isDown());
		Button.Right = new NXTButton(lejos.nxt.Button.RIGHT.isDown());
		Button.Enter = new NXTButton(lejos.nxt.Button.ENTER.isDown());
		Button.Escape = new NXTButton(lejos.nxt.Button.ESCAPE.isDown());
	}
	
	protected void Run()
	{
		while (!ShouldExit())
		{
			Update();
			
			try
			{
				Thread.sleep(UpdateInterval);
			}
			catch (InterruptedException e) { }
			
			updateButtons();
		}
	}

	private static void updateButtons()
	{
		Button.Left.Update(lejos.nxt.Button.LEFT.isDown());
		Button.Right.Update(lejos.nxt.Button.RIGHT.isDown());
		Button.Enter.Update(lejos.nxt.Button.ENTER.isDown());
		Button.Escape.Update(lejos.nxt.Button.ESCAPE.isDown());
	}
	
	private static String[] splitSpaces(String strToSplit)
	{
		List<String> parts = new ArrayList<String>();
		
		String part = "";
		for (char c : strToSplit.toCharArray())
		{
			if (c != ' ')
			{
				part += c;
			}
			else
			{
				if (part.length() > 0)
				{
					parts.add(part);
					part = "";
				}
			}
		}
		if (part.length() > 0)
			parts.add(part);
		
		String[] result = new String[parts.size()];
		for (int i = 0; i < parts.size(); i++)
			result[i] = parts.get(i);
		return result;
	}
	// note: none of the words may be more than 16 characters long.
	public static String splitString(String str)
	{
		String[] words = splitSpaces(str.replace('\n', ' '));
		int currentLine = 0;
		String result = "";
		boolean firstWordOnLine = true;
		for (String word : words)
		{
			if (firstWordOnLine)
			{
				result += word;
				currentLine += word.length();
				firstWordOnLine = false;
			}
			else
			{
				if (currentLine + 1 + word.length() <= 16)
				{
					result += " " + word;
					currentLine += 1 + word.length();
				}
				else
				{
					result += "\n" + word;
					currentLine = word.length();
				}
			}
		}
		return result;
	}
	
	protected abstract void Update();
	protected abstract boolean ShouldExit();
}