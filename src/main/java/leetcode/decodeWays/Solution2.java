package leetcode.decodeWays;

/*
 * https://leetcode.com/problems/decode-ways-ii/
 * Hard

A message containing letters from A-Z can be encoded into numbers using the following mapping:

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"

To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping 
above (there may be multiple ways). For example, "11106" can be mapped into:

    "AAJF" with the grouping (1 1 10 6)
    "KJF" with the grouping (11 10 6)

Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".

In addition to the mapping above, an encoded message may contain the '*' character, which can represent any digit from '1' 
to '9' ('0' is excluded). For example, the encoded message "1*" may represent any of the encoded messages "11", "12", "13", 
"14", "15", "16", "17", "18", or "19". Decoding "1*" is equivalent to decoding any of the encoded messages it can represent.

Given a string s consisting of digits and '*' characters, return the number of ways to decode it.

Since the answer may be very large, return it modulo 10^9 + 7.


 */
public class Solution2 {
    static int MODULO = (int)Math.pow(10, 9) + 7;
    static int[] star = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    static int[][] vals = new int[][] {
        {0},{1},{2},{3},{4},{5},{6},{7},{8},{9}
    };
    long[] memo;

    /*
     Examples:
     "**" -> 9*9 + 9*1 = 90
     "1*" -> 1*9 + 9*1 = 18
     "0*" -> 0
     "2**" 1*90 + 6*9 = 126
     "2*0*0*6*" -> -> 9*0 + 2*9 = 18
     */

    public int numDecodings(String s) {
        memo = new long[s.length()];
        return (int)count(s, 0);    
    }
    long count(String s, int i) {
        int n = s.length();
        if (i == n) {
            return 1;
        }
        if (memo[i] != 0) {
            return memo[i];
        }
        int[] v = getVal(s.charAt(i));
        if (v[0] == 0) {
            return 0;
        }

        if (i == n-1) {
            return v.length;
        }

        int[] v1 = getVal(s.charAt(i+1));
        memo[i] = (v.length * count(s, i+1)) % MODULO;
        // for each combination of v and v1 that can have 2 ways, mulitply count(i+2) and add
        int c = 0;
        for (int j = 0; j < 2 && j < v.length; j++) {
            for (int k = 0; k < v1.length; k++) {
                if (v[j]*10 + v1[k] <= 26) {
                    c++;
                }
            }
        }
        memo[i] = (memo[i] + c * count(s, i+2)) % MODULO;
        return memo[i];
    }

    int[] getVal(char c) {
        if (c == '*') {
            return star;
        } else {
            return vals[c - '0'];
        }
    }
}
