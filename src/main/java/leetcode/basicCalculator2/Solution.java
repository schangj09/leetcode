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

        // a valid expression must always alternate between a number and operator
        // concept is to keep track of previous operator, then get the next number and the lookeahead operator
        // (always convert subtraction to a negative number)

        // a + b*c
        // if prevOp = +, if lookahead is same precedence, then add numbers, else push operand
        // if prevOp = */, then always mult or divide, afterwards if lookahead is same precedence continue, else pop stack and add

        Stack<Integer> stack = new Stack<>();
        Stack<Character> opStack = new Stack<>();
        int n = s.length();
        int i = 0;
        int operand = 0;
        Character prevOp = null;
        while (i < n) {
            char c = s.charAt(i++);
            // skip spaces
            while (i < n && c == ' ') {
                c = s.charAt(i++);
            }
            // parse the digit (handle possible leading negative sign)
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = s.charAt(i++);
            }
            int next = 0;
            while (Character.isDigit(c)) {
                next = next*10 + (c - '0');
                c = i < n ? s.charAt(i++) : '+';
            }
            next *= sign;

            // skip spaces after the number and before the next operator
            while (i < n && s.charAt(i) == ' ') {
                c = i < n ? s.charAt(i++) : '+';
            }

            if (prevOp == null) {
                operand = next;
            } else {
                // if prevOp is mult/divide, then apply it
                if (prevOp == '*' || prevOp == '/') {
                    if (prevOp == '*') {
                        operand = operand * next;
                    } else {
                        operand = operand / next;
                    }
                    // in case lookahead is lower precedence, we must pop the stack and add that value to the current operand
                    if (!stack.isEmpty() && (c == '+' || c == '-')) {
                        prevOp = opStack.pop();
                        operand = stack.pop() + (prevOp == '-' ? -1 : 1) * operand;
                    }
                } else {
                    // if lookeahead is higher precedence than prevOp, then push operand to the stack
                    if (c == '*' || c == '/') {
                        stack.push(operand);
                        opStack.push(prevOp);
                        operand = next;
                    } else {
                        // otherwise lookahead is same precedence as prevOp, so add next to operand
                        operand = operand + (prevOp == '-' ? -1 : 1) * next;
                    }
                }
            }
            prevOp = c;
        }
        // special case if anything is remaining on the stack add it
        while (!stack.isEmpty()) {
            prevOp = opStack.pop();
            operand = stack.pop() + (prevOp == '-' ? -1 : 1) * operand;
        }
        return operand;
    }
}
