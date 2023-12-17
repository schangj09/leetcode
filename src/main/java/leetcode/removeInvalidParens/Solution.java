package leetcode.removeInvalidParens;

import java.util.*;

/*
 Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid.

Return a list of unique strings that are valid with the minimum number of removals. You may return the answer in any order.

 */
public class Solution {

    Set<String> result;
    String s;

    public List<String> removeInvalidParentheses(String s) {
        this.result = new HashSet<>();
        this.s = s;
        if (isValid(s)) {
            result.add(s);
            return new ArrayList<>(result);
        }
        // count how many misplaced open brackets and close brackets
        int badClose = 0;
        int open = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                open++;
            }
            if (c == ')') {
                // if we are out of opens, then we have a misplaced close
                if (open == 0) {
                    badClose++;
                } else {
                    open--;
                }
            }
        }
        // at the end, any extra opens are the number of bad opens
        int badOpen = open;

        // we will have to remove exactly this number of open and close brackets
        recurse(0, 0, 0, badOpen, badClose, new StringBuilder());

        return new ArrayList<>(result);
    }

    // we recurse for index = 0 to n, and at each index branch the recursion
    // considering whether the current
    // index would be removed or not included in the result
    void recurse(int index, int openCount, int closeCount, int openToRemove, int closeToRemove, StringBuilder sb) {
        // the end of the recursion
        if (index == s.length()) {
            if (openToRemove == 0 && closeToRemove == 0 && isValid(sb.toString())) {
                result.add(sb.toString());
            }
            return;
        }

        // case of non-paren
        int len = sb.length();
        char c = s.charAt(index);
        if (c != '(' && c != ')') {
            sb.append(c);
            recurse(index + 1, openCount, closeCount, openToRemove, closeToRemove, sb);
            sb.deleteCharAt(len);
        } else {
            // case skip over an open
            if (c == '(' && openToRemove > 0) {
                recurse(index + 1, openCount, closeCount, openToRemove - 1, closeToRemove, sb);
            } else if (c == ')' && closeToRemove > 0) {
                // case skip over a close
                recurse(index + 1, openCount, closeCount, openToRemove, closeToRemove - 1, sb);
            }

            // case include the open
            sb.append(c);
            if (c == '(') {
                recurse(index + 1, openCount + 1, closeCount, openToRemove, closeToRemove, sb);
            } else {
                // case include the close
                recurse(index + 1, openCount, closeCount + 1, openToRemove, closeToRemove, sb);
            }
            sb.deleteCharAt(len);
        }

    }

    boolean isValid(String s) {
        int open = 0;
        int n = s.length();
        for (int i = 0; i < n && open >= 0; i++) {
            if (s.charAt(i) == '(') {
                open++;
            }
            if (s.charAt(i) == ')') {
                open--;
            }
        }
        return open == 0;
    }

    public static void main(String[] args) {
        boolean c;
        List<String> list;
        String s;

        s = "(()";
        c = new Solution().isValid(s);
        System.out.println(c);
        list = new Solution().removeInvalidParentheses(s);
        System.out.println(list);

        s = "()())()";
        c = new Solution().isValid(s);
        System.out.println(c);
        list = new Solution().removeInvalidParentheses(s);
        System.out.println(list);
    }

}
