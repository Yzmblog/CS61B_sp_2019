package hw2;

import java.util.Random;

public class PercolationStats {
    private double[] x;
    private int Times;

    /**
     *perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        x = new double[T];
        Times = T;

        for (int i = 0; i < T; i++) {
            Percolation pl = pf.make(N);
            while (!pl.percolates()) {
                Random random = new Random();
                int ranRow = random.nextInt(N);
                int rancol = random.nextInt(N);
                pl.open(ranRow, rancol);
            }

            x[i] = (double)pl.numberOfOpenSites() / (N * N);

        }
    }

    /**
     *sample mean of percolation threshold
     */
    public double mean() {
        double total = 0;
        for (double v : x) {
            total += v;
        }
        return total / Times;
    }

    /**
     *sample standard deviation of percolation threshold
     */
    public double stddev() {
        double[] dq = new double[Times];
        double mean = mean();
        double total = 0;

        for (int i = 0; i < Times; i++) {
            dq[i] = Math.pow((x[i] - mean), 2);
        }

        for (double u : dq) {
            total += u;
        }

        return Math.sqrt(total / (Times - 1));
    }

    /**
     *low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(Times));

    }

    /**
     *high endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(Times));
    }


}
