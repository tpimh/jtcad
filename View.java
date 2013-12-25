package org.golovin.jtcad.intface;

import java.util.List;

import org.freedesktop.cairo.Context;
import org.gnome.gdk.RGBA;
import org.golovin.jtcad.geometry.Figure;

public class View {

	// private final static double OFFSET_STEP = 100.0D;
	private final static double LINE_WIDTH = 1.0D;
	private final static double CROSS_WIDTH = 5.0D;
	private final static double CROSS_SIZE = 2.0D;

	public View() {
	}

	private double offset[] = { 0, 0 };
	private double maximum[] = { 0, 0 };
	private double scale = 1.0D;

	public double getScale() {
		return scale;
	}

	public double[] getOffset() {
		return offset;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	/*
	 * public void addXoffset() { offset[0] += OFFSET_STEP; }
	 * 
	 * public void addYoffset() { offset[1] += OFFSET_STEP; }
	 * 
	 * public void reduceXoffset() { offset[0] -= OFFSET_STEP; }
	 * 
	 * public void reduceYoffset() { offset[1] -= OFFSET_STEP; }
	 */

	public void setOffset(double x, double y) {
		offset = new double[] { x, y };
	}

	public void findOffset(List<Figure> figs) {
		int min_x = Integer.MAX_VALUE, min_y = Integer.MAX_VALUE;
		int max_x = Integer.MIN_VALUE, max_y = Integer.MIN_VALUE;

		for (Figure f : figs) {
			for (int[] point : f.getPoints()) {
				if (point[0] < min_x) {
					min_x = point[0];
				}
				if (point[1] < min_y) {
					min_y = point[1];
				}
				if (point[0] > max_x) {
					max_x = point[0];
				}
				if (point[1] > max_y) {
					max_y = point[1];
				}
			}
		}

		offset = new double[] { min_x - 150, min_y - 150 };
		maximum = new double[] { max_x + 150, max_y + 150 };
	}

	public void fitAll(int x, int y) {
		scale = x / maximum[0];
	}

	public void draw(Figure fig, Context cr) {
		List<int[]> points = fig.getPoints();

		cr.setLineWidth(LINE_WIDTH);
		if (fig.getActive() == true) {
			cr.setSource(RGBA.RED);
		} else {
			cr.setSource(RGBA.BLACK);
		}
		cr.moveTo((points.get(0)[0] - offset[0]) * scale,
				(points.get(0)[1] - offset[1]) * scale);
		for (int i = 0; i < points.size() - 1; i++) {
			cr.lineTo((points.get(i + 1)[0] - offset[0]) * scale,
					(points.get(i + 1)[1] - offset[1]) * scale);
		}

		cr.stroke();
	}

	public void drawPoint(int[] point, Context cr) {
		cr.setLineWidth(CROSS_WIDTH);
		cr.setSource(RGBA.BLACK);
		cr.moveTo((point[0] - offset[0]) * scale, (point[1] - offset[1])
				* scale);
		cr.moveRelative(-CROSS_SIZE / 2, -CROSS_SIZE / 2);
		cr.lineRelative(CROSS_SIZE, CROSS_SIZE);
		cr.moveRelative(-CROSS_SIZE, 0);
		cr.lineRelative(CROSS_SIZE, -CROSS_SIZE);
		cr.stroke();
	}
}
