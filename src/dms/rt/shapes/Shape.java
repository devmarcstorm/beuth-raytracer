package dms.rt.shapes;

import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;

public interface Shape {

	/**
	 * Creates a hit object from a ray if 't' is valid.
	 * @param A ray object
	 * @return A hit object
	 */
	public Hit intersect(Ray ray);
}