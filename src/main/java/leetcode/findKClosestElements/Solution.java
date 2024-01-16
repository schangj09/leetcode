package leetcode.findKClosestElements;

import java.util.*;

/*
https://leetcode.com/problems/find-k-closest-elements/description/
Medium

Given a sorted integer array arr, two integers k and x, return the k closest integers to x in the array. The 
result should also be sorted in ascending order.

An integer a is closer to x than an integer b if:

    |a - x| < |b - x|, or
    |a - x| == |b - x| and a < b

  */
public class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // the idea is to first find the index of x or the closest value to x with a binary search
        // then have 2 pointers left and right, decrement or increment depending on 
        // which next value is closer to x until right - left = k

        // the start index should be the leftmost value that is greater or equal to the target

        int n = arr.length;
        // binary search interval for index of leftmost value >= x
        int i = 0, j = n-1;
        while (j > i) {
            int mid = i + (j-i)/2;
            if (x <= arr[mid]) {
                j = mid;
            } else {
                i = mid+1;
            }
        }
        int start = i;
        // choose closest value for start - either index i or i-1
        if (arr[i] != x
                && i > 0
                && Math.abs(arr[i-1] - x) <= Math.abs(arr[i] - x)) {
            start = i - 1;
        }
        int end = start + 1;

        while (end - start < k) {
            if (start == 0) {
                end++;
                continue;
            }
            if (end == n || Math.abs(arr[start-1] - x) <= Math.abs(arr[end] - x)) {
                start--;
            } else {
                end++;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (i = start; i < end; i++) {
            res.add(arr[i]);
        }
        return res;
    }
}
