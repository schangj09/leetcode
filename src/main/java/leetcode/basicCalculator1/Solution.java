package leetcode.basicCalculator1;

import java.util.*;

/*
https://leetcode.com/problems/basic-calculator/description/
Hard

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
        //
        // Note: my previous solution (see previous git commit) was complicated, the 
        // solution below is simpler - copied from the editorial

        Stack<Integer> stack = new Stack<Integer>();
        int operand = 0;
        int result = 0; // For the on-going result
        int sign = 1;  // 1 means positive, -1 means negative

        for (int i = 0; i < n; i++) {

            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {

                // Forming operand, since it could be more than one digit
                operand = 10 * operand + (int) (ch - '0');

            } else if (ch == '+') {

                // Evaluate the expression to the left,
                // with result, sign, operand
                result += sign * operand;

                // Save the recently encountered '+' sign
                sign = 1;

                // Reset operand
                operand = 0;

            } else if (ch == '-') {

                result += sign * operand;
                sign = -1;
                operand = 0;

            } else if (ch == '(') {

                // Push the result and sign on to the stack, for later
                // We push the result first, then sign
                stack.push(result);
                stack.push(sign);

                // Reset operand and result, as if new evaluation begins for the new sub-expression
                sign = 1;
                result = 0;

            } else if (ch == ')') {

                // Evaluate the expression to the left
                // with result, sign and operand
                result += sign * operand;

                // ')' marks end of expression within a set of parenthesis
                // Its result is multiplied with sign on top of stack
                // as stack.pop() is the sign before the parenthesis
                result *= stack.pop();

                // Then add to the next operand on the top.
                // as stack.pop() is the result calculated before this parenthesis
                // (operand on stack) + (sign on stack * (result from parenthesis))
                result += stack.pop();

                // Reset the operand
                operand = 0;
            }
        }
        return result + (sign * operand);
    }
}
