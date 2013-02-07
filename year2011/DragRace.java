package year2011;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;

public class DragRace {
	public static void main(String[] args){
		System.out.println("Press Enter to Start");
		while(!Button.ENTER.isDown()){}
		MotorControl motors = new MotorControl();
		motors.get(Headings.NORTH).resetTachoCount();
		motors.get(Headings.SOUTH).resetTachoCount();
		motors.dragRace();
		TetrixEncoderMotor north = motors.get(Headings.NORTH);
		TetrixEncoderMotor south = motors.get(Headings.SOUTH);
		
		while(true){
			if(Button.ESCAPE.isDown()){
				motors.kill();
				System.exit(0);
			}
		/*	int northn = north.getTachoCount();
			int southn = south.getTachoCount();
			int difference = Math.abs(northn) - Math.abs(southn);
			System.out.println(difference);
			if(difference > 0){
				south.setPower(100);
				north.setPower(80);
			}
			else if(difference <= 0){
				north.setPower(100);
				south.setPower(30);
			}
			try{
				Thread.sleep(10);
			}
			catch(Exception e){}  */
		}
	}
}