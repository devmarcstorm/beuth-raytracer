package dms.rt.shapes;

import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.shapes.Shape;


/**
 * A class for a layer / plane object. (Strength 0)
 */
public class Plane implements Shape{

	private Material material;
	private Vec3 normalVector;
	private Vec3 anchorPoint;
	private Vec3 size;

	/**
	 * Creates a plane with given properties.
	 * @param surfaceColor
	 * @param normalVector
	 * @param anchorPoint
	 */
	public Plane(Vec3 anchorPoint, Vec3 normalVector, Material material, Vec3 size) {
		this.material = material;
		this.normalVector = normalVector;
		this.anchorPoint = anchorPoint;
		this.size = size;
	}

	@Override
	public Hit intersect(Ray ray) {

		double nenner = Vec3.dotProduct(ray.getDirection(), normalVector);
		double t;
		if (nenner != 0) {
			t = (Vec3.dotProduct(anchorPoint, normalVector) - Vec3.dotProduct(ray.getOrigin(), normalVector)) / nenner;
			if (ray.isValid(t)) {
				Vec3 pos = ray.pointAt(t);

				if (pos.x < size.x && pos.x > -size.x && pos.z < size.z && pos.z > -size.z && pos.y < size.y) {
					Hit hit = new Hit(pos, normalVector, t, material, new Vec3(pos.x, pos.z, 0));
					return hit;
				} else
					return null;
			}	
			else 
				return null;
		} else 
			return null;
	}
}