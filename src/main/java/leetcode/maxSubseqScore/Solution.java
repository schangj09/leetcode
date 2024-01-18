package leetcode.maxSubseqScore;

import java.util.*;

/*
https://leetcode.com/problems/maximum-subsequence-score/description/
Hard
(note Leetcode misclassifies this as Medium - the identical problem
https://leetcode.com/problems/maximum-performance-of-a-team/
is classified as Hard)

You are given two 0-indexed integer arrays nums1 and nums2 of equal length n and a positive integer k. You must choose a 
subsequence of indices from nums1 of length k.

For chosen indices i0, i1, ..., ik - 1, your score is defined as:

    The sum of the selected elements from nums1 multiplied with the minimum of the selected elements from nums2.
    It can defined simply as: (nums1[i0] + nums1[i1] +...+ nums1[ik - 1]) * min(nums2[i0] , nums2[i1], ... ,nums2[ik - 1]).

Return the maximum possible score.

A subsequence of indices of an array is a set that can be derived from the set {0, 1, ..., n-1} by deleting some or no 
elements.

Constraints:
    n == nums1.length == nums2.length
    1 <= n <= 10^5
    0 <= nums1[i], nums2[j] <= 10^5
    1 <= k <= n

For more similar problems, try these:
https://leetcode.com/problems/ipo/description/
https://leetcode.com/problems/maximum-fruits-harvested-after-at-most-k-steps/description/

 */

 /**
 * 1st attempt NOT SOLVED - the problem mentions the top k, this should indicate a heap.
 * The heap is populated with the max k items in the potential answer. So, the score
 * is the sum of the heap items * the min. The selected min item defines the set 
 * of values that go into the max heap.
 * 
 * Populate the heap efficiently by making pairs of values from nums1, nums2 so indexes are not
 * needed and sort by decreasing minimum.
 * 
 * 2nd attempt 18 Jan 2024
 * Thinking of sorting nums1 and choose k-1 largest elements. Then sort num2 and choose the largest
 * element that is smaller than the min selected so far.
 * Alternatively, choose the k largest from num2 so the multiplier is greatest.
 * But, in first approach the multiplier could be too small, and in second the sum could be zero.
 * 
 * Final solution 18 Jan 2024
 * Checked the solution - my 2nd idea was closer, correct idea is to sort pairs by nums2 (descending) 
 * beacuse we need to choose a min from nums2 and then the top k-1 from the nums1 numbers
 * whose nums2 are greater. This is the top k of those we've seen already (as we iterate forward).
 */
class Solution {
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i][0] = nums1[i];
            pairs[i][1] = nums2[i];
        }
        // sort by nums2 in decreasing order
        Arrays.sort(pairs, (a, b) -> b[1] - a[1]);

        // add first k-1 of nums1 to a minHeap, and track the sum of these top k-1 elements
        // - we maintain the size of this heap as always k-1 elements
        long topKMinus1 = 0;
        PriorityQueue<Integer> h = new PriorityQueue<>(Comparator.naturalOrder());
        for (int i = 0; i < k-1; i++) {
            topKMinus1 += pairs[i][0];
            h.offer(pairs[i][0]);
        }
        // now for each proposed min(nums2) we calculate the score by using the heap sum
        // then update the heap
        int minNum2 = pairs[k-1][1];
        long maxScore = (topKMinus1 + pairs[k-1][0]) * minNum2;
        for (int i = k; i < n; i++) {
            // add the previous to the heap, then remove the smallest from the heap
            topKMinus1 += pairs[i-1][0];
            h.offer(pairs[i-1][0]);
            topKMinus1 -= h.poll();
            // new minimum from nums2 is i (the next smaller nums2)
            minNum2 = pairs[i][1];
            // update the maxScore with topKMinus1 and the selected minNum2
            maxScore = Math.max(maxScore, (topKMinus1 + pairs[i][0]) * minNum2);
        }
        return maxScore;
    }
}
