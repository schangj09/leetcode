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
class Solution {
    // search with 2 pass binary search algorithm
    //
    // Note: a single pass with a modified search condition would not necessarily save processing
    // time because the condition is more complicated, so the same amount of processing is done
    // as compared to 2 pass binary search.
    int search(int[] nums, int target) {
        final int n = nums.length;
        // first find the pivot if there is one (else the pivot is at index 0)
        //
        // For the binary search method, we must verify that if condition is true for mid=i, then it is also
        // true for mid=i+1. We should consider 2 cases.
        // Case 1: left < mid, then condition is true when the pivot is some index <= mid.
        // In this case, we know the range left:mid+1 also contains the pivot index. 
        // Case 2: If left == mid, then the condition is false, so it may be true or false for mid+1.
        // Since we only iterate when there is a pivot, then we can guarantee condition is true if end == mid+1 
        // and end < n. Otherwise if end == n, then there is no pivot and the find method returns n.
        // Note: it isn't technically necessary to first check 0 and n-1, since if there is no  
        // pivot index, then binary search would yield n (because there is no leftmost index for which is 
        // the condition is true).
        int pivot = (nums[0] > nums[n - 1]) ? find(0, n, (left, mid) -> nums[left] > nums[mid]) % n : 0;
        // then binary search based on the rotated array
        int i = (pivot + find(0, n, (left, mid) -> target <= nums[(pivot + mid) % n])) % n;
        return (nums[i] == target) ? i : -1;
    }

    // find returns the leftmost index for which condition is true - returns end index if condition is false
    // for all indices.
    // condition must be a function c that satisfies the property:
    // if c(i)=true then c(i+1)=true for all i start to end-2
    //
    // For example:
    // - to find leftmost instance of target (i.e. insertBefore), use "target <= nums[i]" and
    // to find value after target (i.e. insertAfter), use "target < nums[i]"
    int find(int start, int end, BiFunction<Integer, Integer, Boolean> condition) {
        int l = start;
        int r = end;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (condition.apply(start, mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}