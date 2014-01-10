package org.golovin.jtcad.geometry;

public class Pad extends Figure {

	int x1, y1, x2, y2, x3, y3, x4, y4;

	public Pad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.x4 = x4;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		this.y4 = y4;
	}

	public Pad(int[] point1, int[] point2, int[] point3, int[] point4) {
		this.x1 = point1[0];
		this.x2 = point2[0];
		this.x3 = point3[0];
		this.x4 = point4[0];
		this.y1 = point1[1];
		this.y2 = point2[1];
		this.y3 = point3[1];
		this.y4 = point4[1];
	}

	@Override
	public int[][] getPoints() {
		return new int[][] { { x1, y1 }, { x2, y2 }, { x3, y3 }, { x4, y4 },
				{ x1, y1 } };
	}
}
