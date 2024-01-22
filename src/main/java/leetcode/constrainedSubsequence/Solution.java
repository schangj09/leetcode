package leetcode.constrainedSubsequence;

import java.util.*;

/*
https://leetcode.com/problems/constrained-subsequence-sum/description/
Hard


Given an integer array nums and an integer k, return the maximum sum of a non-empty subsequence of that array such that for every 
two consecutive integers in the subsequence, nums[i] and nums[j], where i < j, the condition j - i <= k is satisfied.

A subsequence of an array is obtained by deleting some number of elements (can be zero) from the array, leaving the remaining 
elements in their original order.


Example 1:
Input: nums = [10,2,-10,5,20], k = 2
Output: 37
Explanation: The subsequence is [10, 2, 5, 20].

Example 2:
Input: nums = [-1,-2,-3], k = 1
Output: -1
Explanation: The subsequence must be non-empty, so we choose the largest number.


Example 3:
Input: nums = [10,-2,-10,-5,20], k = 2
Output: 23
Explanation: The subsequence is [10, -2, -5, 20].
 
Constraints:
    1 <= k <= nums.length <= 10^5
    -10^4 <= nums[i] <= 10^4

Hint 1
Use dynamic programming.
Hint 2
Let dp[i] be the solution for the prefix of the array that ends at index i, if the element at index i is in the subsequence.
Hint 3
dp[i] = nums[i] + max(0, dp[i-k], dp[i-k+1], ..., dp[i-1])
Hint 4
Use a heap with the sliding window technique to optimize the dp.
 */
public class Solution {
    public int constrainedSubsetSum(int[] nums, int k) {
        // first we need dp to get the sub-solutions for i-k
        // then we need a heap to track the max of the previous k sub-solutions

        // dp array contains solution for the prefix of the array which includes element at index i
        int n = nums.length;
        int [] dp = new int[n];

        // because of the constraint, the previous item in the subsequence must be at index >= i-k
        // the best solution is the max of those solutions plus nums[i]
        // - heap can store all the values we have seen so far with the index. Then we 
        // find the max item, we can discard the max item if it's index is lower than i-k and then
        // we keep looking for the next max item until it is within the range.
        //
        // This will be bounded time since we only add an item once and remove it (at most) once.
        PriorityQueue<int[]> p = new PriorityQueue<>((a, b) -> b[1] - a[1]); // reverse order of dp value
        p.offer(new int[] {-1, 0});
        int maxSolution = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            System.out.println(i);
            // calculate the dp[i] using the first maximum from the heap which is in the required index range
            while (p.peek()[0] < i - k) {
                // p.poll();
                int[] discard = p.poll();
                System.out.println("discard: " + Arrays.toString(discard));
            }
            System.out.println(Arrays.toString(p.peek()));
            dp[i] = nums[i] + Math.max(0, p.peek()[1]);
            int[] o = new int[] {i, dp[i]};
            p.offer(o);
            System.out.println("offer: " + Arrays.toString(o));
            maxSolution = Math.max(maxSolution, dp[i]);
        }
        return maxSolution;
    }

    public static void main(String[] args) {
        int[] nums;
        int k;

        k = 1;
        nums = new int[] {
            // -5266,4019,7336,-3681,-5767
            // 10,-2,-10,-5,20
            -8269,3217,-4023,-4138,-683,6455,-3621,9242,4015,-3790
        };
        System.out.println(Arrays.toString(nums));
        int v = new Solution().constrainedSubsetSum(nums, k);
        System.out.println(v);
    }
}
