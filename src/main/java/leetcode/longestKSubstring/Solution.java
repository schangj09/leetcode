package leetcode.longestKSubstring;

/*
Given a string s and an integer k, return the length of the longest substring of s such that the frequency of each character in this substring is greater than or equal to k.

if no such substring exists, return 0.
 */

public class Solution {
    public int longestSubstring(String s, int k) {
        // brute force - get freq count of each substring, check if the min freq count
        // is greater than or equal to k
        //
        // how to enumerate all the substrings of length k or greater
        int n = s.length();
        int maxLen = Integer.MIN_VALUE;
        for (int i = 0; i <= n-k; i++) {
            int[] freq = new int[26];
            // get freq count of length substring
            for (int len = 1; i + len <= n; len++) {
                int j = i + len - 1;
                char c = s.charAt(j);
                freq[c-'a']++;
                if (check(freq, k)) {
                    maxLen = Math.max(maxLen, j-i+1);
                }
            }
        }
        return maxLen > 0 ? maxLen : 0;
    }
    boolean check(int[] freq, int k) {
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0 && freq[i] < k) {
                return false;
            }
        }
        return true;
    }

}
