package dms.rt.materials;

import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;

public interface Material {
	ReflectionProperties properties(Ray r, Hit h);
}