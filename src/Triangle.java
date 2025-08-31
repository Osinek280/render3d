import java.util.ArrayList;

public class Triangle {
  public int a, b, c;
  public Vector3D normal;

  public Triangle(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public class Vector3D {
    public float x, y, z;

    public Vector3D(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
  }
}