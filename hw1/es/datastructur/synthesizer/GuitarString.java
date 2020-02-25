package es.datastructur.synthesizer;


import java.util.Random;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        buffer = new ArrayRingBuffer<Double>((int)Math.round(SR / frequency));
        for(int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        for(int i = 0; i < buffer.capacity(); i++) {
            buffer.dequeue();
            double addNum = Math.random() - 0.5;
            buffer.enqueue(addNum);
        }
    }

    /* Advance the simulation 'one time step' by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {


        double first = buffer.dequeue();
        double second = buffer.peek();
        double addNum = (first + second) * 0.5 * DECAY;
        buffer.enqueue(addNum);

    }

    /* Return the double at the front of the buffer. */
    public double sample() {

        return buffer.peek();
    }

}

