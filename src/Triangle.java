import java.util.ArrayList;

public class Triangle {
  public int a, b, c;
  public Vector3D normal;

  public Triangle(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.normal = new Vector3D(0, 0, 0);
  }

  public void computeNormal(ArrayList<Point3D> points) {
    Point3D p0 = points.get(a);
    Point3D p1 = points.get(b);
    Point3D p2 = points.get(c);

    Vector3D u = new Vector3D(p1.x - p0.x, p1.y - p0.y, p1.z - p0.z);
    Vector3D v = new Vector3D(p2.x - p0.x, p2.y - p0.y, p2.z - p0.z);

    normal = u.cross(v);

    normal.normalize();
  }

  public class Vector3D {
    public float x, y, z;

    public Vector3D(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    public Vector3D cross(Vector3D v) {
      return new Vector3D(
          this.y * v.z - this.z * v.y,
          this.z * v.x - this.x * v.z,
          this.x * v.y - this.y * v.x
      );
    }

    public void normalize() {
      float length = (float)Math.sqrt(x*x + y*y + z*z);
      if (length != 0) {
        x /= length;
        y /= length;
        z /= length;
      }
    }
  }
}