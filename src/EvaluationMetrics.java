import java.util.List;
import java.util.Map;

public class EvaluationMetrics {
    public static double calculateRSS(Map<Integer, List<double[]>> clusters,
                                      double[][] centroids){
        double rss = 0.0;
        for(Map.Entry<Integer, List<double[]>> entry : clusters.entrySet()){
            double[] centroid = centroids[entry.getKey()];
            List<double[]> points = entry.getValue();

            for(double[] point : points)
                rss += squaredDistance(point, centroid);
        }
        return rss;
    }

    private static double squaredDistance(double[] point1, double[] point2) {
        double sum = 0.0;
        if(point1.length != point2.length)
            throw new IllegalArgumentException("point1 and point2 must have same length");

        for (int i = 0; i < point1.length; i++)
            sum += Math.pow(point1[i] - point2[i], 2);

        return sum;
    }
}
