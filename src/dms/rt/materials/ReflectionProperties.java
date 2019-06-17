package dms.rt.materials;

import cgtools.Vec3;
import dms.rt.raytracer.Ray;

/**
 * A class for the properties of a reflection
 */
public class ReflectionProperties {

	/**
	 * How much energy does the light source emit (in RGB)
	 */
	public Vec3 emission;
	/**
	 * Color reflection
	 */
	public Vec3 albedo;
	public Ray scatteredRay;

	/**
	 * Sets the properties to the given values.
	 * @param scatteredRay
	 * @param emission
	 * @param albedo
	 */
	public ReflectionProperties(Ray scatteredRay, Vec3 emission, Vec3 albedo) {
		this.scatteredRay = scatteredRay;
		this.emission = emission;
		this.albedo = albedo;
	}
}