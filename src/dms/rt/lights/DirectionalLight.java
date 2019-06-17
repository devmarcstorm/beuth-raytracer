package dms.rt.lights;

import cgtools.Vec3;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.shapes.Group;

/**
 * Class - implements LightSource - for a directional light
 */
public class DirectionalLight implements LightSource {

	private Vec3 direction;
	private Vec3 intensity;

	/**
	 * Creates a directional light with given properties.
	 * @param direction
	 * @param intensity
	 */
	public DirectionalLight(Vec3 direction, Vec3 intensity) {
		this.direction = direction;
		this.intensity = intensity;
	}

	@Override
	public LightParameter getLight(Hit hit, Group group) {
		Vec3 hitPoint = hit.getHitPointPos();
		Vec3 normalVector = hit.getnormalVec();
		Vec3 incidenceRadiance = Vec3.multiply(intensity, Vec3.dotProduct(direction, normalVector));
		Ray ray = new Ray(hitPoint, direction, 0.00001, Double.MAX_VALUE);
		
		Hit groupHit = group.intersect(ray);
		if (groupHit == null)
			return new LightParameter(incidenceRadiance, direction);

		else
			return null;
	}
}