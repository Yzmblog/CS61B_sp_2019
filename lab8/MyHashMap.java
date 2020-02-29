import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Codes are learned from Github.
 */

public class MyHashMap<K, V> implements Map61B<K, V>{

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    int size;
    int threshold;
    double loadFactor = 0.75;

    private BucketEntity<K, V>[] buckets;

    private class BucketEntity<K, V> {
        private K key;
        private V value;
        private BucketEntity<K, V> next;
        private int hashCode;

        public BucketEntity(int hashCode, K key, V value, BucketEntity<K, V> next) {
            this.hashCode = hashCode;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public int getHashCode() {
            return hashCode;
        }

        public void setHashCode(int hashCode) {
            this.hashCode = hashCode;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public BucketEntity<K, V> getNext() {
            return next;
        }

        public void setNext(BucketEntity<K, V> next) {
            this.next = next;
        }

    }


    /**
     * The constructor of MyHashMap.
     */
    public MyHashMap() {
        buckets = new BucketEntity[INITIAL_CAPACITY];
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
        size = 0;

    }

    public MyHashMap(int initialSize) {
        buckets = new BucketEntity[initialSize];
        threshold = (int) (initialSize * LOAD_FACTOR);
        size = 0;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new BucketEntity[initialSize];
        threshold = (int) (initialSize * loadFactor);
        this.loadFactor = loadFactor;
        size = 0;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        buckets = new BucketEntity[buckets.length];
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) return false;
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int hashCode = hash(key, buckets.length);
        BucketEntity<K, V> entity = get(hashCode, key);
        return entity == null ? null : entity.getValue();
    }

    private BucketEntity<K, V> get(int hashCode, K key) {
        BucketEntity<K, V> entity = buckets[hashCode];
        while (entity != null) {
            if (entity.getKey().equals(key)
                    && entity.getHashCode() == hashCode)
                return entity;
            entity = entity.getNext();
        }
        return null;
    }

    /**
     * Rewrite the hashCode.
     */
    private int hash(K key, int length) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        return (key.hashCode() & 0x7FFFFFFF) % length;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        int hashCode = hash(key, buckets.length);
        BucketEntity<K, V> entity = buckets[hashCode];

        while (entity != null) {
            if (entity.getHashCode() == hashCode && entity.getKey() == key){
                entity.setValue(value);
                return;
            }
            entity = entity.getNext();
        }

         buckets[hashCode] = new BucketEntity<K, V>(hashCode, key, value, buckets[hashCode]);

        size += 1;

        if (size > threshold) {
            resize(buckets.length * 2);
        }
    }

    private void resize(int newCap) {
        BucketEntity<K, V>[] newBuckets = new BucketEntity[newCap];
        for (BucketEntity<K, V> bucket : buckets) {
            BucketEntity<K, V> entity = bucket;
            while (entity != null) {
                int newHash = hash(entity.getKey(), newCap);
                BucketEntity<K, V> newEntity = new BucketEntity<K, V>(newHash, entity.getKey(),
                        entity.getValue(), newBuckets[newHash]);
                newBuckets[newHash] = newEntity;
                entity = entity.getNext();
            }

        }
        buckets = newBuckets;
        threshold = (int) (newCap * loadFactor);
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> kSet = new HashSet<K>();
        for (BucketEntity<K, V>bucket : buckets) {
            BucketEntity<K, V> entity = bucket;
            while (entity != null) {
                kSet.add(entity.getKey());
                entity = entity.getNext();
            }
        }
        return kSet;
    }

    /** Returns a iterator of the map. */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int hashCode = hash(key, buckets.length);
        return remove(hashCode, key);
    }

    private V remove(int hashCode, K key) {
        BucketEntity<K, V> entity = buckets[hashCode];
        BucketEntity<K, V> nextEntity = entity.next;

        if (entity.getKey().equals(key)) {
            V toRemove = entity.getValue();
            buckets[hashCode] = nextEntity;
            size -= 1;
            return toRemove;
        } else {
            while (! nextEntity.getKey().equals(key)) {
                entity = entity.getNext();
                nextEntity = nextEntity.getNext();
            }
            V toRemove = nextEntity.getValue();
            entity.setNext(nextEntity.getNext());
            size -= 1;
            return toRemove;
        }
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int hashCode = hash(key, buckets.length);
        return remove(hashCode, key, value);
    }

    private V remove(int hashCode, K key, V value) {
        BucketEntity<K, V> entity = buckets[hashCode];
        BucketEntity<K, V> nextEntity = entity.next;

        if (entity.getKey().equals(key) && entity.getValue().equals(value)) {
            V toRemove = entity.getValue();
            buckets[hashCode] = nextEntity;
            size -= 1;
            return toRemove;
        } else {
            while (! nextEntity.getKey().equals(key)) {
                entity = entity.getNext();
                nextEntity = nextEntity.getNext();
            }
            if (nextEntity.getValue() == value) {
                V toRemove = nextEntity.getValue();
                entity.setNext(nextEntity.getNext());
                size -= 1;
                return toRemove;
            }
        }
        return null;
    }

}
