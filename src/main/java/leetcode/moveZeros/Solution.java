package leetcode.moveZeros;

import java.util.Arrays;

class Solution {
    /*
     * Given an integer array nums, move all 0's to the end of it while maintaining
     * the relative order of the non-zero elements.
     * 
     * Note that you must do this in-place without making a copy of the array.
     */
    // minimal moves solution
    public void moveZeroes(int[] nums) {
        // can we do it in one pass
        // work forwards, keep a sliding window of leftmost block of zeros
        // when find a non-zero, swap it with the first zero of the window
        int n = nums.length;
        int left = 0;
        while (left < n && nums[left] != 0) {
            left++;
        }
        int right = left + 1;
        while (right < n) {
            if (nums[right] != 0) {
                nums[left++] = nums[right];
                nums[right] = 0;
            }
            right ++;
        }
    }

    public static void main(String[] args) {
        int[] nums;
        nums = new int[] {1, 2, 0, 1};
        test(nums);

        nums = new int[] {0, 1, 2, 0, 3};
        test(nums);

        nums = new int[] {1, 2, 0, 1, 0};
        test(nums);

        nums = new int[] {};
        test(nums);

        nums = new int[] {1};
        test(nums);
    }
    static void test(int[] nums) {
        System.out.println(Arrays.toString(nums));
        new Solution().moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    // O(n^2) solution
    public void moveZeroes1(int[] nums) {
        // work backwardss, find a zero and then move that zero to the end by swapping
        // one at time with each following value
        // worst case, there are 50% zeros evenly distributed and time is O((n/2)^2) =
        // O(n^2)

        for (int j = nums.length - 1; j >= 0; j--) {
            if (nums[j] == 0) {
                for (int i = j + 1; i < nums.length && nums[i] != 0; i++) {
                    swap(nums, i - 1, i);
                }
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}