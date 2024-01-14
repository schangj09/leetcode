package leetcode.decode;

/*
 * https://leetcode.com/problems/decode-string/description
 * Medium
 * 
Given an encoded string, return its decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].

The test cases are generated so that the length of the output will never exceed 105.
 */
class Solution {
    public String decodeString(String s) {
        // recursively need to decode the string inside the square brackets
        // the recursion ends when we find an unmatched right square bracket
        // expressed like a regex:
        // ((chars)*(\encoded))?(chars)*

        StringBuffer sb = new StringBuffer();
        decode(s, 0, sb);
        return sb.toString();
    }

    /**
     * returns the index after the string has been decoded
     */
    private int decode(String s, int offset, StringBuffer sb) {
        int idx = offset;
        while (idx < s.length()) {
            char c = s.charAt(idx);
            if (c == ']') {
                // return the index after the close bracket ']'
                return idx+1;
            } else if (Character.isAlphabetic(c)) {
                sb.append(c);
            } else if (Character.isDigit(c)) {
                // starting an inner decode - get the number of repitions
                StringBuffer num = new StringBuffer();
                do {
                    num.append(c);
                    idx++;
                    c = s.charAt(idx);
                } while (Character.isDigit(c));
                int repetitions = Integer.parseInt(num.toString());

                // now cur is the index of an open bracket '[', so we recursively call to get an encoded string
                StringBuffer enc = new StringBuffer();
                idx = decode(s, idx+1, enc);
                // note: idx is now at the index after the close bracket ']'

                // append the encoded string the given repititions
                while (repetitions > 0) {
                    sb.append(enc.toString());
                    repetitions--;
                }
            }
        }
        return idx;
    }
}
