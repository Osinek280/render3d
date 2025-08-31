import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Renderer3D {
  ArrayList<Point3D> points;
  ArrayList<Edge> edges;

  ArrayList<Triangle> triangles;

  private final Main renderer;

  int WindowSizeX;

  int WindowSizeY;

  float rotation = 0.0f;
  float FOV = 10.0f;
  float deltaTime = 0.0f;

  public Renderer3D(Main renderer, ArrayList<Point3D> points, ArrayList<Edge> edges, int WindowSizeX, int WindowSizeY, ArrayList<Triangle> triangles) {
    this.triangles = triangles;
    this.renderer = renderer;
    this.points = points;
    this.edges = edges;
    this.WindowSizeX = WindowSizeX;
    this.WindowSizeY = WindowSizeY;
  }

  public void render() {

    long startTime = System.nanoTime();

    Graphics2D g = renderer.getFramebuffer().createGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WindowSizeX, WindowSizeY);
    g.dispose();

    rotation += 1.0f * deltaTime;

    for (Triangle tri : triangles) {
      // Bierz punkty z siatki
      Point3D p0 = rotateY(points.get(tri.a));
      Point3D p1 = rotateY(points.get(tri.b));
      Point3D p2 = rotateY(points.get(tri.c));

      // Projektuj do 2D
      Point2D s0 = projection(p0);
      Point2D s1 = projection(p1);
      Point2D s2 = projection(p2);

      // Narysuj trójkąt

      Graphics2D g2 = renderer.getFramebuffer().createGraphics();

      g2.setColor(Color.DARK_GRAY);
      int[] xPoints = {(int) s0.x, (int) s1.x, (int) s2.x};
      int[] yPoints = {(int) s0.y, (int) s1.y, (int) s2.y};
      g2.fillPolygon(xPoints, yPoints, 3);
      g2.dispose();
    }

    for(Edge edge : edges) {
      Point3D rotatedStartPoint = rotateY(points.get(edge.start));
      Point3D rotatedEndPoint = rotateY(points.get(edge.end));
      Point2D start = projection(rotatedStartPoint);
      Point2D end = projection(rotatedEndPoint);
      renderer.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y, Color.WHITE);
    }


    long endTime = System.nanoTime();
    deltaTime = (endTime - startTime) / 1_000_000_000.0f;
  }

  private Point3D rotateX(Point3D point) {
    Point3D returnPoint = new Point3D(
        point.x,
        (float) (Math.cos(rotation) * point.y - Math.sin(rotation) * point.z),
        (float) (Math.sin(rotation) * point.y + Math.cos(rotation) * point.z)
    );
    return returnPoint;
  }

  private Point3D rotateY(Point3D point) {
    Point3D returnPoint = new Point3D(
        (float) (Math.cos(rotation) * point.x - Math.sin(rotation) * point.z),
        point.y,
        (float) (Math.sin(rotation) * point.x + Math.cos(rotation) * point.z)
    );
    return returnPoint;
  }

  private Point2D projection(Point3D point) {
    return new Point2D(
        (float) WindowSizeX / 2 + (FOV * point.x) /(FOV + point.z) *100,
        (float) WindowSizeY / 2 + (FOV * point.y) /(FOV + point.z)*100
    );
  }
}

