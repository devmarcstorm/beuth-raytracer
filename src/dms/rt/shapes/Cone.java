package dms.rt.shapes;

import java.util.Arrays;

import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;
import dms.rt.shapes.Shape;

/**
 * Class - implements shape - includes Cone object constructors and methods.
 */
public class Cone implements Shape{

	private double radius;
	private double height;
	private Vec3 undersurfaceCenterPosition;
	private Material surfaceMaterial;

	/**
	 * Cone shape object, as a usable geometric shape for a scene.
	 * @param radius
	 * @param height
	 * @param undersurfaceCenterPosition
	 * @param surfaceMaterial
	 */
	public Cone (double radius, double height, Vec3 undersurfaceCenterPosition, Material surfaceMaterial) {
		this.radius = radius;
		this.height = height;
		this.undersurfaceCenterPosition = undersurfaceCenterPosition;
		this.surfaceMaterial = surfaceMaterial;
	}

	@Override
	public Hit intersect(Ray ray) {

		//Calculate ConeVariables
		double coneForm = Math.pow(radius, 2) / Math.pow(height, 2);
		//Calculate Discriminant
		Vec3 origin = Vec3.subtract(ray.getOrigin(), undersurfaceCenterPosition);
		double a = Math.pow(ray.getDirection().x, 2) + Math.pow(ray.getDirection().z, 2) - coneForm * Math.pow(ray.getDirection().y, 2);
		double b = 2 * (ray.getDirection().x * origin.x + ray.getDirection().z * origin.z - coneForm * (origin.y - height) * ray.getDirection().y);
		double c = Math.pow(origin.x, 2) + Math.pow(origin.z, 2) - coneForm * Math.pow(origin.y - height, 2);
		double discriminant = Math.pow(b,  2) - 4 * a * c;

		if (discriminant >= 0){
			//Calculate normalVec and t
			double[] t_list = new double[4];
			t_list[0] = Math.min((-b + Math.sqrt(discriminant)) / (2 * a), (-b + Math.sqrt(discriminant)) / (2 * a));
			t_list[1] = Math.min((-b - Math.sqrt(discriminant)) / (2 * a), (-b + Math.sqrt(discriminant)) / (2 * a));
			t_list[2] = (height - origin.y) / ray.getDirection().y;
			t_list[3] = (-origin.y) / ray.getDirection().y;

			double[] t_list_sort = new double[4];
			t_list_sort[0] = t_list[0];
			t_list_sort[1] = t_list[1];
			t_list_sort[2] = t_list[2];
			t_list_sort[3] = t_list[3];

			Arrays.sort(t_list_sort);

			Vec3 normalVec = null;
			double t = Double.NEGATIVE_INFINITY;

			//Get the right t and normalVec
			for (double currentT : t_list_sort) {
				Vec3 temp = Vec3.add(ray.getOrigin(), Vec3.multiply(ray.getDirection(), currentT));
				if (currentT == t_list[0] || currentT == t_list[1]) {
					if (Math.abs(temp.y - (undersurfaceCenterPosition.y + height / 2)) < height / 2) {
						normalVec = Vec3.normalize(Vec3.add(new Vec3(0, radius / height, 0), Vec3.normalize(new Vec3(temp.x - undersurfaceCenterPosition.x, 0, temp.z - undersurfaceCenterPosition.z))));
						t = currentT;
						break;
					}
				}

				else if (currentT == t_list[2]) {
					if (Math.pow(temp.x - undersurfaceCenterPosition.x, 2) + Math.pow(temp.z - undersurfaceCenterPosition.z, 2) <= 0) {
						normalVec = Vec3.normalize(new Vec3(0, 1, 0));
						t = currentT;
						break;
					}
				}
				else if (currentT == t_list[3]) {
					if (Math.pow(temp.x - undersurfaceCenterPosition.x, 2) + Math.pow(temp.z - undersurfaceCenterPosition.z, 2) - Math.pow((radius / height) * (height), 2) <= 0) {
						normalVec = Vec3.normalize(new Vec3(0, -1, 0));
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
