package dms.rt.lights;

import cgtools.Vec3;

/**
 * This class contains the parameters for the direction of incidence and radiance of a light source.
 */
public class LightParameter {

	private Vec3 incidenceRadiance;
	private Vec3 incidenceDirection;


	/**
	 * Sets parameters to the given values.
	 * @param incidenceRadiance
	 * @param incidenceDirection
	 */
	public LightParameter(Vec3 incidenceRadiance, Vec3 incidenceDirection) {
		this.incidenceRadiance = incidenceRadiance;
		this.incidenceDirection = incidenceDirection;
	}

	/**
	 * @return incidenceRadiance
	 */
	public Vec3 getIncidenceRadiance() {
		return incidenceRadiance;
	}


	/**
	 * @param incidenceRadiance
	 */
	public void setIncidenceRadiance(Vec3 incidenceRadiance) {
		this.incidenceRadiance = incidenceRadiance;
	}

	/**
	 * @return incidenceDirection
	 */
	public Vec3 getIncidenceDirection() {
		return incidenceDirection;
	}

	/**
	 * @param incidenceDirection
	 */
	public void setIncidenceDirection(Vec3 incidenceDirection) {
		this.incidenceDirection = incidenceDirection;
	}
}