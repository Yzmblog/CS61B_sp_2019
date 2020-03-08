import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> tas0 = new Queue<Integer>();
        tas0.enqueue(5);
        tas0.enqueue(3);
        tas0.enqueue(4);
        tas0.enqueue(1);
        tas0.enqueue(99);
        Queue<Integer> actual = new Queue<>();
        actual = QuickSort.quickSort(tas0);
        assertTrue(isSorted(actual));


        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> actual1 = new Queue<>();
        actual1 = QuickSort.quickSort(tas);
        assertTrue(isSorted(actual1));
        assertNotEquals(null, actual1);

    }

    @Test
    public void testMergeSort() {
        Queue<Integer> tas0 = new Queue<Integer>();
        tas0.enqueue(5);
        tas0.enqueue(3);
        tas0.enqueue(4);
        tas0.enqueue(1);
        tas0.enqueue(99);
        Queue<Integer> actual = new Queue<>();
        actual = MergeSort.mergeSort(tas0);
        assertTrue(isSorted(actual));
        assertFalse(isSorted(tas0));

        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        Queue<String> actual1 = new Queue<>();
        actual1 = MergeSort.mergeSort(tas);
        assertTrue(isSorted(actual1));
        assertNotEquals(null, actual1);
        assertFalse(isSorted(tas));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
