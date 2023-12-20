package leetcode.validNumber;

import java.util.Arrays;
import java.util.List;

/*
A valid number can be split up into these components (in order):

    A decimal number or an integer.
    (Optional) An 'e' or 'E', followed by an integer.

A decimal number can be split up into these components (in order):

    (Optional) A sign character (either '+' or '-').
    One of the following formats:
        One or more digits, followed by a dot '.'.
        One or more digits, followed by a dot '.', followed by one or more digits.
        A dot '.', followed by one or more digits.

An integer can be split up into these components (in order):

    (Optional) A sign character (either '+' or '-').
    One or more digits.

 */
public class Solution {
    public boolean isNumber(String s) {
        // try {
        // Double.parseDouble(s);
        // return true;
        // } catch (NumberFormatException e) {
        // return false;
        // }

        // Pattern p =
        // Pattern.compile("(\\+|\\-)?(\\d+(\\.(\\d+)?)?|\\.\\d+)([eE](\\+|\\-)?\\d+)?");
        // return p.matcher(s).matches();

        // parseInteger -> parseSign(optional), parseDigits
        // parseDecimal -> parseSign(optional), parseDigits(optional), parseDot(required
        // or optional), parseDigits(required or optional)
        // parseExp -> (optional) parseE, parseInteger

        boolean hasDigits = false;
        char[] c = toArray(s);

        int i = 0;
        int j = parseDecimalOrInteger(c, i);
        hasDigits = (j > i);
        i = j;

        // now parse the exponent if we found a number
        if (!hasDigits) {
            return false;
        }
        j = parseE(c, i);
        // if we found an E, then an integer is required
        if (j > i) {
            i = j;
            j = parseInteger(c, i);
            return j > i && j == c.length;
        }
        // otherwise no E, so its valid if we got to the end of the string
        return j == c.length;
    }

    int parseDecimalOrInteger(char[] c, int i) {
        int start = i;
        int j = parseInteger(c, i);
        boolean isDotRequired = (j == i);
        if (isDotRequired) {
            j = parseSign(c, i);
        }
        i = j;
        j = parseDot(c, i);
        if (j > i) {
            // case: found a dot
            i = j;
            j = parseDigits(c, i);
            // if dot is required, then digits is also required
            if (isDotRequired && i == j) {
                return start;
            }
        } else if (isDotRequired) {
            // case: didn't found a dot and it is required
            return start;
        }
        return j;
    }

    int parseInteger(char[] c, int i) {
        int j = parseSign(c, i); // always optional
        int k = parseDigits(c, j); // required
        return k > j ? k : i;
    }

    int parseSign(char[] c, int i) {
        return i < c.length && (c[i] == '+' || c[i] == '-') ? i + 1 : i;
    }

    int parseDot(char[] c, int i) {
        return i < c.length && c[i] == '.' ? i + 1 : i;
    }

    int parseE(char[] c, int i) {
        return i < c.length && (c[i] == 'e' || c[i] == 'E') ? i + 1 : i;
    }

    int parseDigits(char[] c, int i) {
        while (i < c.length && Character.isDigit(c[i])) {
            i++;
        }
        return i;
    }

    char[] toArray(String s) {
        char c[] = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            c[i] = s.charAt(i);
        }
        return c;
    }

    public static void main(String[] args) {
        List<String> strings = Arrays.asList(
                "",
                "2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789",
                "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53");

        for (String string : strings) {
            System.out.print(string + ", ");
            System.out.println(new Solution().isNumber(string));
        }
    }
}
