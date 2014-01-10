package org.golovin.jtcad.geometry;

public class RPad extends Figure {

	int xc, yc, radius;

	public RPad(int xc, int yc, int radius) {
		this.radius = radius;
		this.xc = xc;
		this.yc = yc;
	}

	public RPad(int[] center, int radius) {
		this.radius = radius;
		this.xc = center[0];
		this.yc = center[1];
	}

	@Override
	public int[][] getPoints() {

		int[][] points = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
				{ 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
				{ 0, 0 }, { 0, 0 } };

		for (int i = 0; i < 12; i++) {
			int x = (int) (Math.cos(Math.PI * i / 6) * radius) + xc;
			int y = (int) (Math.sin(Math.PI * i / 6) * radius) + yc;

			points[i][0] = x;
			points[i][1] = y;
		}

		points[12][0] = (int) (radius) + xc; // cos(0) == 1
		points[12][1] = (int) (0) + yc; // sin(0) == 1

		return points;
	}
}
