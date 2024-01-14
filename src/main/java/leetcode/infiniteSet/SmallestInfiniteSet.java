package leetcode.infiniteSet;

import java.util.*;

/*
 * https://leetcode.com/problems/smallest-number-in-infinite-set/
 * Medium
 * 
You have a set which contains all positive integers [1, 2, 3, 4, 5, ...].

Implement the SmallestInfiniteSet class:

    SmallestInfiniteSet() Initializes the SmallestInfiniteSet object to contain all positive integers.
    int popSmallest() Removes and returns the smallest integer contained in the infinite set.
    void addBack(int num) Adds a positive integer num back into the infinite set, if it is not already in the infinite set.

 */
class SmallestInfiniteSet {
    // track the next smallest of infinite numbers
    private int nextInfinite = 1;

    // track the addBack values that are less than nextInfinite
    PriorityQueue<Integer> addBackHeap = new PriorityQueue<>(Comparator.naturalOrder());
    // for efficiency of addBack, also keep track of the addBackSet
    Set<Integer> addBackSet = new HashSet<>();

    public SmallestInfiniteSet() {
    }

    public int popSmallest() {
        if (addBackHeap.isEmpty()) {
            nextInfinite++;
            return nextInfinite - 1;
        } else {
            return addBackHeap.poll();
        }
    }

    public void addBack(int num) {
        if (num < nextInfinite && !addBackHeap.contains(num)) {
            addBackHeap.offer(num);
        }
    }
}

/**
 * Your SmallestInfiniteSet object will be instantiated and called as such:
 * SmallestInfiniteSet obj = new SmallestInfiniteSet();
 * int param_1 = obj.popSmallest();
 * obj.addBack(num);
 */