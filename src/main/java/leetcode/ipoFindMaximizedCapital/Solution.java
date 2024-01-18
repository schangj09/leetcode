package leetcode.ipoFindMaximizedCapital;

import java.util.*;
/*
https://leetcode.com/problems/ipo/description/
Hard

Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.

You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it.

Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.

Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the final maximized capital.

The answer is guaranteed to fit in a 32-bit signed integer.

Constraints:
    1 <= k <= 10^5
    0 <= w <= 10^9
    n == profits.length
    n == capital.length
    1 <= n <= 10^5
    0 <= profits[i] <= 10^4
    0 <= capital[i] <= 10^9

 */
public class Solution {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // at each iteration, we increase our available capital by the profit chosen
        // so if we order the projects in increasing order of capital, then after 
        // each iteration, we can choose any item the left (from those not yet chosen).
        // We will maximize the total capital by each time choosing the project with 
        // greatest capital.
        //
        // Method is to make a max heap based on profit. Add to heap every item with capital requirement less 
        // than current capital. Then on each iteration, take the max from heap and add the next projects
        // that meet the new capital amount.

        int n = profits.length;
        int[][] pairs = new int[n][2];
        for (int i = 0; i <n ; i++)  {
            pairs[i][0] = profits[i];
            pairs[i][1] = capital[i];
        }
        Arrays.sort(pairs, (a, b) -> a[1] - b[1]); // sort ascending by capital

        // max heap for projects by profit
        PriorityQueue<Integer> profitHeap = new PriorityQueue<>(Comparator.reverseOrder());

        int capitalSum = w;
        // add items to the heap with capital less than or equal to capitalSum
        int i = 0;
        for (; i < n && pairs[i][1] <= capitalSum; i++) {
            profitHeap.offer(pairs[i][0]);
        }
        int count = 0;
        while (count < k && i <= n && !profitHeap.isEmpty()) {
            // take the max profit from profitHeap and add to capitalSum
            capitalSum += profitHeap.poll();
            count++;
            // add the next projects that we may now have enough capital for
            while (i < n && pairs[i][1] <= capitalSum) {
                profitHeap.offer(pairs[i][0]);
                i++;
            }
        }

        return capitalSum;
    }
}
