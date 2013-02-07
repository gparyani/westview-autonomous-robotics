package year2011;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;

public class Maze implements ButtonListener {

	private Robot robot;
	private boolean started = false;

	// public static CustomLogger log = new CustomLogger();

	public Maze() {
		robot = new Robot(true, false);
		Button.LEFT.addButtonListener(this);
		Button.RIGHT.addButtonListener(this);
		Button.ESCAPE.addButtonListener(this);
		Button.ENTER.addButtonListener(this);
		System.out.println("Use the directional buttons to start the program");
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void buttonPressed(Button b) {
		if (b.equals(Button.LEFT) && !started) {
			LCD.clearDisplay();
			started = true;
			left();
		} else if (b.equals(Button.RIGHT) && !started) {
			LCD.clearDisplay();
			started = true;
			right();
		} else if(b.equals(Button.ENTER) && !started){
			LCD.clearDisplay();
			started = true;
			try {
				runOptimizedPath(readArrayList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.exit(0);
	}

	public ArrayList<State> readArrayList() throws IOException {
		ArrayList<State> arraylist = new ArrayList<State>();
		if(!new File("path").exists())
			new File("path").createNewFile();
		FileInputStream reader = new FileInputStream(new File("path"));
		for (int i = reader.available(); i > 0; i -= 4) {
			for (int k = 0; k < 4; k++) {
				arraylist.add(new State());
				arraylist.get(arraylist.size() - 1).setNextHeading(
						reader.read());
				arraylist.get(arraylist.size() - 1).setPreviousHeading(
						reader.read());
				arraylist.get(arraylist.size() - 1).setPositionCol(
						reader.read());
				arraylist.get(arraylist.size() - 1).setPositionRow(
						reader.read());
			}
		}
		reader.close();
		new File("path").delete();
		return arraylist;
	}

	public void writeArrayList(ArrayList<State> arraylist) throws IOException {
		if(new File("path").exists())
			new File("path").delete();
		new File("path").createNewFile();
		FileOutputStream writer = new FileOutputStream(new File("path"));
		for (State s : arraylist) {
			writer.write(s.getNextHeading());
			writer.write(s.getPreviousHeading());
			writer.write(s.getPositionCol());
			writer.write(s.getPositionRow());
		}
		writer.close();
	}

	public void left() {
		robot.findLeftPath();
		ArrayList<State> leftPath = robot.getLeftPath();
		try {
			writeArrayList(leftPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runOptimizedPath(ArrayList<State> path) {
		for (int i = 0; i < path.size(); i++) {
			robot.autoAdjust();
			robot.move(path.get(i).getNextHeading());
			robot.getMotors().moveNorth();
		}
	}

	public void right() {
		robot.findRightPath();
		ArrayList<State> rightPath = robot.getRightPath();
		try {
			writeArrayList(rightPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Maze();

		/*
		 * Algorithm algorithm = new Algorithm();
		 * 
		 * for (;;) { System.out.println("Options: ");
		 * System.out.println("   0) Run Left Path and Auto-Optimize");
		 * System.out.println("   1) Run Right Path and Auto-Optimize");
		 * System.out
		 * .println("   2) Run Optimal Path (must have previously run 0 and 1)"
		 * ); System.out.println(
		 * "   3) Run Left Auto-Optimized Path (must have previously run 0)");
		 * System.out.println(
		 * "   4) Run Right Auto-Optimized Path (must have previously run 1)");
		 * System.out.println("   5) Exit");
		 * 
		 * int option = userIO.nextInt();
		 * 
		 * switch (option) { case 0: algorithm.findLeftPath(); ArrayList<State>
		 * leftPath = algorithm.getLeftPath();
		 * 
		 * System.out.println("");
		 * System.out.println("Unoptimized Left Path: "); for (int i = 0; i <
		 * leftPath.size(); ++i) { System.out.println("Step " + i + ": ");
		 * System.out.println("   Previous Heading: " +
		 * leftPath.get(i).getPreviousHeading());
		 * System.out.println("   Next Heading: " +
		 * leftPath.get(i).getNextHeading());
		 * System.out.println("   Position: (" +
		 * leftPath.get(i).getPositionRow() + "," +
		 * leftPath.get(i).getPositionCol() + ")"); }
		 * 
		 * algorithm.findOptimizedLeftPath(); leftPath =
		 * algorithm.getLeftPath();
		 * 
		 * System.out.println(""); System.out.println("Optimized Left Path: ");
		 * for (int i = 0; i < leftPath.size(); ++i) {
		 * System.out.println("Step " + i + ": ");
		 * System.out.println("   Previous Heading: " +
		 * leftPath.get(i).getPreviousHeading());
		 * System.out.println("   Next Heading: " +
		 * leftPath.get(i).getNextHeading());
		 * System.out.println("   Position: (" +
		 * leftPath.get(i).getPositionRow() + "," +
		 * leftPath.get(i).getPositionCol() + ")"); } break; case 1:
		 * algorithm.findRightPath(); ArrayList<State> rightPath =
		 * algorithm.getRightPath();
		 * 
		 * System.out.println("");
		 * System.out.println("Unoptimized Right Path: "); for (int i = 0; i <
		 * rightPath.size(); ++i) { System.out.println("Step " + i + ": ");
		 * System.out.println("   Previous Heading: " +
		 * rightPath.get(i).getPreviousHeading());
		 * System.out.println("   Next Heading: " +
		 * rightPath.get(i).getNextHeading());
		 * System.out.println("   Position: (" +
		 * rightPath.get(i).getPositionRow() + "," +
		 * rightPath.get(i).getPositionCol() + ")"); }
		 * 
		 * algorithm.findOptimizedRightPath(); rightPath =
		 * algorithm.getRightPath();
		 * 
		 * System.out.println(""); System.out.println("Optimized Right Path: ");
		 * for (int i = 0; i < rightPath.size(); ++i) {
		 * System.out.println("Step " + i + ": ");
		 * System.out.println("   Previous Heading: " +
		 * rightPath.get(i).getPreviousHeading());
		 * System.out.println("   Next Heading: " +
		 * rightPath.get(i).getNextHeading());
		 * System.out.println("   Position: (" +
		 * rightPath.get(i).getPositionRow() + "," +
		 * rightPath.get(i).getPositionCol() + ")"); } break; case 2:
		 * ArrayList<State> optimalPath = algorithm.getOptimalPath();
		 * 
		 * System.out.println(""); System.out.println("Optimal Path: "); for
		 * (int i = 0; i < optimalPath.size(); ++i) { System.out.println("Step "
		 * + i + ": "); System.out.println("   Previous Heading: " +
		 * optimalPath.get(i).getPreviousHeading());
		 * System.out.println("   Next Heading: " +
		 * optimalPath.get(i).getNextHeading());
		 * System.out.println("   Position: (" +
		 * optimalPath.get(i).getPositionRow() + "," +
		 * optimalPath.get(i).getPositionCol() + ")"); } break; case 3: case 4:
		 * break; case 5: userIO.close(); System.exit(0); break; default:
		 * System.out.println("Unrecognized option \"" + option + "\"."); } }
		 */
	}

	@Override
	public void buttonReleased(Button b) {
		// TODO Auto-generated method stub

	}
}

