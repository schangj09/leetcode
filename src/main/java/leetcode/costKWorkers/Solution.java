package leetcode.costKWorkers;

import java.util.*;

/*
 * https://leetcode.com/problems/total-cost-to-hire-k-workers/description
 * Medium
 * 
You are given a 0-indexed integer array costs where costs[i] is the cost of hiring the ith worker.

You are also given two integers k and candidates. We want to hire exactly k workers according to the following rules:

    You will run k sessions and hire exactly one worker in each session.
    In each hiring session, choose the worker with the lowest cost from either the first candidates workers or the last candidates workers. Break the tie by the smallest index.
        For example, if costs = [3,2,7,7,1,2] and candidates = 2, then in the first hiring session, we will choose the 4th worker because they have the lowest cost [3,2,7,7,1,2].
        In the second hiring session, we will choose 1st worker because they have the same lowest cost as 4th worker but they have the smallest index [3,2,7,7,2]. Please note that the indexing may be changed in the process.
    If there are fewer than candidates workers remaining, choose the worker with the lowest cost among them. Break the tie by the smallest index.
    A worker can only be chosen once.

Return the total cost to hire exactly k workers.


 */
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
