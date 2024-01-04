package leetcode;

import java.util.*;


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