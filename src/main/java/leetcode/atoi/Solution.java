package leetcode.atoi;


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