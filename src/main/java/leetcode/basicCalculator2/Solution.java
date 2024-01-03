package leetcode.basicCalculator2;

import java.util.*;

/*
Given a string s which represents an expression, evaluate this expression and return its value. 

The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of [-2^31, 2^31 - 1].

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as  eval()

s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
s is valid.
s length up to 50k characters.
 */
public class Solution {
    public int calculate(String s) {
        // we want to calculate the result as we go and push the result on to a stack when precedence rules apply
        // a) to simplify, treat subtraction as plus negative number
        // b) if '-' follows another operator (or at beginning), then it must be a negative sign, else it is a subtraction
        // ex: 3+2*-5 -> operator '*' is higher precedence than '+', so push operand and sign
        // ex: 3*7/2-5

        // get a number p1, get a operator prevOp, get a number p2, get a operator (calc the prev expression or push to stack)

        Stack<Integer> stack = new Stack<>();
        // previous operator
        char prevOp = '#';

        int sign = 1;
        int p1 = 0;
        int p2 = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i++);
            if (Character.isDigit(c)) {
                if (prevOp == '#') {
                    p1 = p1*10 + (c - '0');
                } else {
                    p2 = p2*10 + (c - '0');
                }
            } else if (c == '+' || c == '-') {
                // either calc prev expression or push p1 and operator to the stack
                if (prevOp == '+') {
                    p1 = p1 + sign * p2;
                    p2 = 0;
                } else {
                    // if prevOp was higher precedence, then we must pop the stack and apply the operation from the stack
                    if ((prevOp == '*' || prevOp == '/') && stack.size() > 0) {
                        int stackSign = stack.pop();
                        int stackOperand = stack.pop();
                        p1 = stackOperand + stackSign * p1;
                        p2 = 0;
                    }
                }
                if (c == '-') {
                    sign = -1;
                } else {
                    sign = 1;
                }
                prevOp = '+'; // never keep '-' as a prevOp
            } else if (c == '*' || c == '/') {
                if (c == '*') {
                    operand = operand 
                }
            }
        }

    }
}
