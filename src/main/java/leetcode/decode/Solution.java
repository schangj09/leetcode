package leetcode.decode;

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
