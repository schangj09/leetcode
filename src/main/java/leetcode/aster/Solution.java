package leetcode.aster;

import java.util.*;
/*
https://leetcode.com/problems/asteroid-collision/description/
Medium

We are given an array asteroids of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, 
negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are 
the same size, both will explode. Two asteroids moving in the same direction will never meet.

Constraints:
    2 <= asteroids.length <= 10^4
    -1000 <= asteroids[i] <= 1000
    asteroids[i] != 0
*/

class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        // walk through the array of asteroids and push the next asteroid
        // - test the collision in a loop with the next one and the top of the stack
        int n = asteroids.length;
        Deque<Integer> q = new LinkedList<>();
        q.offerLast(asteroids[0]);

        for (int i = 1; i < n; i++) {
            int next = asteroids[i];
            // the only collision is if next is moving left and prev is moving right
            while (next < 0 && !q.isEmpty() && q.peekLast() > 0) {
                // set next to the winner of the collision - reset to empty in case of a tie (zero)
                int tmp = q.pollLast();
                if (tmp == -next) {
                    next = 0;
                } else if (tmp > -next) {
                    next = tmp;
                }
            }
            // add next if it was not destroyed by top of stack
            // (either adds the new next or adds back the last one we popped)
            if (next != 0) {
                q.offerLast(next);
            }
        }
        
        // copy the stack into an array
        int[] res = new int[q.size()];
        int i = 0;
        for (int v : q) {
            res[i++] = v;
        }
        return res;
    }
}
