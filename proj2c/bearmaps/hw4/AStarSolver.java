package bearmaps.hw4;


import bearmaps.proj2ab.DoubleMapPQ;
import javafx.scene.paint.Stop;
import edu.princeton.cs.algs4.Stopwatch;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    SolverOutcome outcome;
    LinkedList<Vertex> solution;
    double solutionWeight;
    int numStatesExplored = 0;
    double explorationTime = 0.0;
    HashMap<Vertex, Double> disTo;
    HashMap<Vertex, Vertex> edgeTo;
    double INF = Double.POSITIVE_INFINITY;

    public AStarSolver(AStarGraph<Vertex> input,
                       Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();

        disTo = new HashMap<Vertex, Double>();
        edgeTo = new HashMap<Vertex, Vertex>();
        solution = new LinkedList<Vertex>();
        DoubleMapPQ<Vertex> seePQ = new DoubleMapPQ<Vertex>();


        seePQ.add(start, input.estimatedDistanceToGoal(start, end));
        disTo.put(start, 0.0);

        while (seePQ.size() != 0 && !seePQ.getSmallest().equals(end)
                && explorationTime < timeout) {
            Vertex p = seePQ.removeSmallest();
            numStatesExplored += 1;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e, seePQ, input, end);
            }
            explorationTime = sw.elapsedTime();
        }



        if (seePQ.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            solution.clear();
        } else if (seePQ.getSmallest().equals(end)) {
            Vertex curVertex = seePQ.getSmallest();
            solution.addFirst(curVertex);
            while (!curVertex.equals(start)) {
                solution.addFirst(edgeTo.get(curVertex));
                curVertex = edgeTo.get(curVertex);
            }
            outcome = SolverOutcome.SOLVED;
            solutionWeight = disTo.get(end);
        } else {
            outcome = SolverOutcome.TIMEOUT;
            solution.clear();
        }

    }

    private void relax(WeightedEdge<Vertex> e, DoubleMapPQ<Vertex> seePQ,
                       AStarGraph<Vertex> input, Vertex end) {
        Vertex p = e.from();
        Vertex q = e.to();
        double weight = e.weight();
        double priority;

        if (!disTo.containsKey(q)) disTo.put(q, INF);

        if ((disTo.get(p) + weight) < disTo.get(q)) {

            disTo.put(q, (disTo.get(p) + weight));
            priority = disTo.get(q) + input.estimatedDistanceToGoal(q, end);

            edgeTo.put(q, p);

            if (seePQ.contains(q)) {
                seePQ.changePriority(q, priority);
            } else {
                seePQ.add(q, priority);
            }
        }
    }





    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return solutionWeight;
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return explorationTime;
    }
}
