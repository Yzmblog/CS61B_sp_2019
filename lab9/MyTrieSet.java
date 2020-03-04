import javax.print.attribute.standard.NumberOfDocuments;
import java.util.*;

public class MyTrieSet implements TrieSet61B{
    private Node root;

    private class Node {
        private char nodeChar;
        private boolean isKey;
        private Map<Character, Node> map;

        public Node(char c, boolean isKey) {
            nodeChar = c;
            this.isKey = isKey;
            map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node('\0', false);
    }


    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = null;
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() == 0 || root == null) {
            return false;
        }

        Node curr = root;
        Node next = null;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            next = curr.map.get(c);
            if (next == null) {
                return false;
            }
            curr = next;
        }
        return curr.isKey;
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }


    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null || prefix.length() == 0 || root == null) {
            throw new IllegalArgumentException();
        }

        Node curr = root;
        List<String> result = new ArrayList<String>();

        for (int i = 0; i < prefix.length(); i++) {
            curr = curr.map.get(prefix.charAt(i));
            if (curr == null) {
                throw new IllegalArgumentException("Error prefix");
            }
        }
        if (curr.isKey) {
            result.add(prefix);
        }
        result = addWords(result, curr, prefix);
        return result;
    }

    private List<String> addWords (List<String> result, Node curr, String prefix) {
        Node child = curr;
        for (char c : child.map.keySet()) {
            curr = child.map.get(c);
            if (curr.isKey) {
                result.add(prefix + c);
            }
            addWords(result, curr, prefix + c);
        }
        return result;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        StringBuilder longestP = new StringBuilder();
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return longestP.toString();
            }
            longestP.append(c);
            curr = curr.map.get(c);
        }
        return longestP.toString();
    }
}
