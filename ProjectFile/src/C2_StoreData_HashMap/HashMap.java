package C2_StoreData_HashMap;

import java.util.LinkedList;
import java.util.List;

public class HashMap<K, V> {

    //=== Attributes =========================================================
    private static final int DEFAULT_CAPACITY = 16;
    private Node<K, V>[] table;
    private int size;

    
    
    //=== Constructor ========================================================
    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    
    
    //=== Methods ============================================================

    // Get the hash code for a key
    private int hash(K key) {
        return key.hashCode() % table.length;
    }

    
    // Insert or update a key-value pair in the map
    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> newNode = new Node<>(key, value);

        if (table[index] == null) {
            table[index] = newNode;
            size++;
        } else {
            Node<K, V> curr = table[index];
            while (curr.getNext() != null) {
                if (curr.getKey().equals(key)) {
                    curr.setValue(value); // Update existing key
                    return;
                }
                curr = curr.getNext();
            }

            if (curr.getKey().equals(key)) {
                curr.setValue(value); // Update existing key
            } else {
                curr.setNext(newNode); // Insert new key-value pair
                size++;
            }
        }
    }

    
    // Retrieve the value associated with a given key
    public V get(K key) {
        int index = hash(key);

        Node<K, V> curr = table[index];
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                return curr.getValue();
            }
            curr = curr.getNext();
        }

        return null; // Key not found
    }

    
    // Remove a key-value pair from the map
    public void remove(K key) {
        int index = hash(key);

        Node<K, V> curr = table[index];
        Node<K, V> prev = null;
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                if (prev == null) {
                    table[index] = curr.getNext(); // Update head of the list
                } else {
                    prev.setNext(curr.getNext()); // Remove node from the list
                }
                size--;
                return;
            }
            prev = curr;
            curr = curr.getNext();
        }
    }

    
    // Check if the map contains a given key
    public boolean containsKey(K key) {
        int index = hash(key);

        Node<K, V> curr = table[index];
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                return true;
            }
            curr = curr.getNext();
        }

        return false;
    }

    
    // Check if the map is empty
    public boolean isEmpty() {
        return size == 0;
    }

    
    // Get the number of key-value pairs in the map
    public int size() {
        return size;
    }

    
    // Clear the map
    public void clear() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        boolean isFirstKey = true;
        for (int i = 0; i < table.length; i++) {
            Node<K, V> curr = table[i];
            while (curr != null) {
                if (!isFirstKey) {
                    sb.append(", ");
                }
                sb.append(curr.getKey()).append("=").append(curr.getValue());
                isFirstKey = false;

                curr = curr.getNext();
            }
        }

        sb.append("}");
        return sb.toString();
    }
    

    // Public method to add a value to the map
    public static void addToMap(HashMap<Integer, LinkedList<String>> map, int key, String value) {
        LinkedList<String> linkedList = map.get(key);
        if (linkedList == null) {
            linkedList = new LinkedList<>();
            map.put(key, linkedList);
        }
        linkedList.add(value);
    }
    
    
    // print data
    public void printData() {
        for (int i = 0; i < table.length; i++) {
            Node<K, V> curr = table[i];
            while (curr != null) {
                System.out.println(curr.getKey() + "= " + curr.getValue());
                curr = curr.getNext();
            }
        }
    }
}
