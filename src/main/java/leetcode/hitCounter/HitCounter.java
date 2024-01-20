package leetcode.hitCounter;

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
    // Passes test cases, but didn't work - Time out

    static int SECONDS = 300;
    // use a circular list of 300 seconds
    int[] q = new int[SECONDS];
    int firstHit = -1;
    int lastHit = 0;

    public HitCounter() {
    }
    
    public void hit(int timestamp) {
        updateFirstHit(timestamp);
        q[timestamp%SECONDS]++;
        lastHit = timestamp;
    }
    
    public int getHits(int timestamp) {
        updateFirstHit(timestamp);
        int count = 0;
        for (int i = 0; i < SECONDS && firstHit + i <= lastHit && firstHit + i <= timestamp; i++) {
            count += q[(firstHit + i)%SECONDS];
        }
        return count;
    }

    void updateFirstHit(int timestamp) {
        if (firstHit == -1) {
            firstHit = timestamp;
        }
        while (timestamp - firstHit + 1 > SECONDS) {
            //System.out.println(String.format("%d %d %d", timestamp, firstHit, q[firstHit%SECONDS]));
            q[firstHit%SECONDS] = 0;
            firstHit++;
        }
    }
}
