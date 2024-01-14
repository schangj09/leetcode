package leetcode.removeInvalidParens;

import java.util.*;

/*
 * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
 * Medium
 * 
Given a string s of '(' , ')' and lowercase English characters.

Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses 
string is valid and return any valid string.

Formally, a parentheses string is valid if and only if:

    It is the empty string, contains only lowercase characters, or
    It can be written as AB (A concatenated with B), where A and B are valid strings, or
    It can be written as (A), where A is a valid string.

 */
public class SolutionOneAnswer {
    public String minRemoveToMakeValid(String s) {
        // to check valid, we can counts the open parens and decrement when 
        // closed
        // since, we just need to find one solution, one approach is to 
        // keep track of each open paren index in the stack, then when 
        // a close paren matches, pop it from the stack and mark those indices as matched. 
        // At the end, all unmarked parens must be removed.
        
        int n = s.length();
        boolean[] keep = new boolean[n];
        Deque<Integer> st = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                st.push(i);
            } else if (c == ')') {
                // mark matched parens, don't mark unmatched close paren
                if (!st.isEmpty()) {
                    keep[st.pop()] = true;
                    keep[i] = true;
                }
            } else {
                // not a paren, keep it
                keep[i] = true;
            }
        }
        // any unmatched open parens are still in the stack, but not marked for keep
        // so we will remove them

        // now build the return string
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            if (keep[i]) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[][] s = new String[][] {
                { "bba" },
                { "(aa" },
                { "B((C)D))" },
                { "))x(y)(" },
        };

        for (int i = 0; i < s.length; i++) {
            System.out.println(new SolutionOneAnswer().minRemoveToMakeValid(s[i][0]));
        }
    }
}
