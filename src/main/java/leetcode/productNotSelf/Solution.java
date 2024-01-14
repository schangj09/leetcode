package leetcode.productNotSelf;

/**
 * https://leetcode.com/problems/product-of-array-except-self/
 * Medium
 * 
Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].

The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

You must write an algorithm that runs in O(n) time and without using the division operation.
 */
public class Solution {

    /**
     * Second attempt using hint - left and right product arrays
     */
    public int[] productExceptSelf(int[] nums) {
        // compute left products for each nums
        // then compute right product for each nums
        int n = nums.length;
        int[] a = new int[n];
        // left products, not including self
        a[0] = 1;
        for (int i = 1; i < n; i++) {
            a[i] = a[i-1]*nums[i-1];
        }
        // accumulate right products, not including self
        // note: a[n-1] is already set correctly
        int rightProd = 1;
        for (int i = n-2; i >= 0; i--) {
            rightProd = rightProd*nums[i+1];
            a[i] = a[i]*rightProd;
        }
        return a;
    }

    /**
     * First attempt using brute force - it times out\
     * space complexity is constant, but time is O(N^2) - if we had an O(1)
     * exponent it would work, but probably not an option...
     */
    public int[] productExceptSelf1(int[] nums) {
        // constraint the values are between -30 and 30, so we can make a frequency count
        // of each value and use that to produce the product for each num
        int n  = nums.length;
        int[] f = new int[61];
        // count frequencies of numbers - values must be between -30 and 30 or we get an array out of bounds exception
        for (int i = 0; i < n; i++) {
            f[nums[i]+30]++;
        }

        // get products
        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            int v = 1;
            for (int j = 0; j < 61; j++) {
                int freq = (nums[i]+30 == j) ? f[j]-1 : f[j];
                for (int k = 0; k < freq; k++) {
                    v *= (j-30);
                }
            }
            answer[i] = v;
        }
        return answer;
    }


}
