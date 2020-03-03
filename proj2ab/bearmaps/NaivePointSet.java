package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet{
    private List<Point> points;

    public NaivePointSet(List<Point> pointSet) {
        points = pointSet;
    }

    @Override
    public Point nearest(double x, double y) {
        Point sourcePoint = new Point(x, y);
        Point nearestPoint = points.get(0);

        double shortestDis = Point.distance(sourcePoint, nearestPoint);

        for (Point point : points) {
            double dis = Point.distance(sourcePoint, point);
            if (dis < shortestDis) {
                shortestDis = dis;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }


    public static void main(String[] args) {
        Point p1 = new Point(1.0, 0);
        Point p2 = new Point(1.0, 1.0);
        Point p3 = new Point(-3.0, -4.0);
        NaivePointSet nps = new NaivePointSet(List.of(p1, p2, p3));

        System.out.println(nps.nearest(0.0, 0.0)); //should print point(1,0)
    }
}
