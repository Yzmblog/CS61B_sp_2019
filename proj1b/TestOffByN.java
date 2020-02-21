import static org.junit.Assert.*;
import org.junit.Test;


public class TestOffByN {

    /**
     * TestOffBtN's equalsChar.
     */
    @Test
    public void testequalsChar() {
        OffByN ob5 = new OffByN(5);
        assertTrue(ob5.equalChars('a', 'f'));
        assertTrue(ob5.equalChars('f', 'a'));
        assertFalse(ob5.equalChars('f', 'h'));
    }
}
