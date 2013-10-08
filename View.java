import org.freedesktop.cairo.Context;

public class View {

	private final static int OFFSET_STEP = 100;

	public View() {
	}

	private int offset[] = { 1000, 1000 };
	private double scale = 0.5;

	public double getScale() {
		return scale;
	}

	public int[] getOffset() {
		return offset;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void addXoffset() {
		offset[0] += OFFSET_STEP;
	}

	public void addYoffset() {
		offset[1] += OFFSET_STEP;
	}

	public void reduceXoffset() {
		offset[0] -= OFFSET_STEP;
	}

	public void reduceYoffset() {
		offset[1] -= OFFSET_STEP;
	}

	public void draw(Figure fig, Context cr) {
		int[][] points = fig.getPoints();

		cr.setLineWidth(1.0);
		if (fig.getActive() == true) {
			cr.setSource(1.0, 0.0, 0.0, 1.0);
		} else {
			cr.setSource(0.0, 0.0, 0.0, 1.0);
		}
		cr.moveTo((points[0][0] - offset[0]) * scale,
				(points[0][1] - offset[1]) * scale);
		for (int i = 1; i < points.length - 1; i++) {
			cr.lineTo((points[i + 1][0] - offset[0]) * scale,
					(points[i + 1][1] - offset[1]) * scale);
		}

		cr.stroke();
	}

	public void drawPoint(int[] point, Context cr) {
		cr.setLineWidth(5.0);
		cr.setSource(1.0, 0.0, 0.0, 1.0);
		cr.moveTo((point[0] - offset[0]) * scale, (point[1] - offset[1]) * scale);
		cr.moveRelative(-1, -1);
		cr.lineRelative(2, 2);
		cr.moveRelative(-2, 0);
		cr.lineRelative(2, -2);
		cr.stroke();
	}
}
