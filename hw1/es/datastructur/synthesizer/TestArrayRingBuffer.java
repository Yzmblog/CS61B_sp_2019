package es.datastructur.synthesizer;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer<Double>(4);

        arb.enqueue(33.1); // 33.1 null null  null
        arb.enqueue(44.8); // 33.1 44.8 null  null
        arb.enqueue(62.3); // 33.1 44.8 62.3  null
        arb.enqueue(-3.4); // 33.1 44.8 62.3 -3.4

        assertEquals(true, arb.isFull());
        assertEquals(false, arb.isEmpty());

        double expected = 33.1;
        double actual = arb.dequeue();
        assertEquals(expected, actual, 0.01);     // 44.8 62.3 -3.4  null (returns 33.1)

        expected = 44.8;
        actual = arb.dequeue();
        assertEquals(expected, actual, 0.01); // 62.3 -3.4 null null

        expected = 62.3;
        actual = arb.peek();
        assertEquals(expected, actual, 0.01);

    }
}
