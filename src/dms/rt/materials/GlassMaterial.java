package dms.rt.materials;

import cgtools.Random;
import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.materials.ReflectionProperties;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;

/**
 * Class - implements Material - for a glass material.
 */
public class GlassMaterial implements Material{

	public Vec3 emission;
	public Vec3 albedo;
	public double roughness;
	public double transparency;

	/**
	 * Sets the values for the material.
	 * @param emission
	 * @param albedo
	 */
	public GlassMaterial(Vec3 emission, Vec3 albedo) {
		this.emission = emission;
		this.albedo = albedo;
	}

	@Override
	public ReflectionProperties properties(Ray ray, Hit hit) {
		Vec3 d = ray.getDirection(); // direction of the ray
		Vec3 n = hit.getnormalVec();

		double n1;
		double n2;

		if (Vec3.dotProduct(d, n) < 0) { // Ray comes from the outside
			n1 = 1.0;
			n2 = 1.5;
		} else { // Ray comes from within
			n1 = 1.5;
			n2 = 1.0;
			n = Vec3.multiply(-1, n);
		}

		Vec3 scattered; // reflection direction
		Vec3 refract = refract(d, n, n1, n2);

		if (refract != null) {
			if (Random.random() > schlick(d, n, n1, n2))
				scattered = refract;
			else 
				scattered = reflect(d, n);
		} else {
			scattered = reflect(d, n);
		}

		Ray scatteredRay = new Ray(hit.getHitPointPos(), scattered, 0.0000001, Double.POSITIVE_INFINITY);
		return new ReflectionProperties(scatteredRay, emission, albedo);
	}

	public Vec3 refract(Vec3 d, Vec3 n, double n1, double n2) {

		// Implementation of the law of refraction
		double rt = n1 / n2;
		double c = Vec3.dotProduct(Vec3.multiply(n, -1), d);

		Vec3 rd = Vec3.multiply(rt, d);
		double rc = rt * c;
		double disc = 1 - rt * rt * (1 - c * c);


		if (disc >= 0) {
			double sqrt = Math.sqrt(disc);
			Vec3 dt = Vec3.add(rd, Vec3.multiply(rc - sqrt, n));
			return dt;
		} else {
			return null;
		}		
	}
	/**
	 * Schlick approximation.
	 */
	public double schlick(Vec3 d, Vec3 n, double n1, double n2) { 
		double r0 = Math.pow(((n1 - n2) / (n1 + n2)), 2); // the reflected part
		return r0 + (1 - r0) * Math.pow(1 + Vec3.dotProduct(n, d), 5);

	}
	/**
	 * Law of reflection
	 * @param d direction
	 * @param n hit - normal vector
	 * @return
	 */
	public Vec3 reflect(Vec3 d,Vec3 n) {
		return Vec3.subtract(d, Vec3.multiply(2, Vec3.multiply(Vec3.dotProduct(n, d), n)));

	}
}