package dms.rt.lights;

import cgtools.Vec3;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.shapes.Group;

/**
 * Class - implements LightSource - for a point light
 */
public class PointLight implements LightSource {

	private Vec3 position;
	private Vec3 intensity;

	/**
	 * Creates a directional light with given properties.
	 * @param position
	 * @param intensity
	 */
	public PointLight(Vec3 position, Vec3 intensity) {
		this.position = position;
		this.intensity = intensity;
	}

	@Override
	public LightParameter getLight(Hit hit, Group group) {
		Vec3 hitPoint = hit.getHitPointPos();
		Vec3 incidenceRadiance = Vec3.divide(intensity, Vec3.squaredLength(Vec3.subtract(position, hitPoint)));
		Ray ray = new Ray(hitPoint, Vec3.normalize(Vec3.subtract(position, hitPoint)), 0.00001, Double.MAX_VALUE);

		Hit groupHit = group.intersect(ray);
		if (groupHit == null)
			return new LightParameter(incidenceRadiance, Vec3.normalize(Vec3.subtract(position, hitPoint)));

		else
			return null;
	}
}