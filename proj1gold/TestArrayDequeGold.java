import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void test() {
        StudentArrayDeque<Integer> initWrong = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> initRight = new ArrayDequeSolution<Integer>();
        String message = "";

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                initWrong.addLast(i);
                initRight.addLast(i);
                Integer expected = initRight.get(i);
                Integer actual = initWrong.get(i);
                assertEquals(message, expected, actual);
                message += "addLast" + "(" + i + ")\n" ;

            } else {
                initWrong.addFirst(i);
                initRight.addFirst(i);
                Integer expected = initRight.get(i);
                Integer actual = initWrong.get(i);
                assertEquals(message, expected, actual);
                message += "addFirst" + "(" + i + ")\n";

            }
        }

        /** Test removeFirst(). */
        for(int i = 0; i<10; i++)
        {
            Integer expected = initRight.removeFirst();
            Integer actual = initWrong.removeFirst();
            assertEquals(message, expected, actual);
            message += "removeFirst()\n";


        }


        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                initWrong.addLast(i);
                initRight.addLast(i);
            } else {
                initWrong.addFirst(i);
                initRight.addFirst(i);
            }
        }



        /** Test removeLast(). */
        for(int i = 0; i<10; i++)
        {
            Integer expected = initRight.removeLast();
            Integer actual = initWrong.removeLast();
            if(expected != actual) {
                message += "removeLast()\n";
            }

            assertEquals(message, expected, actual);

        }


    }
}
