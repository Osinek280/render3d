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

  }
}
