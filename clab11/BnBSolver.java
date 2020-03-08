import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> sortedBears;
    private List<Bed> sortedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        // TODO: Fix me.
        quicksort(bears, beds);
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // TODO: Fix me.
        return sortedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // TODO: Fix me.
        return sortedBeds;
    }


    private List<Bear> catenateBears (List<Bear> bears1, List<Bear> bears2) {
        for (Bear bear : bears2) {
            bears1.add(bear);
        }
        return bears1;
    }

    private List<Bed> catenateBeds (List<Bed> beds1, List<Bed> beds2) {
        for (Bed bed : beds2) {
            beds1.add(bed);
        }
        return beds1;
    }

    private void PartitionBear(List<Bear> less, List<Bear> equal, List<Bear> greater,
                               List<Bear> unsorted, Bed pivot) {
        for (Bear bear : unsorted) {
            int cmp = bear.compareTo(pivot);
            if (cmp > 0)         greater.add(bear);
            else if (cmp < 0)    less.add(bear);
            else                 equal.add(bear);
        }
    }

    private void PartitionBed(List<Bed> less, List<Bed> equal, List<Bed> greater,
                               List<Bed> unsorted, Bear pivot) {
        for (Bed bed : unsorted) {
            int cmp = bed.compareTo(pivot);
            if (cmp > 0)         greater.add(bed);
            else if (cmp < 0)    less.add(bed);
            else                 equal.add(bed);
        }
    }


    private Pair<List<Bear>, List<Bed>> quicksort(List<Bear> bears, List<Bed> beds) {
        if (bears.size() <= 1 || beds.size() <=1) {
            return new Pair<>(bears, beds);
        }

        Bed pivotBed = beds.get(0);
        List<Bear> lessBear = new ArrayList<>();
        List<Bear> equalBear = new ArrayList<>();
        List<Bear> greaterBear = new ArrayList<>();

        Bear pivotBear;
        List<Bed> lessBed = new ArrayList<>();
        List<Bed> equalBed = new ArrayList<>();
        List<Bed> greaterBed = new ArrayList<>();

        PartitionBear(lessBear, equalBear, greaterBear, bears, pivotBed);
        pivotBear = equalBear.get(0);
        PartitionBed(lessBed, equalBed, greaterBed, beds, pivotBear);

        Pair<List<Bear>, List<Bed>> less = quicksort(lessBear, lessBed);
        lessBear = less.first();
        lessBed = less.second();

        Pair<List<Bear>, List<Bed>> greater = quicksort(greaterBear, greaterBed);
        greaterBear = greater.first();
        greaterBed = greater.second();

        sortedBears = catenateBears(catenateBears(lessBear, equalBear), greaterBear);
        sortedBeds = catenateBeds(catenateBeds(lessBed, equalBed), greaterBed);

        return new Pair<>(sortedBears, sortedBeds);
    }

}
