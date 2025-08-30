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

    long startTime = System.nanoTime();

    Graphics2D g = renderer.getFramebuffer().createGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, WindowSizeX, WindowSizeY);
    g.dispose();

    rotation += 1.0f * deltaTime;

    for(Edge edge : edges) {
      Point3D rotatedStartPoint = rotateX(points.get(edge.start));
      Point3D rotatedEndPoint = rotateX(points.get(edge.end));
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

