import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel {

  private final BufferedImage framebuffer;

  public Main(int width, int height) {
    framebuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = framebuffer.createGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, width, height);
    g.dispose();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(framebuffer, 0, 0, null);
  }

  public void setPixel(int x, int y, Color color) {
    if (x >= 0 && y >= 0 && x < framebuffer.getWidth() && y < framebuffer.getHeight()) {
      framebuffer.setRGB(x, y, color.getRGB());
      repaint();
    }
  }

  public void drawLine(int x0, int y0, int x1, int y1, Color color) {
    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);
    int sx = x0 < x1 ? 1 : -1;
    int sy = y0 < y1 ? 1 : -1;
    int err = dx - dy;

    while (true) {
      setPixel(x0, y0, color); // rysujemy piksel
      if (x0 == x1 && y0 == y1) break;
      int e2 = 2 * err;
      if (e2 > -dy) {
        err -= dy;
        x0 += sx;
      }
      if (e2 < dx) {
        err += dx;
        y0 += sy;
      }
    }
  }

  public BufferedImage getFramebuffer() {
    return framebuffer;
  }


  public static void main(String[] args) {

    ArrayList<Point3D> points = new ArrayList<>(Arrays.asList(
        new Point3D(-1.0f, -1.0f, -1.0f),
        new Point3D(-1.0f, -1.0f, 1.0f),
        new Point3D(1.0f, -1.0f, -1.0f),
        new Point3D(-1.0f, 1.0f, -1.0f),
        new Point3D(-1.0f, 1.0f, 1.0f),
        new Point3D(1.0f, -1.0f, 1.0f),
        new Point3D(1.0f, 1.0f, -1.0f),
        new Point3D(1.0f, 1.0f, 1.0f)
    ));

    ArrayList<Edge> edges = new ArrayList<>(Arrays.asList(
        new Edge(0, 1),
        new Edge(0, 2),
        new Edge(0, 3),
        new Edge(2, 5),
        new Edge(3, 6),
        new Edge(3, 4),
        new Edge(4, 7),
        new Edge(6, 7),
        new Edge(7, 5),
        new Edge(5, 1),
        new Edge(4, 1),
        new Edge(2, 6)
    ));

    for (Point3D point : points) {
      System.out.println("x: " + point.x + " y: " + point.y + " z: " + point.z);
    }

    int width = 800;
    int height = 600;

    JFrame frame = new JFrame("Java Software Renderer");
    Main renderer = new Main(width, height);
    frame.add(renderer);
    frame.setSize(width, height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    boolean running = true;
    while (running) {
      Renderer3D renderer3D = new Renderer3D(renderer, points, edges, width, height);

      renderer3D.render();
    }
  }
}
