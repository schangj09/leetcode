/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package leetcode.longestAfterDelete1;

/**
 *
 * @author Jeffrey Schang
 */
public class Solution {

    public int longestSubarray(int[] nums) {
        // one way is we can use a sliding window that includes one zero
        // move the left side up when we encounter a second zero on the right
        //
        // however, we can also leverage the fact that we are looking for the
        // longest, so we don't need to move the left side by more than one
        // instead keep count of the zeros. So, we move left if next is a zero
        // and count is more than 1 and otherwise, the sliding window grows.

        int n = nums.length;
        int left = 0;
        int right = 1;
        int count = nums[0] == 0 ? 1 : 0;
        while (right < n) {
            if (nums[right] == 0) {
                // adding a zero to the right
                count++;
                // need to move the left by 1 and decrement count if a zero drops off the left side
                if (count > 1) {
                    if (nums[left] == 0) {
                        count--;
                    }
                    left++;
                }
            }
            right++;
        }
        // minus 1 because we always have to delete one (either a zero or a one)
        return right - left - 1;
    }
}
