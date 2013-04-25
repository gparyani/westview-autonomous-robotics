package year2013.NXTApp;

import lejos.nxt.LCD;

/**
 * The general class that apps that run on the NXT microcontroller should extend. This class provides a loop system in which a method is executed and the app updates the screen. Example:<br />
 * <pre><code>public class MyApp extends NXTApp
 * {
 * 	public MyApp()
 * 	{
 * 		super(50);	//set the update interval to 50 ms
 * 	}
 * 	
 * 	public void init()
 * 	{
 * 		//perform initialization of sensors and motors
 * 		//invoked upon construction
 * 	}
 * 	
 * 	public void Update()
 * 	{
 * 		System.out.println("Sensor values: " + getSensorValues());
 * 	}
 * 	
 * 	public boolean ShouldExit()
 * 	{
 * 		return reachedTarget();
 * 	}
 * 
 * 	public void shutdown()
 * 	{
 * 		System.out.println("App shutdown");
 * 	}
 * 
 * 	public static void main(String[] args)
 * 	{
 * 		new MyApp().Run();
 * 	}
 * }</code></pre>
 * @author Grant Posner
 *
 */
public abstract class NXTApp
{
	private int UpdateInterval;
	private int instanceCode;
	private AppThread runningThread;
	private static int instances = 1;
	private static Object lock;
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
	protected NXTApp(int updateInterval)
	{
		this.UpdateInterval = updateInterval;
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
		
		AppThread()
		{
			thread = new Thread(this);
			thread.setName("NXTApp " + instanceCode);
		}
		
		@Override
		public void run()
		{
			while (!ShouldExit())
			{
				synchronized(lock)
				{
					Update();
				}
				
				try
				{
					Thread.sleep(UpdateInterval);
				}
				catch (InterruptedException e) { }
				
				Button.Left.Update(lejos.nxt.Button.LEFT.isDown());
				Button.Right.Update(lejos.nxt.Button.RIGHT.isDown());
				Button.Enter.Update(lejos.nxt.Button.ENTER.isDown());
				Button.Escape.Update(lejos.nxt.Button.ESCAPE.isDown());
			}
			shutdown();
		}
		
		void start()
		{
			thread.start();
		}
	}
	
	/**
	 * Starts this app. This app should not already be running.
	 * @param waitForButtonPress whether to wait for a button on the NXT to be pressed before starting.
	 * @throws IllegalStateException if this app is already running.
	 */
	public final void Run(boolean waitForButtonPress)
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
	public final void Run()
	{
		Run(false);
	}
	
	/**
	 * This method is called when it is time for the screen to update (i.e. when the update interval time is reached). This method must do all the screen updating itself.
	 */
	public abstract void Update();
	
	/**
	 * Checks whether this app should exit (i.e. is done executing). The {@code shutdown()} method is called when this method returns {@code true}.
	 * @return whether this app is done with its job
	 */
	public abstract boolean ShouldExit();
	
	/**
	 * Shutdown hook. Implement this if this app needs to perform a special set of steps when it shuts down. The {@code shutdown()} method in class NXTApp does nothing and returns.
	 */
	protected void shutdown()
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
