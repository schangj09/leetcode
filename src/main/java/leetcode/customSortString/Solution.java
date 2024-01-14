package leetcode.customSortString;

/*
https://leetcode.com/problems/custom-sort-string/
Medium

You are given two strings order and s. All the characters of order are unique and were sorted in some custom order 
previously.

Permute the characters of s so that they match the order that order was sorted. More specifically, if a character x occurs 
before a character y in order, then x should occur before y in the permuted string.

Return any permutation of s that satisfies this property.


 */
public class Solution {
    public String customSortString(String order, String s) {
        // idea is to get freq count of each character in s that occurs in order. then output 
        // the count of each matching character in order plus all the non-matching chars.
        // Algorithm:
        // First go over order and make a map of characters.
        // Next iterate over s, if c is in orderMap, count it, otherwise append it to result.
        // Finally append the counts of matching characters.

        // ex: order "cba" s: "bbcac"

        StringBuffer sb = new StringBuffer(s.length());
        boolean[] orderMap = new boolean[26];
        char[] counts = new char[26];
        for (int i = 0; i < order.length(); i++) {
            orderMap[order.charAt(i) - 'a'] = true;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!orderMap[c-'a']) {
                sb.append(c);
            } else {
                counts[c-'a']++;
            }
        }
        for (int i = 0; i < order.length(); i++) {
            char c = order.charAt(i);
            for (int j = counts[c - 'a']; j > 0; j--) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
