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
}
