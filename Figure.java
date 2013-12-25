package org.golovin.jtcad.geometry;

import java.util.ArrayList;
import java.util.List;

import CGAL.Kernel.Point_2;
import CGAL.Kernel.Polygon_2;

public abstract class Figure {

	Polygon_2 polygon;

	public Figure() {
		polygon = new Polygon_2();
	}

	public List<int[]> getPoints() {
		List<int[]> list = new ArrayList<int[]>();
		
		for (int i = 0; i < polygon.size(); i++) {
			Point_2 point = polygon.vertex(i);
			
			list.add(new int[] { (int) point.x(), (int) point.y() });
		}

		return list;
	}

	public void addPoint(int[] point) {
		polygon.push_back(new Point_2(point[0], point[1]));
	}

	private boolean active = false;

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive() {
		return active;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@"
				+ Integer.toHexString(polygon.hashCode());
	}
}