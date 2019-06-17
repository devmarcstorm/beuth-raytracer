package dms.rt.materials;

import cgtools.Random;
import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.materials.ReflectionProperties;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import cgtools.Sampler;

/**
 * Class - implements Material - for a polished metal material.
 */
public class PolishedMetalMaterial implements Material{

	public Vec3 emission;
	public Sampler albedo;
	public double roughness;

	/**
	 * Sets the values for the material.
	 * @param roughness
	 * @param albedo
	 */
	public PolishedMetalMaterial(double roughness, Sampler albedo) {
		this.emission = new Vec3(0,0,0);
		this.albedo = albedo;
		this.roughness = roughness;
	}

	@Override
	public ReflectionProperties properties(Ray ray, Hit hit) {
		Vec3 d = ray.getDirection();
		Vec3 n = hit.getnormalVec();
		Vec3 r = Vec3.subtract(d, Vec3.multiply(2, Vec3.multiply(Vec3.dotProduct(n, d), n)));

		double x;
		double y;
		double z;
		while(true) {
			x = (2 * roughness) * Random.random() - roughness;
			y = (2 * roughness) * Random.random() - roughness;
			z = (2 * roughness) * Random.random() - roughness;
			if ((x * x + y * y + z * z) <= 1) {
				break;
			}
		}

		r = Vec3.add(r, Vec3.multiply(roughness, new Vec3(x, y, z)));
		double min = 0.0000001;
		Ray scatteredRay = new Ray(hit.getHitPointPos(), r, min, Double.POSITIVE_INFINITY);

		double u = hit.getU();
		double v = hit.getV();
		return new ReflectionProperties(scatteredRay, emission, albedo.color(u, v));
	}

}