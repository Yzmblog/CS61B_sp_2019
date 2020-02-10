public class ArrayDeque<T> {
    private T[] array;
    private int size;
    private int nextFirst;
    private int nextLast;


    /**
     * Creates an empty array deque.
     */
    public ArrayDeque() {
        array = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    /**
     * Creates a deep copy of other.
     */
    public ArrayDeque(ArrayDeque<T> other) {
        array = (T[]) new Object[other.size];
        size = other.size;
        System.arraycopy(other.array, 0, array, 0, size);
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
    }

    /**
     * Return if it is full.
     */
    public boolean isFull() {
        return (size == array.length);
    }

    /**
     * Whether to downsize the deque.
     */
    private boolean isSparse() {
        return array.length >= 16 && size < (array.length / 4);
    }

    /**
     * Index plus one.
     */
    public int Plus(int index) {
        return (index+1) % array.length;
    }

    /**
     * Index minus one;
     */
    public int Minus(int index) {
        return (index - 1 + array.length) % array.length;
    }

    /**
     * Resize the array. When sparse and too small to contain new content.
     */
    public void Resize(int cap) {
        T[] newA = (T[]) new Object[cap];
        int oldIndex = Plus(nextFirst);
        for(int i = 0; i < size; i++) {
            newA[i] = array[oldIndex];
            oldIndex = Plus(oldIndex);
        }
        array = newA;
        nextFirst = Minus(0);
        nextLast = size;

    }


    /**
     * Adds an item of type T to the front of the deque.
     */
    public void addFirst(T item) {
        if (isFull()) {
            Upsize();
        }
        array[nextFirst] = item;
        nextFirst = Minus(nextFirst);
        size += 1;
    }

    /**
     * Adds an xvbo the back of the deque.
     */
    public void addLast(T item) {
        if (size == array.length) {
            Upsize();
        }
        array[nextLast] = item;
        nextLast = Plus(nextLast);
        size += 1;
    }

    /**
     *  Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque(){
        for(int i = 0; i < size; i++) {
            System.out.println(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if(!isEmpty()) {
            if(isSparse()) {
                Downsize();
            }
            T toReturn = array[Plus(nextFirst)];
            nextFirst = Plus(nextFirst);
            size -= 1;
        }

        return null;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if(!isEmpty()) {
            if(isSparse()) {
                Downsize();
            }
            T toReturn = array[Minus(nextLast)];
            nextLast = Minus(nextLast);

            size -= 1;
        }

        return null;
    }

    /**
     *     Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *     If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if(index >= size) {
            return null;
        }
        int start = Plus(nextFirst);
        return array[(start + index) % array.length];
    }

    /**
     * Upsize the deque.
     */
    private void Upsize() {
        Resize(size * 2);
    }

    /**
     * Downsize the deque.
     */
    private void Downsize() {
        Resize(array.length / 2);
    }

}



