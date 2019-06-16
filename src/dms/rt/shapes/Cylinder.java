package dms.rt.shapes;

import java.util.Arrays;
import java.lang.Math.*;

import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.shapes.Shape;

/**
 * Class - implements shape - includes Cylindrical object constructors and methods.
 */
public class Cylinder implements Shape{

	private double radius;
	private double height;
	private Vec3 undersurfaceCenterPosition;
	private Material surfaceMaterial;

	/**
	 * Cylindrical shape object, as a usable geometric shape for a scene.
	 * @param radius
	 * @param height
	 * @param undersurfaceCenterPosition
	 * @param surfaceMaterial
	 */
	public Cylinder(double radius, double height, Vec3 undersurfaceCenterPosition, Material surfaceMaterial) {
		this.radius = radius;
		this.height = height;
		this.undersurfaceCenterPosition = undersurfaceCenterPosition;
		this.surfaceMaterial = surfaceMaterial;
	}

	@Override
	public Hit intersect(Ray ray){
		//Calculate Discriminant
		Vec3 origin = Vec3.subtract(ray.getOrigin(), undersurfaceCenterPosition);
		double a = ray.getDirection().x * ray.getDirection().x + ray.getDirection().z * ray.getDirection().z;
		double b = 2 * (ray.getDirection().x * origin.x + ray.getDirection().z * origin.z);
		double c = Math.pow(origin.x, 2) + Math.pow(origin.z, 2) - Math.pow(radius, 2);
		double discriminant = Math.pow(b,  2) - 4 * a * c;

		//Calculate normalVec and t
		if (discriminant >= 0){
			double[] t_list = new double[3];
			t_list[0] = Math.min((-b - Math.sqrt(discriminant)) / (2 * a), (-b + Math.sqrt(discriminant)) / (2 * a));
			t_list[1] = (height / 2 - origin.y) / ray.getDirection().y;
			t_list[2] = (-height / 2 - origin.y) / ray.getDirection().y;

			double[] t_list_sort = new double[3];
			t_list_sort[0] = t_list[0];
			t_list_sort[1] = t_list[1];
			t_list_sort[2] = t_list[2];

			Arrays.sort(t_list_sort);

			Vec3 normalVec = null;
			double t = Double.NEGATIVE_INFINITY;

			//Get the right t and normalVec
			for (double currentT : t_list_sort) {
				Vec3 temp = Vec3.add(ray.getOrigin(), Vec3.multiply(ray.getDirection(), currentT));
				if (currentT == t_list[0]) {
					if (Math.abs(temp.y - undersurfaceCenterPosition.y) < height / 2) {
						normalVec = Vec3.normalize(new Vec3(temp.x - undersurfaceCenterPosition.x, 0, temp.z - undersurfaceCenterPosition.z));
						t = currentT;
						break;
					}
				}
				else {                
					if (Math.pow(temp.x - undersurfaceCenterPosition.x, 2) + Math.pow(temp.z - undersurfaceCenterPosition.z, 2) - Math.pow(radius, 2) <= 0) {
						double y = 1;
						if (currentT == t_list[2])
							y = -y;
						normalVec = Vec3.normalize(new Vec3(0, y, 0));
						t = currentT;
						break;
					}
				}
			}

			//Proof t
			if (ray.isValid(t))
				return new Hit(ray.pointAt(t), normalVec, t, surfaceMaterial, new Vec3(0,0,0));
		}
		return null;
	}
}