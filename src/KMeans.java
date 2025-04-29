import java.util.*;

public class KMeans {
    private final int k;
    private final Map<Integer, List<double[]>> clusters;
    private double[][] centroids;
    private final Random rand;

    public KMeans(int k) {
        this.k = k;
        this.clusters = new HashMap<>();
        this.rand = new Random();
    }

    private void initializeClusters(double[][] data) {
        clusters.clear();
        for (int i = 0; i < k; i++)
            clusters.put(i, new ArrayList<>());

        List<Integer> assignments = new ArrayList<>();
        // guarantee at least one point per cluster
        for (int i = 0; i < k; i++)
            assignments.add(i);

        for (int i = 0; i < data.length; i++)
            assignments.add(rand.nextInt(k));
        Collections.shuffle(assignments);

        for (int i = 0; i < data.length; i++)
            clusters.get(assignments.get(i)).add(data[i]);
    }

    private int findClosestCluster(double[] vector) {
        double minDistance = euclideanDistance(vector, centroids[0]);
        List<Integer> closestIndices = new ArrayList<>();
        closestIndices.add(0);

        for (int i = 1; i < centroids.length; i++) {
            double distance = euclideanDistance(vector, centroids[i]);
            if (distance < minDistance) {
                minDistance = distance;
                closestIndices.clear();
                closestIndices.add(i);
            } else if (distance == minDistance) {
                closestIndices.add(i);
            }
        }

        if (closestIndices.size() == 1) {
            return closestIndices.getFirst();
        } else {
            Random random = new Random();
            return closestIndices.get(random.nextInt(closestIndices.size()));
        }
    }

    private boolean updateCentroids() {
        boolean changed = false;

        // k is the #centroids
        double[][] newCentroids = new double[k][];

        for (int i = 0; i < k; i++) {
            List<double[]> points = clusters.get(i);

            //assigning new points
            double[] newCentroidsPoints = new double[points.get(0).length];
            for (double[] point : points) {
                for (int dim = 0; dim < point.length; dim++)
                    newCentroidsPoints[dim] += point[dim];
            }

            //normalizing
            for (int dim = 0; dim < newCentroidsPoints.length; dim++)
                newCentroidsPoints[dim] /= points.size();

            if(centroids == null || !Arrays.equals(centroids[i], newCentroidsPoints))
                changed = true;

            newCentroids[i] = newCentroidsPoints;
        }
        centroids = newCentroids;
        return changed;
    }

    private void reassignClusters(double[][] data) {
        clusters.clear();
        for (int i = 0; i < k; i++)
            clusters.put(i, new ArrayList<>());

        for(double[] vector : data) {
            int clusterIndex = findClosestCluster(vector);
            clusters.get(clusterIndex).add(vector);
        }
    }

    public void fit(double[][] data) {
        initializeClusters(data);
        boolean centroidsChanged = true;

        while(centroidsChanged){
            reassignClusters(data);
            centroidsChanged = updateCentroids();
        }
    }

    private double euclideanDistance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    public Map<Integer, List<double[]>> getClusters() {
        return clusters;
    }

    public double[][] getCentroids() {
        return centroids;
    }
}
