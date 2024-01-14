package leetcode.groupShiftedStrings;

import java.util.*;

/*
https://leetcode.com/problems/group-shifted-strings/
Medium

We can shift a string by shifting each of its letters to its successive letter.

    For example, "abc" can be shifted to be "bcd".

We can keep shifting the string to form a sequence.

    For example, we can keep shifting "abc" to form the sequence: "abc" -> "bcd" -> ... -> "xyz".

Given an array of strings strings, group all strings[i] that belong to the same shifting sequence. You may return the answer 
in any order.

 
 */
public class Solution {
    // The idea is a hash function that maps strings based on shifting sequence
    // 2 char string is same shift sequence if diff between chars is the same
    // ab = jk -> a-b = -1, j-k = -1, modulo 26 = 25
    // can map diff to a character, then hash map of the hash strings

    public List<List<String>> groupStrings(String[] strings) {

        Map<String, List<String>> m = new HashMap<>();
        for (String s : strings) {
            System.out.println(String.format("%s -> %s", s, hashString(s)));
            List<String> shift = m.computeIfAbsent(hashString(s), (x) -> new ArrayList<>());
            shift.add(s);
        }
     
        return new ArrayList<>(m.values());
    }

    String hashString(String s) {
        if (s.length() == 1) {
            return "0";
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 1; i < s.length(); i++) {
                char c = (char)('a' + (s.charAt(i) - s.charAt(i-1) + 26)%26);
                sb.append(c);
            }
            return sb.toString();
        }


    }
}