import java.io.*;
import java.util.*;

public class FileLoader {

  public static class OBJData {
    public ArrayList<Point3D> vertices = new ArrayList<>();
    public List<float[]> normals = new ArrayList<>();
    public List<float[]> texcoords = new ArrayList<>();
    public List<String[]> faces = new ArrayList<>();
  }

  public static OBJData loadOBJ(String path) throws IOException {
    OBJData data = new OBJData();

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.startsWith("v ")) {
          String[] parts = line.split("\\s+");
          float x = Float.parseFloat(parts[1]);
          float y = Float.parseFloat(parts[2]);
          float z = Float.parseFloat(parts[3]);
          data.vertices.add(new Point3D(x, y, z));
        } else if (line.startsWith("vn ")) {
          String[] parts = line.split("\\s+");
          float x = Float.parseFloat(parts[1]);
          float y = Float.parseFloat(parts[2]);
          float z = Float.parseFloat(parts[3]);
          data.normals.add(new float[]{x, y, z});
        } else if (line.startsWith("vt ")) {
          String[] parts = line.split("\\s+");
          float u = Float.parseFloat(parts[1]);
          float v = Float.parseFloat(parts[2]);
          data.texcoords.add(new float[]{u, v});
        } else if (line.startsWith("f ")) {
          String[] parts = line.split("\\s+");
          String[] faceIndices = Arrays.copyOfRange(parts, 1, parts.length);
          data.faces.add(faceIndices);
        }
      }
    }

    return data;
  }
}
