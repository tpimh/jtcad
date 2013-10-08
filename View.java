import org.freedesktop.cairo.Context;
import org.gnome.gdk.RGBA;

public class View {

	private final static double OFFSET_STEP = 100.0D;
	private final static double LINE_WIDTH = 1.0D;
	private final static double CROSS_WIDTH = 5.0D;
	private final static double CROSS_SIZE = 2.0D;

	public View() {
	}

	private double offset[] = { 1000, 1000 };
	private double scale = 0.5;

	public double getScale() {
		return scale;
	}

	public double[] getOffset() {
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

		cr.setLineWidth(LINE_WIDTH);
		if (fig.getActive() == true) {
			cr.setSource(RGBA.RED);
		} else {
			cr.setSource(RGBA.BLACK);
		}
		cr.moveTo((points[0][0] - offset[0]) * scale,
				(points[0][1] - offset[1]) * scale);
		for (int i = 0; i < points.length - 1; i++) {
			cr.lineTo((points[i + 1][0] - offset[0]) * scale,
					(points[i + 1][1] - offset[1]) * scale);
		}

		cr.stroke();
	}

	public void drawPoint(int[] point, Context cr) {
		cr.setLineWidth(CROSS_WIDTH);
		cr.setSource(RGBA.BLACK);
		cr.moveTo((point[0] - offset[0]) * scale, (point[1] - offset[1]) * scale);
		cr.moveRelative(-CROSS_SIZE / 2, -CROSS_SIZE / 2);
		cr.lineRelative(CROSS_SIZE, CROSS_SIZE);
		cr.moveRelative(-CROSS_SIZE, 0);
		cr.lineRelative(CROSS_SIZE, -CROSS_SIZE);
		cr.stroke();
	}
}
