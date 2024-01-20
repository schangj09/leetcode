package leetcode.hitCounter;

import java.util.*;

/*
https://leetcode.com/problems/design-hit-counter/description/
Medium

Design a hit counter which counts the number of hits received in the past 5 minutes (i.e., the past 300 seconds).

Your system should accept a timestamp parameter (in seconds granularity), and you may assume that calls are being made to the system in chronological order (i.e., timestamp is monotonically increasing). Several hits may arrive roughly at the same time.

Implement the HitCounter class:

    HitCounter() Initializes the object of the hit counter system.
    void hit(int timestamp) Records a hit that happened at timestamp (in seconds). Several hits may happen at the same timestamp.
    int getHits(int timestamp) Returns the number of hits in the past 5 minutes from timestamp (i.e., the past 300 seconds).

 
 */
public class HitCounter {
    // First attempt - use a circular queue of 300 elements that count the number of 
    // of hits at a given timestamp - clear values that more than 300 seconds on each call
    //
    // second attempt - use a Deque with the timestamp and count

    static int SECONDS = 300;
    Deque<int[]> q = new LinkedList<>();

    public HitCounter() {
    }
    
    public void hit(int timestamp) {
        updateFirstHit(timestamp);
        if (!q.isEmpty() && q.peekLast()[0] == timestamp) {
            q.peekLast()[1]++;
        } else {
            q.offerFirst(new int[] { timestamp, 1 });
        }
        
    }
    
    public int getHits(int timestamp) {
        updateFirstHit(timestamp);
        // we can optimize this futher by maintaining the totalCount in the queue
        int count = 0;
        for (int[] v : q) {
            if (v[0] <= timestamp) {
                count += v[1];
            }
        }
        return count;
    }

    void updateFirstHit(int timestamp) {
        System.out.println(String.format("%d %d", q.peekFirst()[0], timestamp - SECONDS));
        while (!q.isEmpty() && q.peekFirst()[0] <= timestamp - SECONDS) {
            q.pollFirst();
        }
    }
}
