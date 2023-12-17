package leetcode.infiniteSet;

import java.util.*;

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