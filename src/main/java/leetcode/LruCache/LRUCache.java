package leetcode.LruCache;
import java.util.*;

/*
https://leetcode.com/problems/lru-cache/
Medium

Implement the LRUCache class:

    LRUCache(int capacity) Initialize the LRU cache with positive size capacity.

    int get(int key) Return the value of the key if the key exists, otherwise return -1.

    void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the 
    key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict 
    the least recently used key.

The functions get and put must each run in O(1) average time complexity.
 */
public class LRUCache {

    // Strategy - implement a double-linked list in order of used, keep a hashmap of key to ListNode
    // on get, pull from the list and move to the tail
    // on put, pull from list and move to the tail
    // on evict, evict the head of the list
    static class Node {
        int key;
        int val;
        Node prev;
        Node next;
        Node() {
        }
        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    Node head;
    Node tail;
    int capacity;
    Map<Integer, Node> cache = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (cache.containsKey(key)) {
            Node p = cache.get(key);
            // move node to the tail
            moveToTail(p);
            return p.val;
        } else  {
            return -1;
        }
    }
    
    public void put(int key, int value) {
        // if in cache, then update the val and move the node to the tail
        Node p;
        if (cache.containsKey(key)) {
            p = cache.get(key);
        } else  {
            // otherwise adding a new Node to cache, check capacity and evict if necessary
            p = new Node(key, value);
            cache.put(key, p);
            if (cache.size() > capacity) {
                Node evict = head.next;
                head.next = evict.next;
                head.next.prev = head;
                cache.remove(evict.key);
            }
        }
        p.val = value;
        moveToTail(p);
    }

    void moveToTail(Node p) {
        // extract p
        if (p.prev != null) {
            p.prev.next = p.next;
            p.next.prev = p.prev;
        }
        // add it to the tail
        p.next = tail;
        p.prev = tail.prev;
        p.prev.next = p;
        tail.prev = p;
    }
}
