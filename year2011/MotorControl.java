package year2011;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.tetrix.TetrixControllerFactory;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;
import lejos.nxt.addon.tetrix.TetrixMotorController;
import lejos.nxt.addon.tetrix.TetrixServoController;


public class MotorControl {
	private boolean moving;
	
	public static int DISTANCE = 1136;
	public static double CM_IN_INCHES = 2.54;
	public static int NORTH = TetrixMotorController.MOTOR_1;
	public static int SOUTH = TetrixMotorController.MOTOR_2;
	public static int EAST = TetrixMotorController.MOTOR_1;
	public static int WEST = TetrixMotorController.MOTOR_2;
	private TetrixEncoderMotor north;
	private TetrixEncoderMotor south;
	private TetrixEncoderMotor east;
	private TetrixEncoderMotor west;
	
	public MotorControl(){
		moving = false;
		TetrixControllerFactory cf = new TetrixControllerFactory(SensorPort.S1);
		TetrixMotorController mc2 = cf.newMotorController();
		cf.newServoController();
		TetrixMotorController mc1 = cf.newMotorController();
		north = mc1.getRegulatedMotor(NORTH);
		south = mc1.getRegulatedMotor(SOUTH);
		east = mc2.getRegulatedMotor(EAST);
		west = mc2.getRegulatedMotor(WEST);
	}

	public boolean isMoving(){
		return moving;
	}
	
	public void moveNorth() {
		System.out.println("Moving North");
		moving = true;
		east.rotate(DISTANCE, true);
		west.rotate(-DISTANCE, true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		east.stop();
		west.stop();
		moving = false;
	}
	
	public void moveSouth() {
		System.out.println("Moving South");
		moving = true;
		east.rotate(-DISTANCE, true);
		west.rotate(DISTANCE, false);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		east.stop();
		west.stop();
		moving = false;
	}
	
	public void rotate(int distance){
		moving = true;
		// 1 inch = 2.54cm
		east.rotate(-(int)(((distance / CM_IN_INCHES) / 31) * DISTANCE), true);
		north.rotate(-(int)(((distance / CM_IN_INCHES) / 31) * DISTANCE), true);
		south.rotate(-(int)(((distance / CM_IN_INCHES) / 31) * DISTANCE), true);
		west.rotate(-(int)(((distance / CM_IN_INCHES) / 31) * DISTANCE), false);
		try {
			Thread.sleep(1000);
		}
		catch(Exception e){
			
		}
		east.stop();
		north.stop();
		south.stop();
		west.stop();
		moving = false;
	}
	
	
	public void moveEast(){

		System.out.println("Moving East");
		moving = true;
		north.rotate(-DISTANCE, true);
		south.rotate(DISTANCE, false);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		north.stop();
		south.stop();
		moving = false;
	}
	
	public void moveNS(int distance){
		moving = true;
		east.rotate((int) ((double) ((distance / 2.54)/31) * DISTANCE), true);
		west.rotate((int) -((double) ((distance / 2.54)/31) * DISTANCE), false);
		try {
			Thread.sleep(1000);
		}
		catch(Exception e){
			
		}
		east.stop();
		west.stop();
		moving = false;
	}
	
	public TetrixEncoderMotor get(int direction){
		if(direction == Headings.NORTH)
			return north;
		else if(direction == Headings.SOUTH)
			return south;
		else if(direction == Headings.WEST)
			return west;
		else
			return east;
	}
	
	public void moveEW(int distance){
		moving = true;
		System.out.println(distance);
		north.rotate((int) -((double) ((distance / 2.54)/31) * DISTANCE), true);
		south.rotate((int) ((double) ((distance / 2.54)/31) * DISTANCE), false);
		try {
			Thread.sleep(1000);
		}
		catch(Exception e){
			
		}
		east.stop();
		west.stop();
		moving = false;
	}
	
	public void moveWest(){

		System.out.println("Moving West");
		moving = true;
		north.rotate(DISTANCE, true);
		south.rotate(-DISTANCE, false);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		north.stop();
		south.stop();
		moving = false;		
	}

	public void dragRace() {
		north.setPower(100);
		south.setPower(90);
		east.setPower(0);
		west.setPower(0);
		south.forward();
		north.backward();
	}

	public void kill() {
		north.stop();
		south.stop();
		east.stop();
		west.stop();
	}

	public void maze() {
		north.setPower(50);
		south.setPower(50);
		west.setPower(50);
		east.setPower(50);
	}

	public void goldRush() {
		north.setPower(100);
		south.setPower(100);
		east.setPower(100);
		west.setPower(100);
	}

	public void moveDirection(int i) {
		if(i == Headings.NORTH)
			moveNorth();
		else if(i == Headings.SOUTH)
			moveSouth();
		else if(i == Headings.EAST)
			moveEast();
		else
			moveWest();
	}
	

}

