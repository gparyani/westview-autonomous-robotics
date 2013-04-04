package year2013.NXTApp.Sensors;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.GyroDirectionFinder;

public class GyroSensor extends Sensor
{
	private GyroDirectionFinder Gyro;
	private double AngularAcceleration;
	private double AngularVelocity; 
	private double Angle;
	
	public GyroSensor(SensorPort port)
	{
		super(-1, -1, -1, port);
		Gyro = new GyroDirectionFinder(new lejos.nxt.addon.GyroSensor(port), true);
		Gyro.resetCartesianZero();
	}
	
	protected void UpdateData()
	{
		this.AngularAcceleration = this.Gyro.getAngularAcceleration();
		this.AngularVelocity = this.Gyro.getAngularVelocity();
		this.Angle = this.Gyro.getDegrees();
	}
	
	public double GetAngularAcceleration()
	{
		return this.AngularAcceleration;
	}
	public double GetAngularVelocity()
	{
		return this.AngularVelocity;
	}
	public double GetAngle()
	{
		return this.Angle;
	}
	
	public void ResetAngle()
	{
		this.Gyro.resetCartesianZero();
	}
}
