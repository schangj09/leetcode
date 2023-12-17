package leetcode.maxSubseqScore;

import java.util.*;

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
