package leetcode.mruQueue;

import java.util.*;

/*
https://leetcode.com/problems/design-most-recently-used-queue/description/
Medium

Design a queue-like data structure that moves the most recently used element to the end of the queue.

Implement the MRUQueue class:

    MRUQueue(int n) constructs the MRUQueue with n elements: [1,2,3,...,n].
    int fetch(int k) moves the kth element (1-indexed) to the end of the queue and returns it.


 */

class MRUQueue {
    LinkedList<Integer>[] buckets;
    int numBuckets;

    public MRUQueue(int n) {
        numBuckets = (int)Math.sqrt(n);
        buckets = new LinkedList[(n+numBuckets-1)/numBuckets];
        Arrays.asList(buckets).replaceAll(i-> new LinkedList<>());
        for(int i = 1; i <= n;++i){
            buckets[(i-1)/numBuckets].offerLast(i);
        }
    }
    
    // fetch kth item - 1-based index
    public int fetch(int k) {
        LinkedList<Integer> bucket = buckets[--k/numBuckets];
        int v = bucket.remove(k%numBuckets);
        // rebalance by moving values from the higher buckets to the lower ones
        for (int i = 1+k/numBuckets; i < buckets.length; ++i) {
            buckets[i-1].offerLast(buckets[i].removeFirst());
        }
        buckets[buckets.length-1].offerLast(v);
        return v;
    }
}