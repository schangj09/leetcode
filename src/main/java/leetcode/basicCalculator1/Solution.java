package leetcode.basicCalculator1;

import java.util.*;

/*
Given a string s which represents an expression, evaluate this expression and return its value. 

The operators are only addition and substraction and grouping parentheses.

You may assume that the given expression is always valid. All intermediate results will be in the range of [-2^31, 2^31 - 1].

Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as  eval()

s consists of integers and operators ('+', '-', '(', ')') separated by some number of spaces.
'-' can be unary expression, but not '+'
s is valid.
s length up to 50k characters.
 */
public class Solution {

    public int calculate(String s) {
        int n = s.length();
        // use a stack to push each value (use negative value for subtraction) and each
        // open paren
        // on close paren or end, pop values and add until we get to the top
        // in order to handle substraction, we can iterate in reverse
        // to handle unary - for an expression, push a 0 preceding the -
        Stack<Integer> numbers = new Stack<>();
        numbers.push(0);

        Stack<Character> ops = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ' ') {
                i--;
            } else if (Character.isDigit(c)) {
                int operand = 0;
                int pow = 1;
                while (Character.isDigit(c) && i >= 0) {
                    operand = pow * (c - '0') + operand;
                    pow *= 10;
                    i--;
                    c = i >= 0 ? s.charAt(i) : '0';
                }
                numbers.push(operand);
            } else if (c == '(') { // reverse order, so push the close paren and pop expressions on open paren
                // pop the stack and sum up the values
                int val = numbers.pop();
                while (ops.peek() != ')') { // iterate to a close paren
                    char op = ops.pop();
                    val = val + calc(op, val, numbers.pop());
                }
                numbers.push(val);
                ops.pop(); // pop the open paren
                i--;
            } else {
                // push the operand or calculate from the stack
                ops.push(c);
                i--;
            }
        }
        int val = numbers.pop();
        while (!ops.isEmpty() && ops.peek() != ')') {
            char op = ops.pop();
            val = val + calc(op, val, numbers.pop());
        }
        return val;
    }

    // perform a-b or a+b
    int calc(char op, int a, int b) {
        if (op == '-') {
            return a - b;
        } else {
            return a + b;
        }
    }

}
