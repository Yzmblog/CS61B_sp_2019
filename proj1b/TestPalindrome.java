import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    /**
     * Test isPalindrome.
     */
    @Test
    public void testisPalindrome() {
        String test1 = "a";
        String test2 = "racecar";
        String test3 = "noon";
        String test4 = "horse";
        String test5 = "aaab";

        assertTrue(palindrome.isPalindrome(test1));
        assertTrue(palindrome.isPalindrome(test2));
        assertTrue(palindrome.isPalindrome(test3));
        assertFalse(palindrome.isPalindrome(test4));
        assertFalse(palindrome.isPalindrome(test5));

    }

    /**
     * Test the overloaded isPalindrome.
     */
    @Test
    public void testisPalindrome2() {
        CharacterComparator c = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", c));
        assertFalse(palindrome.isPalindrome("abc", c));
        assertTrue(palindrome.isPalindrome("a", c));


    }
}