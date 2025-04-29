import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "resources/IRIS.csv";
        double[][] data = getData(filePath);

        if(data == null) {
            System.out.println("No data found");
            return;
        }

        // #clusters because we have 3 species
        int k = 3;
        KMeans kMeans = new KMeans(k);
        kMeans.fit(data);

        Map<Integer, List<double[]>> clusters = kMeans.getClusters();
        double[][] centroids = kMeans.getCentroids();

        double rss = EvaluationMetrics.calculateRSS(clusters, centroids);
        System.out.printf("RSS: %.3f%n", rss);
    }

    private static double[][] getData(String filePath) {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            //skipping header line
            br.readLine();
            return br.lines()
                    .map(line -> {
                        String[] split = line.split(",");
                        //not getting species part
                        double[] vector = new double[split.length - 1];
                        for (int i = 0; i < vector.length; i++)
                            vector[i] = Double.parseDouble(split[i]);
                        return vector;
                    }).toArray(double[][]::new);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
