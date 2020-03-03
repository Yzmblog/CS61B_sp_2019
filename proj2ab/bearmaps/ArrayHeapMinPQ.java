package bearmaps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> items;
    private HashMap<T, Integer> itemMapIndex;

    private class PriorityNode {
        private T item;
        private double priority;

        PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        T getItem() {
            return item;
        }

        Double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

    }

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        itemMapIndex = new HashMap<>();
    }




    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) throw new IllegalArgumentException();
        items.add(new PriorityNode(item, priority));
        itemMapIndex.put(item, size() - 1);
        climb(size() - 1);

    }

    private void climb(int i) {
        if (i > 0 && smaller(i, parent(i))) {
            swap(i, parent(i));
            climb(parent(i));
        }
    }

    private void swap(int i, int j) {
        PriorityNode temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
        itemMapIndex.put(items.get(i).getItem(), i);
        itemMapIndex.put(items.get(j).getItem(), j);
    }

    private boolean smaller(int i, int j) {
        return items.get(i).getPriority() < items.get(j).getPriority();
    }

    private void sink(int i) {
        int smallest = i;
        if (leftChild(i) <= size() - 1 && smaller(leftChild(i), smallest)) {
            smallest = leftChild(i);
        }
        if (rightChild(i) <= size() - 1 && smaller(rightChild(i), smallest)) {
            smallest = rightChild(i);
        }
        if (smallest != i) {
            swap(i, smallest);
            sink(smallest);
        }
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        if (i == 0) return 0;
        return (i - 1) / 2;
    }

    private boolean isEmpty() {
        return items.isEmpty();
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return itemMapIndex.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (isEmpty()) throw new NoSuchElementException();
        return items.get(0).getItem();
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (isEmpty()) throw new NoSuchElementException();
        T toRemove = items.get(0).getItem();
        swap(0, size() - 1);
        items.remove(size() - 1);
        itemMapIndex.remove(items.get(size() - 1).getItem());
        sink(0);
        return toRemove;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return items.size();

    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (! contains(item)) throw new NoSuchElementException();
        int index = itemMapIndex.get(item);
        double oldP = items.get(index).getPriority();
        items.get(index).setPriority(priority);
        if (priority > oldP) {
            sink(index);
        }
        else {
            climb(index);
        }
    }


}
