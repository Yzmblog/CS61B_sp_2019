import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;  //root of the tree

    private class Node {
        private K key;      //key of the value
        private V value;    //value of the node
        private Node left;  //left subtree
        private Node right; //right subtree
        private int size;   //size of the node

        public Node(K k, V v, int s) {
            key = k;
            value = v;
            size = s;
        }

    }




    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;

    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument to containsKey() is null");
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(root, key);
    }

    /**
     * Get value with key in specific Node.
     */
    private V get(Node n, K key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if      (cmp > 0) return get(n.right, key);
        else if (cmp < 0) return get(n.left, key);
        else              return n.value;
    }


    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    /* size of some Node */
    private int size(Node n) {
        if(n == null) {
            return 0;
        } else {
            return n.size;
        }
    }


    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put with a null key");
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node n, K key, V value) {

        if (n == null) return new Node(key, value, 1);
        int cmp = key.compareTo(n.key);
        if      (cmp > 0) n.right = put(n.right, key, value);
        else if (cmp < 0) n.left = put(n.left, key, value);
        else              n.value = value;
        n.size = 1 + size(n.left) + size(n.right);

        return n;

    }

    /**
     * Delete a key.
     */
    public void delete(K key) {
        if (key == null)    throw new IllegalArgumentException("calls delete with null");
        root = delete(root, key);


    }

    private Node delete(Node n, K key) {
        if  (n == null) return null;
        int cmp = key.compareTo(n.key);
        if      (cmp > 0)   n.right = delete(n.right, key);
        else if (cmp < 0)   n.left = delete(n.left, key);
        else {
            if (n.left == null)    return n.right;
            if (n.right == null)   return n.left;
            Node t = n;
            n = min(t.right);
            n.left = t.left;
            n.right = deleteMin(t.right);
        }

        n.size = size(n.left) + size(n.right) - 1;
        return n;
    }

    private Node min(Node n) {
        if (n == null)      return null;
        if (n.left == null) return n;
        else                return min(n.left);
    }

    private Node deleteMin(Node n) {
        if (n == null)  return null;
        if (n.left == null) return n.right;
        else                n.left = deleteMin(n.left);
        n.size = size(n.left) + size(n.right) - 1;
        return n;
    }


    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        throw (new UnsupportedOperationException());
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw (new UnsupportedOperationException());
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw (new UnsupportedOperationException());
    }

    /**
     * Iterator of this class, Don't support in this lab.
     */
    public Iterator<K> iterator() {
        throw (new UnsupportedOperationException());
    }

    /**
     *Prints out your BSTMap in order of increasing Key
     */
    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node r) {
        if (r == null)  return;
        if (r.left == null && r.right != null) {
            System.out.println("key: " + r.key.toString() + ":" + r.value.toString());
            printInOrder(r.right);
        } else if (r.right == null && r.left != null) {
            printInOrder(r.left);
            System.out.println("key: " + r.key.toString() + ":" + r.value.toString());
        } else if (r.left == null && r.right == null) {
            System.out.println("key: " + r.key.toString() + ":" + r.value.toString());
        } else if (r.left != null && r.right != null) {
            printInOrder(r.left);
            System.out.println("key: " + r.key.toString() + ":" + r.value.toString());
            printInOrder(r.right);
        }

    }


}
