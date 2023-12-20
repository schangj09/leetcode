package leetcode.regexMatch;

public class Solution {
    public boolean isMatch(String s, String p) {
        // use recursion
        // the idea is to match the first character and then recurse on the rest of the
        // string
        int n = s.length();
        int m = p.length();
        // note: a non-empty pattern can match an empty string, but an empty pattern only
        // matches the empty string
        if (m == 0) {
            return n == m;
        }
        char cp = p.charAt(0);
        boolean firstMatch = (n > 0 && (cp == '.' || cp == s.charAt(0)));
        char lookahead = (m > 1) ? p.charAt(1) : 0;
        // first char matches
        // when it is a wildcard, then we need to recursively check either with an empty string
        // and no wildcard or with one less char in the match string and keep the wildcard
        if (lookahead == '*') {
            return isMatch(s, p.substring(2))
                    || (firstMatch && isMatch(s.substring(1), p));
        } else {
            if (firstMatch) {
                // no wildcard and a single char match), so we can just match the rest of the
                // pattern to the rest of the string
                return isMatch(s.substring(1), p.substring(1));
            } else {
                // non matching char
                return false;
            }
        }
    }

    public static void main(String[] args) {
        String[][] s = new String[][] {
                { "aa", "a" },
                { "aa", "a*" },
                { "ab", ".*" },
                { "auuiabx", ".*ab." },
                { "mississippi", "mis*is*p*." },
                { "aab", "c*a*b" },
                { "baaaaa", "baa*a" }
        };

        for (int i = 0; i < s.length; i++) {
            System.out.println(new Solution().isMatch(s[i][0], s[i][1]));
        }
    }
}
