package bearmaps.proj2ab;

import javax.print.attribute.standard.NumberOfDocuments;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class KDTree implements PointSet{

    private Node root;
    private static final boolean HORIZONTAL = false;// horizontal direction

    private class Node {
        Point point;
        Node left;
        Node right;
        boolean splitDim;

        public Node(Point point, boolean splitDim) {
            this.point = point;
            this.splitDim = splitDim;
        }

        public Point getPoint() {
            return point;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public boolean getSplitDim() {
            return splitDim;
        }

    }

    public KDTree(List<Point> points) {
        for (Point point : points) {
            root = insert(point, root, HORIZONTAL);
        }
    }

    private int comparePoints(Point a, Point b, boolean splitDim) {
        if (splitDim == HORIZONTAL) return Double.compare(a.getX(), b.getX());
        return Double.compare(a.getY(), b.getY());
    }

    private Node insert(Point point, Node node, boolean splitDim) {
        if (node == null) return new Node(point, splitDim);

        if (point.equals(node.getPoint())) return node;

        int cmp = comparePoints(point, node.getPoint(), splitDim);

        if (cmp < 0) {
            node.left = insert(point, node.getLeft(), !splitDim);
        } else {
            node.right = insert(point, node.getRight(), !splitDim);
        }

        return node;
    }

    private boolean isWorthLooking(Node node, Point target, Point best) {
        double disToBest = Point.distance(best, target);
        double disToBad;
        if (node.splitDim == HORIZONTAL) {
            disToBad = Point.distance(new Point(node.getPoint().getX(), target.getY()), target);
        } else {
            disToBad = Point.distance(new Point(target.getX(), node.getPoint().getY()), target);
        }
        return Double.compare(disToBad, disToBest) < 0;
    }

    /**
     * @return the nearest Point of the input Point.
     */
    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        return nearest(root, target, root.getPoint());
    }

    private Point nearest(Node node, Point target, Point best) {
        if (node == null) return best;

        double bestDist = Point.distance(best, target);
        double currDist = Point.distance(node.getPoint(), target);
        if (Double.compare(currDist, bestDist) < 0) {
            best = node.getPoint();
        }

        Node goodSideNode;
        Node badSideNode;
        int cmp = comparePoints(target, node.getPoint(), node.getSplitDim());
        if (cmp < 0) {
            goodSideNode = node.getLeft();
            badSideNode = node.getRight();
        } else {
            goodSideNode = node.getRight();
            badSideNode = node.getLeft();
        }

        best = nearest(goodSideNode, target, best);
        if (isWorthLooking(node, target, best)) {
            best = nearest(badSideNode, target, best);
        }

        return best;
    }
}
