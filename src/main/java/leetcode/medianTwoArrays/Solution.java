package leetcode.medianTwoArrays;

/*
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

The overall run time complexity should be O(log (m+n)).

 */
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // for array size n, the median is a[n/2] when size is odd and avg(a[n/2-1], a[n/2]) when size is even
        // the idea here is to merge the sorted arrays and find the elements that would be 
        // at index size/2 and size/2+1
        int v1 = 0;
        int v2 = 0;
        int size1 = nums1.length; // [3]
        int size2 = nums2.length; // [5,6]
        int size = size1 + size2;
        int i1 = 0;
        int i2 = 0;
        int j = 0;
        int medianIndex = size/2;
        while (j < size && j <= medianIndex) {
            int cur;
            if (i1 < size1 && i2 < size2) {
                if (nums1[i1] < nums2[i2]) {
                    cur = nums1[i1++];
                } else {
                    cur = nums2[i2++];
                }
            } else if (i1 < size1) {
                cur = nums1[i1++];
            } else {
                cur = nums2[i2++];
            }
            if (j == medianIndex-1) {
                v1 = cur;
            } else if (j == medianIndex) {
                v2 = cur;
            }
            j++;
        }
        if (size%2 == 1) {
            return v2;
        } else {
            return (v1 + v2)/2.0;
        }
    }
}
