package leetcode.regexMatch;

public class Solution {
    public boolean isMatch(String s, String p) {
        // greedy consume as much as possible for * and backtrack as needed for a match

        int n = s.length();
        int m = p.length();
        int ip = 0;
        int is = 0;
        while (is < n && ip < m) {
            char cs = s.charAt(is++);
            char cp = p.charAt(ip++);
            if (cp == '*') {
                // bad expression
                return false;
            }
            char lookahead = (ip < m) ? p.charAt(ip) : 0;
            // check for wildacard match ".*" or "a*"
            if (lookahead == '*') {
                // increment ip for the lookahead char
                ip++;
                // if wildcard match, then go to end of string
                // otherwise forward until cs is not equal to prev, then back up 
                if (cp == '.') {
                    is = n;
                } else {
                    char wildcardmatch = cp; // the character from the pattern we are looking for
                    while (wildcardmatch == cs && is < n) {
                        cs = s.charAt(is++);
                    }
                    // if cs doesn't match, then we went past it so back up one
                    if (wildcardmatch != cs) {
                        is--;
                    }
                }
            } else {
                // case: no wildcard 
                // no match if characters don't match
                if (cp != '.' && cp != cs) {
                    return false;
                }
            }
        }
        return is == n && ip == m;
    }

    public static void main(String[] args) {
        String[][] s = new String[][] {
            // {"aa", "a"},
            //{"aa", "a*"}
            // {"ab", ".*"},
            // {"auuiabx", ".*ab."},
            // {"mississippi", "mis*is*p*."},
            {"aab", "c*a*b"}
        };

        for (int i = 0; i < s.length; i++) {
            System.out.println(new Solution().isMatch(s[i][0], s[i][1]));
        }
    }
}
