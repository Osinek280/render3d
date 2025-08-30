import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
    int width = 800;
    int height = 600;

    JFrame frame = new JFrame("Java Software Renderer");
    Main renderer = new Main(width, height);
    frame.add(renderer);
    frame.setSize(width, height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    // TEST: narysuj przekątną
    for (int i = 0; i < Math.min(width, height); i++) {
      renderer.setPixel(i, i, Color.RED);
      try { Thread.sleep(5); } catch (InterruptedException ignored) {}
    }
  }
}
