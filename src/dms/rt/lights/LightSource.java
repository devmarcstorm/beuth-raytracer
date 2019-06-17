package dms.rt.lights;

import dms.rt.raytracer.Hit;
import dms.rt.shapes.Group;

public interface LightSource {
	public LightParameter getLight(Hit hit, Group group);
}