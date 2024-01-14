package leetcode.getSubstrings;

import java.util.*;

/*
https://leetcode.com/problems/permutations/
Medium

Get Substrings is not on leetcode, but there is an identical problem - Permutations.

 */
public class Solution {
    // get all pssible substrings of a string - in order of starting point in the string
    List<String> getSubStrings(String s) {
        int n = s.length();
        List<String> res = new ArrayList<>();
        for (int start = 0; start < n; start++) {
            for (int size = 1; size < n - start + 1; size++) {
                String sub = s.substring(start, start + size);
                res.add(sub);
            }
        }
        //res.sort((a,b) -> {return a.length() - b.length();});
        return res;
    }
    // get all pssible substrings of a string - in order of size (easier to read output)
    List<String> getSubStrings2(String s) {
        int n = s.length();
        List<String> res = new ArrayList<>();
        for (int size = 1; size <= n; size++) {
            for (int j = 0; j+size <= n; j++) {
                String sub = s.substring(j, j+size);
                res.add(sub);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().getSubStrings("123"));
        System.out.println(new Solution().getSubStrings("890123"));
    }
}
