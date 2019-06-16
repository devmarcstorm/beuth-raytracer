package dms.rt.shapes;

import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.shapes.Shape;

/**
 * A class for a simple sphere.
 */
public class Sphere implements Shape{

	private Vec3 position;
	private Material material;
	private double radius;

	/**
	 * Creates a sphere with given properties.
	 * @param pos
	 * @param material
	 * @param rad
	 */
	public Sphere(Vec3 pos, Material material, double rad) {
		this.position = pos;
		this.material = material;
		this.radius = rad;
	}

	public Vec3 getPosition(){
		return this.position;
	}

	public Material getMaterial(){
		return this.material;
	}

	@Override	
	public Hit intersect(Ray ray) {
		Vec3 x0 = Vec3.subtract(ray.getOrigin(), position);
		double a = Vec3.dotProduct(ray.getDirection(), ray.getDirection());
		double b = Vec3.dotProduct(ray.getDirection(), Vec3.multiply(2, x0));
		double c = Vec3.dotProduct(x0, x0) - radius * radius;

		double discr = b * b - 4 * a * c;
		if (discr < 0) 
			return null;
		else if (discr == 0) {
			double t = (-b / (2 * a));
			if (ray.isValid(t)) {
				Vec3 hP = ray.pointAt(t);
				Vec3 n =  Vec3.divide(Vec3.subtract(hP, position), radius);
				double theta = Math.acos((hP.y - position.y) / radius) / Math.PI;
				double phie = (Math.PI + Math.atan2(hP.z - position.z, hP.x - position.x)) / (Math.PI * 2);
				Vec3 uv = new Vec3(phie, theta, 0);
				return new Hit(hP, n, t, material, uv);	
			}
			else 
				return null;
		} else {
			double x1 = ((-b + Math.sqrt(discr)) / (2 * a));
			double x2 = ((-b - Math.sqrt(discr)) / (2 * a));
			double intersect = 0;
			if (ray.isValid(x1)) intersect = x1;
			if (ray.isValid(x2) && x2 < x1) intersect = x2;
			else if (intersect == 0)
				return null;
			Vec3 hP = ray.pointAt(intersect);
			Vec3 n =  Vec3.divide(Vec3.subtract(hP, position), radius);
			double theta = Math.acos((hP.y - position.y) / radius) / Math.PI;
			double phie = (Math.PI + Math.atan2(hP.z - position.z, hP.x - position.x)) / (Math.PI * 2);
			Vec3 uv = new Vec3(phie, theta, 0);
			return new Hit(hP, n, intersect, material, uv);
		}	
	}
}