package leetcode.findRange;

import java.util.function.*;

/*
Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.

If target is not found in the array, return [-1, -1].

You must write an algorithm with O(log n) runtime complexity.

 */
public class Solution {
    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        // left binary search (finds index of leftmost value that matches the condition),
        // "target <= nums[i]" so we find the leftmost index equal to target, or index of next largest value
        int left = find(0, n, (i) -> target <= nums[i]);

        // return now if target not found in the array
        if (left == n || nums[left] != target) {
            return new int[] { -1, -1 };
        }

        // right binary search (finds leftmost index that is strictly greater than target - so it is 
        // the index immediately after the rightmost instance of target)
        int right = find(0, n, (i) -> target < nums[i]);
        return new int[] { left, right-1 };
    }

    // find returns the leftmost index for which condition is true - returns end index if condition is false 
    // for all indices.
    // condition must be a function c that satisfies the property:
    //   if c(i)=true then c(i+1)=true for all i start to end-2
    //
    // For example:
    // - to find leftmost instance of target (i.e. insertBefore), use "target <= nums[i]" and
    // to find value after target (i.e. insertAfter), use "target < nums[i]"
    int find(int start, int end, Function<Integer, Boolean> condition) {
        int l = start;
        int r = end;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (condition.apply(mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}