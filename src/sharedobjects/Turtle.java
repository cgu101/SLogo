package sharedobjects;

import java.util.Observable;

public class Turtle {
	private int ID;
	private double[] position;
	private double[] oldPosition;
	private double heading;
	private boolean showing;
	private boolean active;
	private int windowMode;
	private static final int WRAP = 1;
	private static final int WINDOW = 2;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 290;

	public Turtle(int id) {
		setID(id);
		position = new double[] { 0.0, 0.0 };
		oldPosition = new double[] { 0.0, 0.0 };
		heading = 90;
		showing = true;
		active = true;
		windowMode = WINDOW;
	}

	public void setID(int i) {
		ID = i;
	}

	public int getID() {
		return ID;
	}

	public void setPosition(double[] p) {
		oldPosition = position;
		// Wrap
		if (windowMode == WRAP) {
			if (p[0] > WIDTH)
				p[0] = -WIDTH + (p[0] - WIDTH);
			if (p[0] < -WIDTH)
				p[0] = WIDTH - (p[0] + WIDTH);
			if (p[1] > HEIGHT)
				p[1] = -HEIGHT + (p[1] - HEIGHT);
			if (p[1] < -HEIGHT)
				p[1] = HEIGHT - (p[1] + HEIGHT);
		}
		position = p;
		System.out.println("Changing the current Turtle's position to..." + p[0] + ":" + p[1]);
	}

	public double[] getPosition() {
		return position;
	}

	public double[] getOldPosition() {
		return oldPosition;
	}

	public void setHeading(double h) {
		heading = h;
	}

	public double getHeading() {
		return heading;
	}

	public void show() {
		showing = true;
	}

	public void hide() {
		showing = false;
	}

	public boolean isShowing() {
		return showing;
	}

	public boolean isActive() {
		return active;
	}

	public void activate() {
		active = true;
	}

	public void deativate() {
		active = false;
	}

	public void setWindowWrap() {
		windowMode = WRAP;
	}

	public void setWindowInfinite() {
		windowMode = WINDOW;
	}
}
