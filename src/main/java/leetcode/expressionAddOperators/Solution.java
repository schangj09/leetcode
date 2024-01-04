package leetcode.expressionAddOperators;

import java.util.*;

/*
Given a string num that contains only digits and an integer target, return all possibilities to insert the binary operators '+', '-', and/or '*' between the digits of num so that the resultant expression evaluates to the target value.

Note that operands in the returned expressions should not contain leading zeros.


 */
class Solution {
    List<String> results = new ArrayList<>();
    String s;
    int target;
    public List<String> addOperators(String num, int target) {
        this.s = num;
        this.target = target;

        // sounds like backtracking, at each step, we can choose to 
        // include the next digit as an operand or insert an operator
        // the twist is how to calculate the result and backtrack
        // - make a stack of values that represents operands for a 
        // sum
        // cases for handling the next digit
        // case: include next digit to the last operand on the stack
        // case: insert a '-' before the next
        // case: insert a '+'
        // case: insert a '*'

        Stack<Long> sums = new Stack<>();
        sums.push(0L);
        backtrack(0, 0, sums, new StringBuilder());
        results.sort(Comparator.naturalOrder());
        return results;
    }

    // example: 10256, target 46, ans is ["102-560"]
    void backtrack(int i, long lastOperand, Stack<Long> sums, StringBuilder sb) {
        if (i == s.length()) {
            if (sum(sums) == target) {
                results.add(sb.toString());
            }
            return;
        }
        char c = s.charAt(i);

        // case: add next digit to the last operand on the stack, unless lastOperand is a 0
        // special case for 0 at the beginning of the string since we use 0 for starting lastOperand
        long nextDigit = c - '0';
        long topSum = sums.peek();
        boolean isLeadingZero = i > 0 && lastOperand == 0;
        if (!isLeadingZero) {
            sums.pop();
            // calculate new last operand from sb
            long newLastOp = lastOperand * 10 + nextDigit;
            sb.append(c);
            // the top of sums could be equal to lastOperand or it could be a multiple of lastOperand
            if (topSum == lastOperand) {
                sums.push(newLastOp);
            } else {
                sums.push((topSum/lastOperand) * newLastOp);
            }
            backtrack(i+1, newLastOp, sums, sb);
            sb.delete(sb.length()-1, sb.length());
            sums.pop();
            sums.push(topSum);
        }


        // cases to insert an operator, unless at the beginning
        if (i != 0) {
            // case: insert a '-' before the next digit
            sb.append('-').append(c);
            sums.push(-nextDigit);
            backtrack(i+1, -nextDigit, sums, sb);
            sb.delete(sb.length()-2, sb.length());
            sums.pop();

            // case: insert a '+'
            sb.append('+').append(c);
            sums.push(nextDigit);
            backtrack(i+1, nextDigit, sums, sb);
            sb.delete(sb.length()-2, sb.length());
            sums.pop();

            // case: insert a '*'
            sb.append('*').append(c);
            sums.pop();
            sums.push(topSum * nextDigit);
            backtrack(i+1, nextDigit, sums, sb);
            sb.delete(sb.length()-2, sb.length());
            sums.pop();
            sums.push(topSum);
        }
    }

    long sum(Stack<Long> sums) {
        long sum = 0;
        for (int i = 0; i < sums.size(); i++) {
            sum += sums.get(i);
        }
        return sum;
    }
}