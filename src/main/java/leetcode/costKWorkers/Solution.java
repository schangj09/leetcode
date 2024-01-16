package leetcode.costKWorkers;

import java.util.*;

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

        // Tried O(Nk) iterations, but time limit exceeded.
        // So, we need a data structure to represent the remaining workers.
        // Thinking to use a priority queue for the costs and a set for the 

        // available workers, and leftSet and rightSet. When we choose one from the leftSet,
        // then iterate for nextLeft until find an index in available workers


        // keep the array of costs and remove one each time, then iterate by candidates
        // algorithm should be O(KC)

        int n = costs.length;
        List<Integer> remainingWorkers = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            remainingWorkers.add(costs[i]);
        }

        int cost = 0;
        for (int i = 0; i < k; i++) {
            int minCost = Integer.MAX_VALUE;
            int minCostIndex = -1;
            int m = remainingWorkers.size();
            for (int left = 0; left < candidates && left < m; left++) {
                if (remainingWorkers.get(left) < minCost) {
                    minCost = remainingWorkers.get(left);
                    minCostIndex = left;
                }
            }
            int count = 0;
            for (int right = m-1; count < candidates && right >= candidates; right--, count++) {
                if (remainingWorkers.get(right) < minCost) {
                    minCost = remainingWorkers.get(right);
                    minCostIndex = right;
                }
            }

            cost += minCost;
            remainingWorkers.remove(minCostIndex);
        }
        return cost;
    }
}