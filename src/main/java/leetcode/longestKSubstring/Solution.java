package leetcode.longestKSubstring;

import java.util.*;

/*
Given a string s and an integer k, return the length of the longest substring of s such that the frequency of each character in this substring is greater than or equal to k.

if no such substring exists, return 0.
 */

public class Solution {
    public int longestSubstring(String s, int k) {

        // 2 approaches from editorial:
        // a) divide and conquer, by splitting at a character whose count is less than k, then 
        // find on left and right sides - faster in general case, but still O(n^2) worst case
        // b) sliding window using the "max number of unique characters" from 1 to total unique characters in the string
        // each iteration of sliding window takes N iterations, up to 26 times over the string to get the max substring
        int n = s.length();

        // implementation of b)
        // first count max unique charactes in the string (actually don't really need to do this, it just means 
        // there can be extra unnecessary passes over the string, but still a constant number of passes)
        int numUnique = 26;

        // sliding window looking for longest substring with m unique characters
        int maxLen = Integer.MIN_VALUE;
        for (int m = 1; m <= numUnique; m++) {
            int left = 0;
            int right = 0;
            Map<Character, Integer> freq = new LinkedHashMap<>();
            // (except the first time) for each iteration, the number of characters in freq is m, so 
            // the loop is done when right is past the end
            while (right < n) {
                // move the left window to remove characters until we get to m-1
                while (freq.keySet().size() >= m) {
                    decrement(freq, s.charAt(left));
                    left++;
                }
                // move right to add characters up to m total unique characters in the substring
                while (right < n && (freq.keySet().size() < m || freq.containsKey(s.charAt(right)))) {
                    add(freq, s.charAt(right));
                    right++;
                }
                // track the longest substring that meets the criteria
                if (check(freq, k)) {
                    maxLen = Math.max(maxLen, right - left);
                }
            }
        }

        return maxLen > 0 ? maxLen : 0;
    }

    boolean check(Map<Character, Integer> freq, int k) {
        for (char c : freq.keySet()) {
            if (freq.get(c) < k) {
                return false;
            }
        }
        return true;
    }

    // increment
    void add(Map<Character, Integer> freq, char c) {
        freq.put(c, 1+freq.getOrDefault(c, 0));
    }

    // returns true if the character is removed from the map
    boolean decrement(Map<Character, Integer> freq, char c) {
        int v = freq.get(c);
        if (v == 1) {
            freq.remove(c);
            return true;
        } else {
            freq.put(c, v-1);
            return false;
        }

    }

}
