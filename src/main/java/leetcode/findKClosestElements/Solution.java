package leetcode.findKClosestElements;

import java.util.*;

/*
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

        // the tricky part is to find the start index.
        // cases:
        // 1) x <= a[0]
        // 2) x >= a[n-1]
        // 3) x = a[i] and x < a[i+1]
        // 4) x = a[i] and x > a[i-1]
        // 5) a[i] < x < a[i+1]

        int n = arr.length;
        // binary search interval for index of first val < x and right val > x
        int i = 0, j = n-1;
        while (j > i) {
            int mid = i + (j-i)/2;
            if (arr[mid] >= x) {
                j = mid;
            } else {
                i = mid+1;
            }
        }
        int start = i - 1;
        int end = start+1;

        while (end - start < k) {
            if (start < 0) {
                end++;
                continue;
            }
            int leftDist = start == 0 ? Integer.MAX_VALUE : Math.abs(arr[start] - x);
            int rightDist = end == n ? Integer.MAX_VALUE : Math.abs(arr[end] - x);
            if (leftDist <= rightDist) {
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
