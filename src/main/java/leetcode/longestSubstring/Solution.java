package leetcode.longestSubstring;
import java.util.*;

/*
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
 * Medium
 * 
Given a string s, find the length of the longest
substring
without repeating characters.

Constraints:

    0 <= s.length <= 5 * 104
    s consists of English letters, digits, symbols and spaces.

 */
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        // keep a pointer at start and end of substring, hashset of characters in it
        // move the start up when the next character is found in the current set
        Set<Character> characters = new HashSet<>();
        int n = s.length();
        int start = 0;
        int maxlen = 0;
        for (int c = 0; c < n; c++) {
            char chr = s.charAt(c);
            if (!characters.contains(chr)) {
            } else {
                // move the start up, so that substring is not including chr, the set doesn't change
                boolean found = false;
                while (!found) {
                    maxlen = Math.max(maxlen, c - start);
                    if (s.charAt(start) == chr) {
                        found = true;
                    }
                    characters.remove(s.charAt(start));
                    start++;
                }
            }
            characters.add(chr);
        }
        maxlen = Math.max(maxlen, n - start);
        return maxlen;
    }

    public static void main(String[] args) {
        Solution inst = new Solution();
        String s;
        int expected;
        s = "";
        expected = 0;
        inst.test(s, expected);

        s = "abc";
        expected = 3;
        inst.test(s, expected);

        s = "abcssSDfaasd";
        expected = 5;
        inst.test(s, expected);

        s = "aaaaa";
        expected = 1;
        inst.test(s, expected);

        s = "1 2 3";
        expected = 3;
        inst.test(s, expected);

        s = "..9* 3";
        expected = 5;
        inst.test(s, expected);

        s = ".9* 33";
        expected = 5;
        inst.test(s, expected);

    }

    public void test(String s, int expected) {
        int actual = lengthOfLongestSubstring(s);
        if (expected != actual) {
            throw new RuntimeException(String.format("%s, expected: %d, actual: %d", s, expected, actual));
        }
    }
}
