/**
 * 
 */
package year2013.NXTApp;

import lejos.nxt.LCD;

/**
 * Extended version of the NXTApp class. My fellow programmers disagreed about the implementation of the {@link NXTApp} class, so I reverted it and put my changes into a different file.
 * @author Gaurav Paryani
 * @see NXTApp
 */
public abstract class NXTApp2 {

	private int updateInterval;
	private int instanceCode;
	private AppThread runningThread;
	private static int instances = 1;
	/**
	 * Synchronize on this field to make sure that there are no other NXTApps that run while you run. This could be used to, say, get user input.
	 */
	protected static final Object lock;
	protected static class Button
	{
		public static NXTButton Left, Right, Enter, Escape;
	}
	
	static
	{
		Button.Left = new NXTButton(lejos.nxt.Button.LEFT.isDown());
		Button.Right = new NXTButton(lejos.nxt.Button.RIGHT.isDown());
		Button.Enter = new NXTButton(lejos.nxt.Button.ENTER.isDown());
		Button.Escape = new NXTButton(lejos.nxt.Button.ESCAPE.isDown());
		lock = new Object();
	}
	
	/**
	 * Creates a new NXTApp instance with the specified update interval. The update interval specifies how long to wait until the screen is updated.
	 * @param updateInterval the update interval, in milliseconds
	 */
	protected NXTApp2(int updateInterval)
	{
		this.updateInterval = updateInterval;
		instanceCode = instances++;
		synchronized(lock)
		{
			System.out.println("Initializing " + instanceCode);
			init();
			System.out.println("Complete!");
		}
	}
	
	/**
	 * Initialization hook. Implement this if your class needs to perform initialization (such as setting servo positions or zeroing out sensors) before it can run. The {@code init()} method of class NXTApp does nothing and returns.
	 */
	protected void init()
	{
		
	}
	
	private class AppThread implements Runnable
	{
		private Thread thread;
		private NXTApp2 app;
		
		AppThread()
		{
			thread = new Thread(this);
			thread.setName("NXTApp " + instanceCode);
		}
		
		@Override
		public void run()
		{
			while (!shouldExit())
			{
				synchronized(lock)
				{
					update();
				}
				
				if(Thread.interrupted())
					interrupted(app);
				
				try
				{
					Thread.sleep(updateInterval);
				}
				catch (InterruptedException e)
				{
					interrupted(app);
				}
				
				Button.Left.Update(lejos.nxt.Button.LEFT.isDown());
				Button.Right.Update(lejos.nxt.Button.RIGHT.isDown());
				Button.Enter.Update(lejos.nxt.Button.ENTER.isDown());
				Button.Escape.Update(lejos.nxt.Button.ESCAPE.isDown());
				
				if(Thread.interrupted())
					interrupted(app);
			}
			shutdown();
		}
		
		void start()
		{
			thread.start();
		}
		
		void sendInterrupt(NXTApp2 source)
		{
			app = source;
			thread.interrupt();
		}
	}
	
	/**
	 * Starts this app. This app should not already be running.
	 * @param waitForButtonPress whether to wait for a button on the NXT to be pressed before starting.
	 * @throws IllegalStateException if this app is already running.
	 */
	public final void run(boolean waitForButtonPress)
	{
		if(runningThread != null)
			throw new IllegalStateException("App " + instanceCode + " is already running");
		if(waitForButtonPress)
			synchronized(lock)
			{
				System.out.println("Press any button");
				System.out.println("to start.");
				lejos.nxt.Button.waitForAnyPress();
				LCD.clearDisplay();
				(runningThread = new AppThread()).start();
			}
		else
			(runningThread = new AppThread()).start();
	}
	
	/**
	 * Starts this app. This app should not already be running.
	 * @throws IllegalStateException if this app is already running.
	 */
	public final void run()
	{
		run(false);
	}
	
	/**
	 * This method is called when it is time for the screen to update (i.e. when the update interval time is reached). This method must do all the screen updating itself.
	 */
	public abstract void update();
	
	/**
	 * Checks whether this app should exit (i.e. is done executing). The {@code shutdown()} method is called when this method returns {@code true}.
	 * @return whether this app is done with its job
	 */
	public abstract boolean shouldExit();
	
	/**
	 * Shutdown hook. Implement this if this app needs to perform a special set of steps when it shuts down. The {@code shutdown()} method in class NXTApp does nothing and returns.
	 */
	protected void shutdown()
	{
		
	}
	
	/**
	 * Interrupt this app. The interrupted() method of this app is invoked.
	 * This method waits for update() to finish before calling interrupted() when it is called during an update session and, if called during a timeout between successive updates, the timeout is stopped, this method runs, and the next update is performed without waiting any longer.
	 * @param source who interrupted this app. Most of the time (if you're another NXTApp), you'll pass {@code this}.
	 * @see NXTApp2#interrupted()
	 */
	public void interrupt(NXTApp2 source)
	{
		runningThread.sendInterrupt(source);
	}
	
	/**
	 * Method that is invoked when this NXTApp is interrupted. Implement if you want to do something when another app interrupts you.
	 * The interrupted() method of class NXTApp2 does nothing and returns; it is an optional hook.
	 * @param interruptSource the NXTApp2 who interrupted you
	 */
	protected void interrupted(NXTApp2 interruptSource)
	{
		
	}
	
	/**
	 * Returns the instance code for this app.
	 * @return this app's instance code.
	 */
	@Override
	public int hashCode()
	{
		return instanceCode;
	}
}
