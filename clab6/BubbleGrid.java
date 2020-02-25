public class BubbleGrid {
    private int[][] grid;
    private int rowNum;
    private int colNum;
    private int ceiling = 0;
    private int dirs[][] = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        rowNum = grid.length;
        colNum = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        // TODO
        int totalNumber = rowNum * colNum;
        UnionFind uf = new UnionFind(totalNumber + 1);

        for (int[] dart : darts) {
            if(grid[dart[0]][dart[1]] == 1) {
                grid[dart[0]][dart[1]] = 2;
            }
        }

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if(grid[i][j] == 1) {
                    connectToNeibor(i, j, grid, uf);
                }

            }
        }

        int count = uf.sizeOf(ceiling);
        int[] popSet = new int[darts.length];

        for (int i = darts.length - 1; i >= 0; i--) {
            int rowD = darts[i][0];
            int colD = darts[i][1];

            if (grid[rowD][colD] == 2) {

                connectToNeibor(rowD, colD, grid, uf);
                grid[rowD][colD] = 1;

            }
            int newCount = uf.sizeOf(ceiling);

            popSet[i] = newCount > count ? newCount - count - 1 : 0;

            count = newCount;

        }


        return popSet;
    }

    private void connectToNeibor(int row, int col, int[][] grind, UnionFind uf) {
        if(row == 0) {
            uf.union(xyToD1(row, col), ceiling);
        }

        for(int[] dir : dirs) {
            int rowNei = row + dir[0];
            int colNei = col + dir[1];
            if(rowNei < 0 || rowNei == rowNum || colNei < 0 || colNei == colNum || grind[rowNei][colNei] != 1) {
                continue;
            }
            uf.union(xyToD1(row, col), xyToD1(rowNei, colNei));
        }

    }

    private int xyToD1(int row, int col) {
        return row*rowNum + 1 + col;
    }


}
