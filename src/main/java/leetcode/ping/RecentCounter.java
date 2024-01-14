package leetcode.ping;

import java.util.Deque;
import java.util.LinkedList;

/*
 * https://leetcode.com/problems/number-of-recent-calls/
 * Easy
 * 
You have a RecentCounter class which counts the number of recent requests within a certain time frame.

Implement the RecentCounter class:

    RecentCounter() Initializes the counter with zero recent requests.

    int ping(int t) Adds a new request at time t, where t represents some time in milliseconds, and returns the number of 
    requests that has happened in the past 3000 milliseconds (including the new request). Specifically, return the number of 
    requests that have happened in the inclusive range [t - 3000, t].

It is guaranteed that every call to ping uses a strictly larger value of t than the previous call.


 */
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
