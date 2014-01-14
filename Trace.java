package org.golovin.jtcad.geometry;

public class Trace extends Figure {

	int x1, y1, x2, y2;

	public Trace(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

	public Trace(int[] point1, int[] point2) {
		this.x1 = point1[0];
		this.x2 = point2[0];
		this.y1 = point1[1];
		this.y2 = point2[1];
	}

	@Override
	public int[][] getPoints() {
		return new int[][] { { x1, y1 }, { x2, y2 } };
	}

	@Override
	public int[][] getOutline(int increment) {

		int[][] points = new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
				{ 0, 0 } };

		int x = x2 - x1, y = y2 - y1; // vector dimensions

		if (y == 0) {
			y = 1;
		}

		double angle = Math.atan2(y, x);

		int nx = increment, ny = -(x * nx) / y; // perpendicular vector
										// dimensions

		int length = (int) Math.sqrt(x * x + y * y); // vector length

		int dx = (int) ((length + increment) * Math.cos(angle)), dy = (int) ((length + increment) * Math
				.sin(angle)); // extended coordinates

		points[0] = new int[] { x1 + dx + nx, y1 + dy + ny };
		points[1] = new int[] { x1 + dx - nx, y1 + dy - ny };
		points[2] = new int[] { x2 - dx - nx, y2 - dy - ny };
		points[3] = new int[] { x2 - dx + nx, y2 - dy + ny };
		points[4] = new int[] { x1 + dx + nx, y1 + dy + ny };

		return points;
	}
}
