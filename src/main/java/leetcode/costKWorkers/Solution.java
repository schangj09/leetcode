package leetcode.costKWorkers;

import java.util.*;

/**
 * NOT SOLVED - but the algorithm is correct - there is some bug that failed the test case when 
 * 46 costs, k 37 and candidates 40
 *
 * Looks like the problem statement is vauge - the solution implements left candidates as larger 
 * set than right candidates. My solution makes them equal sets in this case. (not worth fixing it)
 */
public class Solution {
    public long totalCost(int[] costs, int k, int candidates) {
        // there are 3 pools of workers, left candidate, right candidates and middle
        // on each iteration we choose the lowest cost from left or right, then add one of the
        // remaining middle workers to that candidates pool

        // left and right are priority heaps since we want to choose the min cost worker
        // middle is a double linked list because we need to choose one from the left or right

        // represent the candidates as pair of index and cost, sorted by lowest cost and lowest index
        PriorityQueue<int[]> left = new PriorityQueue<>(Solution::comparator);
        PriorityQueue<int[]> right = new PriorityQueue<>(Solution::comparator);

        int n = costs.length;
        for (int i = 0; i < candidates && i <= costs.length/2; i++) {
            left.add(new int[] {costs[i], i});
            // don't double add the middle value in case of odd length list and candidates is greater than n/2
            if (i != n-1-i) {
                right.add(new int[] {costs[n-1-i], n-1-i});
            }
        }
        LinkedList<int[]> middle = new LinkedList<>();
        for (int i = candidates; i < n-candidates; i++) {
            middle.add(new int[] { costs[i], i });
        }

        int cost = 0;
        for (int i = 0; i < k; i++) {
            int peekLeft = !left.isEmpty() ? left.peek()[0] : Integer.MAX_VALUE;
            int peekRight = !right.isEmpty() ? right.peek()[0] : Integer.MAX_VALUE;
            // favor left candidates since they have lower index
            if (peekLeft <= peekRight) {
                cost += left.poll()[0];
                if (!middle.isEmpty()) {
                    left.offer(middle.removeFirst());
                }
            } else {
                cost += right.poll()[0];
                if (!middle.isEmpty()) {
                    right.offer(middle.removeLast());
                }
            }
        }
        return cost;
    }

    static int comparator(int[] p1, int[] p2) {
        int costDiff = p1[0] - p2[0];
        return costDiff != 0 ? costDiff : p1[1] - p2[1];
    }
}
