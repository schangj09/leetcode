package leetcode.regexMatch;

public class Solution {
    public boolean isMatch(String s, String p) {
        // greedy consume as much as possible for * and backtrack as needed for a match

        int n = s.length();
        int m = p.length();
        int ip = 0;
        int is = 0;
        char prev = 0;
        while (is < n && ip < m) {
            char cp = p.charAt(ip);
            char cs = s.charAt(is);
            if (cp == '.' || cp != '*') {
                is++;
                prev = cp;
            } else {
                if (prev == '.') {
                    is = n;
                } else {
                    // if cs matches prev, so need to advance is at least 1
                    while (is < n && s.charAt(is) == prev) {
                        is++;
                    }
                    // if at the end, we matched all characters, but otherwise, we need to back up one
                    if (is < n) {
                        is--;
                    }
                }
            }
            ip++;
        }
        return is == n && ip == m;
    }

    public static void main(String[] args) {
        String[][] s = new String[][] {
            {"aa", "a"},
            {"aa", "a*"},
            {"ab", ".*"},
            {"auuiabx", ".*ab."},
            {"mississippi", "mis*is*p*."}
        };

        for (int i = 0; i < s.length; i++) {
            System.out.println(new Solution().isMatch(s[i][0], s[i][1]));
        }
    }
}
