import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExportUtils {

    public static void exportObservations(String filename, Map<Integer, List<double[]>> clusters) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("cluster,f1,f2,f3,f4\n"); // header

            for (Map.Entry<Integer, List<double[]>> entry : clusters.entrySet()) {
                int clusterId = entry.getKey();
                for (double[] point : entry.getValue()) {
                    writer.write(clusterId + "," + point[0] + "," + point[1] + "," + point[2] + "," + point[3] + "\n");
                }
            }

            System.out.println("Observations exported to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportCentroids(String filename, double[][] centroids) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("cluster,f1,f2,f3,f4\n"); // header

            for (int i = 0; i < centroids.length; i++) {
                if (centroids[i] != null) { // skip null centroids (if any)
                    writer.write(i + "," + centroids[i][0] + "," + centroids[i][1] + "," + centroids[i][2] + "," + centroids[i][3] + "\n");
                }
            }

            System.out.println("Centroids exported to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
