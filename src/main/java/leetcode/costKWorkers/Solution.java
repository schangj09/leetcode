package leetcode.costKWorkers;

import java.util.PriorityQueue;

/*
 * https://leetcode.com/problems/total-cost-to-hire-k-workers/description
 * Medium
 * 
You are given a 0-indexed integer array costs where costs[i] is the cost of hiring the ith worker.

You are also given two integers k and candidates. We want to hire exactly k workers according to the following rules:

    You will run k sessions and hire exactly one worker in each session.
    In each hiring session, choose the worker with the lowest cost from either the first 'candidates' workers or the 
    last 'candidates' workers. Break the tie by the smallest index.

    For example, if costs = [3,2,7,7,1,2] and candidates = 2, then in the first hiring session, we will choose the 4th 
    worker because they have the lowest cost [3,2,7,7,1,2].

    In the second hiring session, we will choose 1st worker because they have the same lowest cost as 4th worker but 
    they have the smallest index [3,2,7,7,2]. Please note that the indexing may be changed in the process.

    If there are fewer than 'candidates' workers remaining, choose the worker with the lowest cost among them. Break the tie 
    by the smallest index.

    A worker can only be chosen once.

Return the total cost to hire exactly k workers.

Constraints:
    1 <= costs.length <= 10^5 
    1 <= costs[i] <= 10^5
    1 <= k, candidates <= costs.length

 */
public class Solution {
    public long totalCost(int[] costs, int k, int candidates) {
        // At first I was thinking of using 2 heaps for the left and right, but then I realized
        // the heaps overlap after the remaining workers shrinks to less than 2*candidates.
        // In fact, candidates can start out overlapping (if greater than n/2).
        // This would make it impossible to remove elements from the priority heaps.

        // So, given constraints on the problem are few elements, it makes more sense to iterate
        // through the remaining workers on each hiring selection.

        int n = costs.length;
        boolean[] selected = new boolean[n];

        int cost = 0;
        for (int i = 0; i < k; i++) {
            int minCost = Integer.MAX_VALUE;
            int minCostIndex = -1;
            int left = 0;
            int count = 0;
            while (left < n && count < candidates) {
                if (!selected[left]) {
                    count++;
                    if (costs[left] < minCost) {
                        minCost = costs[left];
                        minCostIndex = left;
                    }
                }
                left++;
            }
            int right = n-1;
            count = 0;
            while (right >= 0 && count < candidates) {
                if (!selected[right]) {
                    count++;
                    if (costs[right] < minCost) {
                        minCost = costs[right];
                        minCostIndex = right;
                    }
                }
                right--;
            }

            cost += minCost;
            selected[minCostIndex] = true;
        }
        return cost;
    }
}
