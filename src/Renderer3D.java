import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Renderer3D {
  ArrayList<Point3D> points;
  ArrayList<Edge> edges;

  private Main renderer;

  private BufferedImage framebuffer;

  int WindowSizeX;

  int WindowSizeY;

  float rotation = 0.0f;
  float FOV = 10.0f;
  float deltaTime = 0.0f;

  public Renderer3D(Main renderer, ArrayList<Point3D> points, ArrayList<Edge> edges, int WindowSizeX, int WindowSizeY) {
    this.renderer = renderer;
    this.points = points;
    this.edges = edges;
    this.WindowSizeX = WindowSizeX;
    this.WindowSizeY = WindowSizeY;
    this.framebuffer = renderer.getFramebuffer();
  }

  public void render() {
    for(Edge edge : edges) {
      Point3D rotatedStartPoint = rotateX(rotateY(points.get(edge.start)));
      Point3D rotatedEndPoint = rotateX(rotateY(points.get(edge.end)));
      Point2D start = projection(rotatedStartPoint);
      Point2D end = projection(rotatedEndPoint);
      renderer.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y, Color.WHITE);
    }
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

