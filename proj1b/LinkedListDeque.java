public class LinkedListDeque<T> implements Deque<T> {

    /**
     * class of TNode.
     */
    private class TNode {
        private T item;
        private TNode prev;
        private TNode next;

        private TNode(T x, TNode p, TNode n) {
            item = x;
            prev = p;
            next = n;
        }
    }

    /** sentinel of the deque.
     * the first item is sentinel.next(if it exists)
     */
    private TNode sentinel;

    /** Size of the deque. */
    private int size;

    /**
     * Constructor of the deque that takes no argument.
     */
    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Deep copy cnstructor of the deque.
     */
    public LinkedListDeque(LinkedListDeque<T> other) {
        sentinel = new TNode(other.sentinel.item, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        for(int i = 0; i<other.size; i++) {
            addLast((T) other.get(i)); // (T) is cast, since type of other is unknown
        }

    }

    /**
     * Add first element to the deque.
     */
    @Override
    public void addFirst(T item) {
        sentinel.next = new TNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;

    }

    /**
     * Add last element to the deque.
     */
    @Override
    public void addLast(T item) {
        sentinel.prev = new TNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /**
     * Return if the deque is empty.
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Return the number of the items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Print the items in deque.
     */
    @Override
    public void printDeque() {

        for (int i = 0; i < size; i++) {
            System.out.println(get(i) + " ");
         }
        System.out.println();
    }

    /**
     *Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.next.prev = sentinel;
        if (!isEmpty()) {
            size -= 1;
        }
        return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        if (!isEmpty()) {
            size -= 1;
        }
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    @Override
    public T get(int index) {
        int i = 0;
        for (TNode p = sentinel; p !=null; p = p.next) {
            if(i == index) {
                return p.next.item;
            } else {
                    i += 1;
            }
        }
        return null;
    }

    /** Help get item recursively. */
    public T helper(int index, TNode node) {
        if (index == 0) {
            return node.next.item;
        } else {
            return helper(index - 1, node.next);
        }
    }

    /**
     *Gets the item at the given index using recursive method.
     */
    public T getRecursive(int index) {
        return helper(index, sentinel);
    }

}



