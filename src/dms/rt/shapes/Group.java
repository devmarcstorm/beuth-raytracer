package dms.rt.shapes;

import cgtools.Mat4;
import cgtools.Vec3;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.raytracer.Transformation;
import dms.rt.shapes.Shape;


/**
 * A class for a group object. 
 * A group of shapes.
 */
public class Group implements Shape {

	Shape[] shapes;
	private Transformation transformation;

	/**
	 * Creates a group with given shapes and transformation.
	 * @param transformationMatrix
	 * @param shapes
	 */
	public Group(Mat4 transformationMatrix, Shape...shapes) {
		transformation = new Transformation(transformationMatrix);
		this.shapes = shapes;
	}

	/**
	 * Creates a group with given shapes.
	 * @param shapes
	 */
	public Group(Shape...shapes) {
		transformation = new Transformation(Mat4.translate(new Vec3(0,0,0)));
		this.shapes = shapes;
	}

	@Override
	public Hit intersect(Ray ray) {
		double t = Double.POSITIVE_INFINITY;
		ray = new Ray(
				transformation.fromWorld.transformPoint(ray.getOrigin()),
				transformation.fromWorld.transformDirection(ray.getDirection()), 
				ray.getTmin(), 
				ray.getTmax());  
		Hit hit = null;
		for (Shape shape : shapes) {
			Hit hitShape = shape.intersect(ray);
			if (hitShape != null)
				if (hitShape.getT() <= t) {
					hit = hitShape;
					t = hit.getT();
				}
		}
		if (hit != null) {
			hit.setHitPointPos(transformation.fromWorld.transformPoint(hit.getHitPointPos()));
			hit.setNormalVec(transformation.toWorldN.transformDirection(hit.getnormalVec()));
		}
		return hit;
	}
}