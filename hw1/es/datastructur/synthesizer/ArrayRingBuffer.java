package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.Objects;


public class ArrayRingBuffer<T>  implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;

    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if(isFull()) {
            throw (new RuntimeException("Ring buffer overflow"));
        } else {
            int newLast = (last + 1) % rb.length;
            rb[last] = x;
            last = newLast;
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if(isEmpty()) {
            throw (new RuntimeException("Ring buffer underflow"));
        } else {
            T itemToRe = rb[first];
            first = (first + 1) % rb.length;
            fillCount -= 1;
            return itemToRe;
        }

    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {

        if(isEmpty()) {
            throw (new RuntimeException("Ring buffer underflow"));
        } else {
            return rb[first];
        }
    }

    /**
     *Return size of the buffer
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     *Return number of items currently in the buffer
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Iterator.
     */
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int count; //next times count
        private int curpos; //current pos

        public ArrayRingBufferIterator() {
            curpos = first;
            count = 0;
        }

        /**
         * return if iterator has next.
         */
        public boolean hasNext() {
            return count < fillCount();
        }

        /**
         * return next item. and pos be next pos.
         */
        public T next() {
            int index = curpos;
            curpos = (first + 1) % capacity();
            count += 1;
            return rb[index];
        }
    }

    /**
     * Equals.
     */
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(o == null) {
            return false;
        }
        if(o.getClass() != this.getClass()) {
            return false;
        }

        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if(other.fillCount() != this.fillCount()) {
            return false;
        }

        Iterator<T> thisIter = iterator();
        Iterator<T> otherIter = other.iterator();

        while (thisIter.hasNext() && otherIter.hasNext()) {
            if(thisIter.next() != otherIter.next()) {
                return false;
            }

        }
        return true;

    }

}

