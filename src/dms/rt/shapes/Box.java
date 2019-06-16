package dms.rt.shapes;

import cgtools.Vec3;
import dms.rt.materials.Material;
import dms.rt.raytracer.Hit;
import dms.rt.raytracer.Ray;

/**
 * A class for a simple box / cube object.
 */
public class Box implements Shape
{

	private Vec3 size_05; // Half of the specified size
	private Vec3 position;
	private Material material;

	/**
	 * Creates a Box object with given properties.
	 * @param size
	 * @param position
	 * @param material
	 */
	public Box(Vec3 size, Vec3 position, Material material)
	{		
		size_05 = Vec3.divide(size, 2);
		this.position = position;
		this.material = material;
	}

	public Hit intersect(Ray ray)
	{
		Vec3 origin = Vec3.subtract(ray.getOrigin(), position);
		double t, t_1, t_2, close, far;

		int indexClose = 0;
		int indexFar = 0;

		if (ray.getDirection().x == 0 && (origin.x < -size_05.x || origin.x > size_05.x))
			return null;
		else {
			close = (-size_05.x - origin.x) / ray.getDirection().x;
			far = (size_05.x - origin.x) / ray.getDirection().x;

			if (close > far) {
				double temp = close;

				close = far;
				far = temp;
			}

			if (close > far || far < 0)
				return null;
		}


		if (ray.getDirection().y == 0 && (origin.y < -size_05.y || origin.y > size_05.y))
			return null;
		else {
			t_1 = (-size_05.y - origin.y) / ray.getDirection().y;
			t_2 = (size_05.y - origin.y) / ray.getDirection().y;

			if (t_1 > t_2) {
				double temp = t_1;

				t_1 = t_2;
				t_2 = temp;
			}

			if (t_1 > close) {
				close = t_1;
				indexClose = 1;
			}

			if (t_2 < far) {
				far = t_2;
				indexFar = 1;
			}

			if (close > far || far < 0)
				return null;
		}


		if (ray.getDirection().z == 0 && (origin.z < -size_05.z || origin.z > size_05.z))
			return null;
		else {
			t_1 = (-size_05.z - origin.z) / ray.getDirection().z;
			t_2 = (size_05.z - origin.z) / ray.getDirection().z;

			if (t_1 > t_2){
				double currT = t_1;

				t_1 = t_2;
				t_2 = currT;
			}

			if (t_1 > close) {
				close = t_1;
				indexClose = 2;
			}

			if (t_2 < far){
				far = t_2;
				indexFar = 2;
			}

			if (close > far || far < 0)
				return null;
		}		

		Vec3[] refVecs = {
				new Vec3(1, 0, 0), 
				new Vec3(0, 1, 0), 
				new Vec3(0, 0, 1)};

		Vec3 normalVec = new Vec3(0, 0, 0);

		if (close <= 0){
			t = far;
			normalVec = refVecs[indexFar];
		}
		else{
			t = close;
			normalVec = refVecs[indexClose];
		}		

		if (ray.isValid(t) == false)
			return null;

		Vec3 hitPoint = ray.pointAt(t);
		Vec3 uvcoords = new Vec3(0, 0, 0);

		return new Hit(hitPoint, normalVec, t, material, uvcoords);
	}

}