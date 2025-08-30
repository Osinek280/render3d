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
      Point2D start = projection(points.get(edge.start));
      Point2D end = projection(points.get(edge.end));
      renderer.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y, Color.WHITE);
    }
  }

  private Point2D projection(Point3D point) {
    return new Point2D(
        (float) WindowSizeX / 2 + (FOV * point.x) /(FOV + point.z) *100,
        (float) WindowSizeY / 2 + (FOV * point.y) /(FOV + point.z)*100
    );
  }
}

