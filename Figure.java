package org.golovin.jtcad.geometry;

public abstract class Figure {

	private boolean active = false;

	public void setActive(boolean active) {
		this.active = active;
	};

	public boolean isActive() {
		return active;
	}

	public abstract int[][] getPoints();

	@Override
	public String toString() {
		String classname = getClass().getCanonicalName();
		return classname.substring(classname.lastIndexOf('.') + 1) + "@"
				+ Integer.toHexString(hashCode());
	}
}