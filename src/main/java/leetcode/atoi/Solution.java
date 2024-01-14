package leetcode.atoi;

/*
https://leetcode.com/problems/string-to-integer-atoi/description/
Medium

Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer (similar to C/C++'s atoi f
function).

The algorithm for myAtoi(string s) is as follows:

    Read in and ignore any leading whitespace.
    
    Check if the next character (if not already at the end of the string) is '-' or '+'. Read this character in if it is 
    either. This determines if the final result is negative or positive respectively. Assume the result is positive if 
    neither is present.
    
    Read in next the characters until the next non-digit character or the end of the input is reached. The rest of the 
    string is ignored.
    
    Convert these digits into an integer (i.e. "123" -> 123, "0032" -> 32). If no digits were read, then the integer is 0. 
    
    Change the sign as necessary (from step 2).
    
    If the integer is out of the 32-bit signed integer range [-231, 231 - 1], then clamp the integer so that it remains in 
    the range. Specifically, integers less than -231 should be clamped to -231, and integers greater than 231 - 1 should be 
    clamped to 231 - 1.
    
    Return the integer as the final result.

Note:

    Only the space character ' ' is considered a whitespace character.
    Do not ignore any characters other than the leading whitespace or the rest of the string after the digits.


 */
class Solution {
    // good cases
    // " 00"
    // " 00a"
    // " 001"
    // " -001" -> -1
    // " 1.3" -> 1
    // "+2"
    // "-304"
    // "2200100100"

    // exception cases
    // ""
    // " "
    // "-"
    // "+"
    // "++1"
    // "--1"
    // "abc"
    public int myAtoi(String s) {
        // skip whitespace, record the sign of the int
        int n = s.length();
        int i = 0;
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }
        int sign = 1;
        if (i == n
                || (s.charAt(i) != '+' && s.charAt(i) != '-' && !Character.isDigit(s.charAt(i)))) {
            return 0;
        } else if (s.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if (s.charAt(i) == '+') {
            i++;
        }
        if (i == n || !Character.isDigit(s.charAt(i))) {
            return 0;
        }

        // get next digit, multiply current value by 10 and add the next digit
        // if it wraps around, then cancel and return the MAX_INTEGER or MIN_INTEGER
        int result = s.charAt(i++) - '0';
        // skip over any zeros
        while (i < n && result == 0 && s.charAt(i) == '0') {
            i++;
        }
        // in case of zero result or leading zeros
        if (result == 0) {
            // at the end of digits, then return 0
            if (i == n || !Character.isDigit(s.charAt(i))) {
                return 0;
            } else {
                // otherwise start result with the next digit
                result = s.charAt(i++) - '0';
            }
        }

        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i++) - '0';
            // check for overflow or underflow
            if (sign == 1) {
                if (result > Integer.MAX_VALUE / 10
                        || (result == Integer.MAX_VALUE / 10 && digit >= Integer.MAX_VALUE % 10)) {
                    return Integer.MAX_VALUE;
                }
            } else {
                if (result > Integer.MIN_VALUE / -10
                  || (result == Integer.MIN_VALUE / -10 && digit >= Math.abs(Integer.MIN_VALUE % 10))) {
                    return Integer.MIN_VALUE;
                  }
            }
            result *= 10;
            result += digit;
        }
        return result * sign;
    }

    public static void main(String[] args) {
        throw new RuntimeException("" + new Solution().myAtoi("-21474836482"));
    }
}