package leetcode.decodeWays;

/*
 A message containing letters from A-Z can be encoded into numbers using the following mapping:

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"

To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, "11106" can be mapped into:

    "AAJF" with the grouping (1 1 10 6)
    "KJF" with the grouping (11 10 6)

Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".

Given a string s containing only digits, return the number of ways to decode it.

The test cases are generated so that the answer fits in a 32-bit integer.


 */
public class Solution {
    boolean decodeFailed = false;

    public int numDecodings(String s) {
        int[] a = new int[s.length()];
        return count(s, 0, a);
    }

    int count(String s, int i, int[] a) {
        int n = s.length();
        if (i == n) {
            return 1;
        }
        if (a[i] != 0) {
            return a[i];
        }
        if (decodeFailed) {
            return 0;
        }
        int v = getVal(s.charAt(i));
        if (i == 0 && v == 0) {
            return 0;
        } else if (i == n - 1) {
            a[i] = (v == 0 ? 0 : 1);
        } else {
            int v1 = getVal(s.charAt(i + 1));

            if (v == 0 || v > 2 || v1 > 7) {
                // special case 0 where it can't be decoded
                if ((v == 0 || v > 2) && v1 == 0) {
                    // set a marker
                    decodeFailed = true;
                    a[i] = 0;
                } else {
                    // only 1 way to decode this char, so the total count is the same as a[i+1]
                    a[i] = count(s, i + 1, a);
                }
            } else {
                // only 1 extra way of decoding
                a[i] = 1 + count(s, i + 1, a);
            }
        }
        return a[i];
    }

    int getVal(char c) {
        return c - 'A' + 1;
    }
}
