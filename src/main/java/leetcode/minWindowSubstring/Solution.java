package leetcode.minWindowSubstring;

import java.util.*;

/*
Given two strings s and t of lengths m and n respectively, return the minimum window
substring of s such that every character in t (including duplicates) is included in the window. 
If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.

 */
public class Solution {

    // the idea is to generate a frequency map of characters in t, then iterate a
    // sliding window over
    // s, keeping track of when all the characters are found. Once we find all then
    // record the
    // window length if it is less than previously (track min length). On finding
    // new window, slide the left
    // pointer to the next character in t and continue.
    public String minWindow(String s, String t) {
        int m = s.length();

        // map of freq in t
        Map<Character, Integer> f = fmap(t);

        // map of freq counts in window
        // Note: counts in w can be more than f for a match
        Map<Character, Integer> w = new HashMap<>();

        int left = 0;
        while (left < m && !f.containsKey(s.charAt(left))) {
            left++;
        }

        int right = left;
        String minWindow = null;
        while (right < m) {
            char c = s.charAt(right++);
            // add one to the window map if it still to be counted
            if (f.containsKey(c)) {
                incr(w, c, 1);

                // check if we have a match, then slide the left pointer and decrement w
                // appropriately
                if (isMatch(w, f)) {

                    // Note: we remove chars from left until no longer a match before getting the minWindow
                    // since there could be extra repeated characters at the beginning of the window
                    boolean done = false;
                    while (left < right && !done) {
                        char removec = s.charAt(left);
                        // Note: if w contains a key, then it must be non-zero or else we would already
                        // be done
                        if (w.containsKey(removec)) {
                            // decrement
                            incr(w, removec, -1);

                            // we are done if w count of the removed char is less than the f count
                            done = w.get(removec) < f.get(removec);
                        }
                        left++;
                    }
                    // now get window from left-1 to right
                    if (minWindow == null || minWindow.length() > right - left + 1) {
                        minWindow = s.substring(left - 1, right);
                    }
                    // skip over any more unneeded left characters
                    while (left < right && !w.containsKey(s.charAt(left))) {
                        left++;
                    }
                }
            }
        }
        return minWindow == null ? "" : minWindow;
    }

    boolean isMatch(Map<Character, Integer> w, Map<Character, Integer> f) {
        // matches if all the counts in w are >= counts in f
        if (w.size() == f.size()) {
            for (Character c : w.keySet()) {
                if (w.get(c) < f.get(c)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    Map<Character, Integer> fmap(String t) {
        Map<Character, Integer> f = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            incr(f, t.charAt(i), 1);

        }
        return f;
    }

    void incr(Map<Character, Integer> f, char c, int increment) {
        f.put(c, increment + f.getOrDefault(c, 0));
    }

    public static void main(String[] args) {
        String[][] s = new String[][] {
                { "bba", "ab" },
                { "aa", "a" },
                { "ADOBECODEBANC", "ABC" },
                { "ba", "a" },
        };

        for (int i = 0; i < s.length; i++) {
            System.out.println(new Solution().minWindow(s[i][0], s[i][1]));
        }
    }

}
