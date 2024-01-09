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
        // "<=" so we find the leftmost index equal to target, or index of next largest value
        int left = find(nums, target, (a, b) -> a <= b);

        // return now if target not found in the array
        if (left == n || nums[left] != target) {
            return new int[] { -1, -1 };
        }

        // right binary search (finds leftmost index that is strictly greater than target - so it is 
        // the index immediately after the rightmost instance of target)
        int right = find(nums, target, (a, b) -> a < b);
        return new int[] { left, right-1 };
    }

    // returns the leftmost index for which condition is true
    // - to find leftmost instance of target (i.e. insertBefore), use "<=" and
    // to find value after target (i.e. insertAfter), use "<"
    int find(int[] nums, int target, BiFunction<Integer, Integer, Boolean> condition) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (condition.apply(target, nums[mid])) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}