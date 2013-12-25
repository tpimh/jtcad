package org.golovin.jtcad.geometry;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure {

	List<int[]> list;

	public Figure() {
		list = new ArrayList<int[]>();
	}

	public List<int[]> getPoints() {
		return list;
	}

	public void addPoint(int[] point) {
		list.add(point);
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
				+ Integer.toHexString(list.hashCode());
	}
}