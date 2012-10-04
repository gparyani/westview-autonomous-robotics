package org.wvrobotics.iaroc;

import java.util.ArrayList;

import lejos.nxt.Button;

public class Robot {
	public Sensor sensor;
	protected ArrayList<State> leftPath;
	protected ArrayList<State> rightPath;
	protected MotorControl motors;
	
	public Robot(boolean isMaze, boolean single) {
		motors = new MotorControl();
		sensor = new Sensor(single);
		if(!isMaze)
			return;
		motors.maze();
		leftPath = new ArrayList<State>();
		leftPath.add(new State());
		leftPath.get(0).setPreviousHeading(Headings.NORTH);
		leftPath.get(0).setPositionRow(0);
		leftPath.get(0).setPositionCol(0);

		rightPath = new ArrayList<State>();
		rightPath.add(new State());
		rightPath.get(0).setPreviousHeading(Headings.NORTH);
		rightPath.get(0).setPositionRow(0);
		rightPath.get(0).setPositionCol(0);
	}

	public ArrayList<State> getLeftPath() {
		return leftPath;
	}

	public ArrayList<State> getRightPath() {
		return rightPath;
	}

	public void goldRush(){
		motors.goldRush();
	}
	
	public ArrayList<State> getOptimalPath() {
		if (rightPath.size() < leftPath.size())
			return rightPath;
		else
			return leftPath;
	}

	public void findLeftPath() {
		while (!sensor.isFinish(leftPath.get(leftPath.size() - 1)
				.getPositionRow(), leftPath.get(leftPath.size() - 1)
				.getPositionCol())) {
			autoAdjust();
			while (motors.isMoving()) {
			}
			findNextLeftState();
		}
		leftPath.get(leftPath.size() - 1).setNextHeading(Headings.done);
		autoAdjust();
		motors.moveNorth();
		motors.moveNorth();
		findOptimizedLeftPath();
	}

	private void findNextLeftState() {
		for (int i = 0; i < 4; ++i) {
			if (sensor
					.canGo(leftPath.get(leftPath.size() - 1).getPositionRow(),
							leftPath.get(leftPath.size() - 1).getPositionCol(),
							(leftPath.get(leftPath.size() - 1)
									.getPreviousHeading() + 3 + i) % 4)) {
				leftPath.get(leftPath.size() - 1)
						.setNextHeading(
								(leftPath.get(leftPath.size() - 1)
										.getPreviousHeading() + 3 + i) % 4);
				break;
			}
		}
		move(leftPath.get(leftPath.size() - 1).getNextHeading());
		if (Button.ESCAPE.isDown()) {
			System.exit(0);
		}
		leftPath.get(leftPath.size() - 1).setLength(1);
		leftPath.add(new State());
		leftPath.get(leftPath.size() - 1).setPreviousHeading(
				leftPath.get(leftPath.size() - 2).getNextHeading());
		leftPath.get(leftPath.size() - 1).setPositionRow(
				leftPath.get(leftPath.size() - 2).getPositionRow());
		leftPath.get(leftPath.size() - 1).setPositionCol(
				leftPath.get(leftPath.size() - 2).getPositionCol());
		switch (leftPath.get(leftPath.size() - 1).getPreviousHeading()) {
		case Headings.WEST:
			leftPath.get(leftPath.size() - 1).setPositionRow(
					leftPath.get(leftPath.size() - 1).getPositionRow() - 1);
			break;
		case Headings.NORTH:
			leftPath.get(leftPath.size() - 1).setPositionCol(
					leftPath.get(leftPath.size() - 1).getPositionCol() + 1);
			break;
		case Headings.EAST:
			leftPath.get(leftPath.size() - 1).setPositionRow(
					leftPath.get(leftPath.size() - 1).getPositionRow() + 1);
			break;
		case Headings.SOUTH:
			leftPath.get(leftPath.size() - 1).setPositionCol(
					leftPath.get(leftPath.size() - 1).getPositionCol() - 1);
			break;
		}
	}

	public void autoAdjust() {
		rotate();
		boolean nadjusted = false;
		boolean sadjusted = false;
		boolean eadjusted = false;
		boolean wadjusted = false;
		boolean north = sensor.canGo(0, 0, Headings.NORTH);
		boolean south = sensor.canGo(0, 0, Headings.SOUTH);
		boolean east = sensor.canGo(0, 0, Headings.EAST);
		boolean west = sensor.canGo(0, 0, Headings.WEST);
		if (!north && !south) {
			nsAdjust();
			nadjusted = true;
			sadjusted = true;
		}
		if (!east && !west) {
			ewAdjust();
			eadjusted = true;
			wadjusted = true;
		}
		singleAdjust(nadjusted, sadjusted, eadjusted, wadjusted);

	}

	private void rotate() {
		boolean north = sensor.canGo(0, 0, Headings.NORTH);
		if (!north && (sensor.getDifference(Headings.NORTH) < 15 && sensor.getDifference(Headings.NORTH) > -15)) {
			rotateN();
		}
		if (!sensor.canGo(0, 0, Headings.SOUTH) && (sensor.getDifference(Headings.SOUTH) < 15 && sensor.getDifference(Headings.SOUTH) > -15)) {
			rotateS();
		} 
		if (!sensor.canGo(0, 0, Headings.EAST) && (sensor.getDifference(Headings.EAST) < 15 && sensor.getDifference(Headings.EAST) > -15)) {
			rotateE();
		} 
		if (!sensor.canGo(0, 0, Headings.WEST) && (sensor.getDifference(Headings.WEST) < 15 && sensor.getDifference(Headings.WEST) > -15)) {
			rotateW();
		}
	}

	private void rotateW() {
		motors.rotate(sensor.getDifference(Headings.WEST) / 2);
	}

	private void rotateE() {
		motors.rotate(sensor.getDifference(Headings.EAST) / 2);
	}

	private void rotateS() {
		motors.rotate(sensor.getDifference(Headings.SOUTH) / 2);
	}

	private void rotateN() {
		motors.rotate(sensor.getDifference(Headings.NORTH) / 2);
	}

	private void nsAdjust() {
		int north = sensor.getAverage(Headings.NORTH);
		int south = sensor.getAverage(Headings.SOUTH);
		motors.moveNS((north - south) / 2);
	}

	private void ewAdjust() {
		int east = sensor.getAverage(Headings.EAST);
		int west = sensor.getAverage(Headings.WEST);
		motors.moveEW((east - west) / 2);
	}

	private void singleAdjust(boolean n, boolean s, boolean e, boolean w) {
		boolean north = sensor.canGo(0, 0, Headings.NORTH);
		if (!north && !n) {
			singleN();
		}
		if (!sensor.canGo(0, 0, Headings.SOUTH) && !s) {
			singleS();
		}
		if (!sensor.canGo(0, 0, Headings.EAST) && !e) {
			singleE();
		}
		if (!sensor.canGo(0, 0, Headings.WEST) && !w) {
			singleW();
		}
	}

	private void singleN() {
		motors.moveNS((sensor.getAverage(Headings.NORTH) - 23)/2);
	}

	private void singleS() {
		motors.moveNS((23 - sensor.getAverage(Headings.SOUTH))/2);
	}

	private void singleE() {
		motors.moveEW((sensor.getAverage(Headings.EAST) - 23)/2);
	}

	private void singleW() {
		motors.moveEW((23 - sensor.getAverage(Headings.WEST))/2);
	}

	public void move(int nextHeading) {
		if (nextHeading == Headings.NORTH)
			motors.moveNorth();
		else if (nextHeading == Headings.SOUTH)
			motors.moveSouth();
		else if (nextHeading == Headings.EAST)
			motors.moveEast();
		else if (nextHeading == Headings.WEST)
			motors.moveWest();
	}

	public void findOptimizedLeftPath() {
		for (int i = 0; i < leftPath.size(); ++i) {
			for (int j = (leftPath.size() - 1); j > i; --j) {
				if (leftPath.get(i).getPositionRow() == leftPath.get(j)
						.getPositionRow()
						&& leftPath.get(i).getPositionCol() == leftPath.get(j)
								.getPositionCol()) {
					for (int k = 0; k < j - i; ++k)
						leftPath.remove(i);
					leftPath.get(i).setPreviousHeading(
							leftPath.get(i - 1).getNextHeading());
					j = leftPath.size() - 1;
				}
			}
		}
	}

	public void findRightPath() {
		while (!sensor.isFinish(rightPath.get(rightPath.size() - 1)
				.getPositionRow(), rightPath.get(rightPath.size() - 1)
				.getPositionCol())){
			autoAdjust();
			while (motors.isMoving()) {
			}
			findNextRightState();
		}
		rightPath.get(rightPath.size() - 1).setNextHeading(Headings.done);
		autoAdjust();
		motors.moveNorth();
		motors.moveNorth();
		findOptimizedRightPath();
	}

	public void findNextRightState() {
		for (int i = 0; i < 4; ++i) {
			if (sensor.canGo(rightPath.get(rightPath.size() - 1)
					.getPositionRow(), rightPath.get(rightPath.size() - 1)
					.getPositionCol(), (rightPath.get(rightPath.size() - 1)
					.getPreviousHeading() + 5 - i) % 4)) {
				rightPath.get(rightPath.size() - 1).setNextHeading(
						(rightPath.get(rightPath.size() - 1)
								.getPreviousHeading() + 5 - i) % 4);
				break;
			}
		}
		// Add Code to Move the Robot in Direction leftPath.get(leftPath.size()
		// - 1).getNextHeading() by One Block
		move(rightPath.get(rightPath.size() - 1).getNextHeading());
		if (Button.ESCAPE.isDown()) {
			System.exit(0);
		}
		rightPath.get(rightPath.size() - 1).setLength(1); // If Length is Not
															// Standard, Set
															// Length to
															// Something Else

		rightPath.add(new State());
		rightPath.get(rightPath.size() - 1).setPreviousHeading(
				rightPath.get(rightPath.size() - 2).getNextHeading());
		rightPath.get(rightPath.size() - 1).setPositionRow(
				rightPath.get(rightPath.size() - 2).getPositionRow());
		rightPath.get(rightPath.size() - 1).setPositionCol(
				rightPath.get(rightPath.size() - 2).getPositionCol());
		switch (rightPath.get(rightPath.size() - 1).getPreviousHeading()) {
		case Headings.WEST:
			rightPath.get(rightPath.size() - 1).setPositionRow(
					rightPath.get(rightPath.size() - 1).getPositionRow() - 1);
			break;
		case Headings.NORTH:
			rightPath.get(rightPath.size() - 1).setPositionCol(
					rightPath.get(rightPath.size() - 1).getPositionCol() + 1);
			break;
		case Headings.EAST:
			rightPath.get(rightPath.size() - 1).setPositionRow(
					rightPath.get(rightPath.size() - 1).getPositionRow() + 1);
			break;
		case Headings.SOUTH:
			rightPath.get(rightPath.size() - 1).setPositionCol(
					rightPath.get(rightPath.size() - 1).getPositionCol() - 1);
			break;
		}
	}

	public void findOptimizedRightPath() {
		for (int i = 0; i < rightPath.size(); ++i) {
			for (int j = (rightPath.size() - 1); j > i; --j) {
				if (rightPath.get(i).getPositionRow() == rightPath.get(j)
						.getPositionRow()
						&& rightPath.get(i).getPositionCol() == rightPath
								.get(j).getPositionCol()) {
					for (int k = 0; k < j - i; ++k)
						rightPath.remove(i);
					rightPath.get(i).setPreviousHeading(
							rightPath.get(i - 1).getNextHeading());
					j = rightPath.size() - 1;
				}
			}
		}
	}

	public MotorControl getMotors() {
		return motors;
	}
};

