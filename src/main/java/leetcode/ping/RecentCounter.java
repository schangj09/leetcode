package leetcode.ping;

import java.util.Deque;
import java.util.LinkedList;

class RecentCounter {
    Deque<Integer> recent = new LinkedList<>();
    // one option is to keep a FIFO queue of recent ping times. When a new ping comes in, we poll until 
    // the first in the queue is greater than t-3000
    // the space is bounded to 3000 since ping is strictly increasing, the time to remove queue is also O(3000) = O(1)
    public RecentCounter() {
        
    }
    
    public int ping(int t) {
        while (!recent.isEmpty() && recent.peekFirst() < t-3000) {
            recent.pollFirst();
        }
        recent.add(t);
        return recent.size();
    }
}

/**
 * Your RecentCounter object will be instantiated and called as such:
 * RecentCounter obj = new RecentCounter();
 * int param_1 = obj.ping(t);
 */
