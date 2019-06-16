package dms.rt.shapes;

import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.shapes.Shape;

/**
 * A class for an infinitely far away background object.
 */
public class Background implements Shape{

	private Material material;

	/**
	 * Creates a background object with given material.
	 * @param surfaceColor
	 */
	public Background(Material material) {
		this.material = material;
	}

	/**
	 * Always returns a hit.
	 */
	@Override
	public Hit intersect(Ray ray) {

		if (ray.getTmax() < Double.POSITIVE_INFINITY)
			return null;
		else {
			double t = Double.POSITIVE_INFINITY;
			Vec3 hitPoint = ray.pointAt(t);
			Vec3 normalVector = new Vec3(0, 0, 0);
			double theta = Math.acos((ray.getDirection().y)) / Math.PI;
			double phie = (Math.PI + Math.atan2(ray.getDirection().z, ray.getDirection().x)) / (Math.PI * 2);
			Vec3 uv = new Vec3(phie, theta, 0);
			return new Hit(hitPoint, normalVector, t, material, uv);
		}	
	}
}