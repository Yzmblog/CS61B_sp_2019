public class Palindrome {
    /**
     * Transform the word to Deque.
     */
    public Deque<Character> wordToDeque(String word) {
        int size = word.length();
        Deque<Character> wordDeque = new ArrayDeque<Character>();
        for(int i = 0; i<size; i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /**
     * Help to isPalindrome.
     */
    private boolean Helper(Deque<Character> wordDeque) {
        if(wordDeque.size() == 0 || wordDeque.size() == 1) {
            return true;
        } else {
            if(wordDeque.removeFirst() == wordDeque.removeLast()) {
                return Helper(wordDeque);
            } else {
                return false;
            }
        }
    }

    /**
     * Judge if the string is a Palindrome.
     */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return Helper(wordDeque);
    }

    /**
     * Help2 to isPalindrome.
     */
    private boolean Helper2(Deque<Character> wordDeque, CharacterComparator cc) {
        if(wordDeque.size() == 0 || wordDeque.size() == 1) {
            return true;
        } else {
            if(cc.equalChars(wordDeque.removeFirst(), wordDeque.removeLast())) {
                return Helper2(wordDeque, cc);
            } else {
                return false;
            }
        }
    }

    /**
     * Overload the isPalindrome.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return Helper2(wordDeque, cc);
    }



}
