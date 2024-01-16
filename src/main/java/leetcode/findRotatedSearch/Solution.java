package leetcode.findRotatedSearch;

import java.util.function.*;

/*
https://leetcode.com/problems/search-in-rotated-sorted-array/description/
Medium

There is an integer array nums sorted in ascending order (with distinct values).

Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].

Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.

You must write an algorithm with O(log n) runtime complexity.
 */
public class Solution {
    // search with modified search condition
    //
    // not working yet
    int search(int[] nums, int target) {
        final int n = nums.length;
        int i = find(0, n, (x) -> {
            int mid = x[1];
            int left = nums[x[0]];
            //int right = nums[x[2] - 1];
            // consider if target is in a range that does not contain the pivot point
            if (left < nums[mid]) {
                return target <= nums[mid];
            } else {
                // mid+1 < right
                return mid + 1 < n ? target >= nums[mid + 1] : false;
            }
        });
        return (i < n && nums[i] == target) ? i : -1;
    }

    // find returns the leftmost index for which condition is true - returns end
    // index if condition is false
    // for all indices.
    // condition must be a function c that satisfies the property:
    // if c(i)=true then c(i+1)=true for all i start to end-2
    //
    // For example:
    // - to find leftmost instance of target (i.e. insertBefore), use "target <=
    // nums[i]" and
    // to find value after target (i.e. insertAfter), use "target < nums[i]"
    int find(int start, int end, Function<int[], Boolean> condition) {
        int l = start;
        int r = end;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (condition.apply(new int[]{start, mid, end})) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}