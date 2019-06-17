package dms.rt.materials;

import cgtools.Random;
import cgtools.Vec3;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import cgtools.Sampler;


/**
 * Class - implements Material - for a material of a background object that is infinitely far away.
 */
public class BackgroundMaterial implements Material{

	public Sampler emission;
	Vec3 albedo;

	/**
	 * Sets the values for the material.
	 * @param albedo
	 * @param texture
	 */
	public BackgroundMaterial(Vec3 albedo, Sampler texture) {
		this.emission = texture;
		this.albedo = albedo;
	}

	@Override
	public ReflectionProperties properties(Ray ray, Hit hit) {
		double x;
		double y;
		double z;
		while(true) {
			x = 2 * Random.random() - 1;
			y = 2 * Random.random() - 1;
			z = 2 * Random.random() - 1;
			if ((x * x + y * y + z * z) <= 1) {
				break;
			}
		}
		Vec3 d = Vec3.add(hit.getnormalVec(), new Vec3(x, y, z));
		double min = 0.0000001;
		double u = hit.getU();
		double v = hit.getV();
		return new ReflectionProperties(new Ray(hit.getHitPointPos(), d, min, Double.POSITIVE_INFINITY), emission.color(u, v), albedo);
	}
}