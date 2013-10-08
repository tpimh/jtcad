public class Figure {
	public Figure(int points[][]) {
		this.points = points;
	}

	private int points[][];

	public int[][] getPoints() {
		return points;
	}

	private boolean active = false;

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive() {
		return active;
	}
}
