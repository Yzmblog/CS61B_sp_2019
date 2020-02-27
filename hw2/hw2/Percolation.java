package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int top = 0;

    private int open = 0;

    private int blocked = -1;

    private int[][] grid;

    private int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private int openNum = 0;

    private int rowNum;

    private WeightedQuickUnionUF uf;

    /**
     * create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {
        uf = new WeightedQuickUnionUF(N * N + 1);
        rowNum = N;
        grid = new int[N][N];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = blocked;
            }
        }
    }

    /**
     *open the site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (grid[row][col] == blocked) {

            grid[row][col] = open;

            if (row == 0) uf.union(xyToD1(row, col), top);

            for (int[] dir : dirs) {
                int rowNei = row + dir[0];
                int colNei = col + dir[1];

                if (rowNei >= 0 && rowNei < rowNum
                        && colNei >= 0 && colNei < rowNum
                        && grid[rowNei][colNei] == open) {
                    uf.union(xyToD1(row, col), xyToD1(rowNei, colNei));
                }

            }
            openNum += 1;
        }

    }

    /**
     *is the site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        return grid[row][col] == open;
    }

    /**
     *is the site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        return uf.connected(xyToD1(row, col), top) && isOpen(row, col);
    }

    /**
     *number of open sites
     */
    public int numberOfOpenSites() {
        return openNum;

    }

    /**
     *does the system percolate?
     */
    public boolean percolates() {
        int row = rowNum - 1;
        for (int i = 0; i < rowNum; i++) {
            if(uf.connected(xyToD1(row, i), top)) return true;
        }
        return false;
    }

    /** transform xy to D1 */
    private int xyToD1(int row, int col) {
        return row * rowNum + 1 + col;
    }

    /**
     *use for unit testing (not required, but keep this here for the autograder)
     */
    public static void main(String[] args) {
    }
}
