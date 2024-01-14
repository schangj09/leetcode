package leetcode.maxSubseqScore;

import java.util.*;

/*
 * https://leetcode.com/problems/maximum-subsequence-score/description/
 * Medium
 * 
You are given two 0-indexed integer arrays nums1 and nums2 of equal length n and a positive integer k. You must choose a 
subsequence of indices from nums1 of length k.

For chosen indices i0, i1, ..., ik - 1, your score is defined as:

    The sum of the selected elements from nums1 multiplied with the minimum of the selected elements from nums2.
    It can defined simply as: (nums1[i0] + nums1[i1] +...+ nums1[ik - 1]) * min(nums2[i0] , nums2[i1], ... ,nums2[ik - 1]).

Return the maximum possible score.

A subsequence of indices of an array is a set that can be derived from the set {0, 1, ..., n-1} by deleting some or no 
elements.
 */

 /**
 * NOT SOLVED - the problem mentions the top k, this should indicate a heap.
 * The heap is populated with the max k items in the potential answer. So, the score
 * is the sum of the heap items * the min. The selected min item defines the set 
 * of values that go into the max heap.
 * 
 * Populate the heap efficiently by making pairs of values from nums1, nums2 so indexes are not
 * needed and sort by decreasing minimum.
 */
class Solution {
    public long maxScore(int[] nums1, int[] nums2, int k) {
        // recursive algorithm where I pass in the running maxScore and the sum and min of the previously selected
        // indices, return the maxScore
        Set<Integer> remaining = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            remaining.add(i);
        }
        // brute force, but too slow (and not working)
        return max(new HashSet<>(), remaining, nums1, nums2, 0, 0, Integer.MAX_VALUE, k);
    }

    
    // set of selected indices
    // set of remaining indices
    int max(Set<Integer> selected,
        Set<Integer> remaining,
        int[] nums1, int[] nums2,
        int maxScore,
        int sum, int min, int k
    ) {
        if (k == 0) {
            // calculate the score
            return Math.max(maxScore, sum * min);
        } else {
            // get the max score recursively for each of remaining indexes
            List<Integer> toDo = new ArrayList<>(remaining);
            for (int v : toDo) {
                remaining.remove(v);
                selected.add(v);
                maxScore = Math.max(maxScore, max(selected, remaining, nums1, nums2, maxScore, sum + nums1[v], Math.min(min, nums2[v]), k-1));
            }
            return maxScore;
        }
    }
}
