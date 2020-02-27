package hw2;

public class test {
    public static void main(String args[]) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(10, 10, pf);
        System.out.println(ps.mean());
    }
}
