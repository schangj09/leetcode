package leetcode.multiplyStrings;

/*
Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.

Note: You must not use any built-in BigInteger library or convert the inputs to integer directly.

Constraints:

    1 <= num1.length, num2.length <= 200
    num1 and num2 consist of digits only.
    Both num1 and num2 do not contain any leading zero, except the number 0 itself.

 */
public class Solution {
    public String multiply(String num1, String num2) {
        // algorithm for multiplication:
        // 1678
        //x 345
        //-----
        // (8*345)
        //+(7*345)0
        //+(6*345)00
        //+(1*345)000

        String l1 = num1.length() > num2.length() ? num1 : num2;
        String l2 = num1.length() > num2.length() ? num2 : num1;
        int n = l1.length();
        String[] vals = new String[n];
        for (int i = 0; i < n; i++) {
            StringBuffer sb = new StringBuffer();
            sb.append("0".repeat(i));
            multAppendReversed(l2, l1.charAt(n-i-1) - '0', sb);
            sb.reverse();
            vals[i] = sb.toString();
        }
        return sum(vals);
    }

    void multAppendReversed(String s, int d, StringBuffer sb) {
        int n = s.length();
        int carry = 0;
        for (int i = n-1; i >= 0; i--) {
            char c = s.charAt(i);
            int v = (c - '0')*d + carry;
            sb.append((char)(v%10 + '0'));
            carry = v/10;
        }
        if (carry != 0) {
            sb.append((char)(carry + '0'));
        }
    }

    String sum(String[] s) {
        StringBuffer sb = new StringBuffer();
        int n = s.length;
        int carry = 0;
        boolean done = false;
        for (int k = 0; !done; k++) {
            int placeSum = 0;
            done = true;
            for (int i = 0; i < n; i++) {
                // for each string, if it still has characters, then sum
                if (k < s[i].length()) {
                    char c = s[i].charAt(s[i].length() - k - 1);
                    placeSum += c - '0';
                    done = false;
                }
            }
            if (!done) {
                placeSum += carry;
                sb.append((char)(placeSum % 10 + '0'));
                carry = placeSum/10;
            }
        }
        if (carry != 0) {
            while (carry > 0) {
                sb.append((char)(carry % 10 + '0'));
                carry = carry/10;
            }
        }

        // remove leading zeros
        while (sb.length() > 1 && sb.charAt(sb.length()-1) == '0') {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.reverse().toString();
    }

    static public void main(String[] args) {
        System.out.println(new Solution().multiply("14000", "2"));
        System.out.println(new Solution().multiply("345", "1678"));
        System.out.println(new Solution().multiply("1678", "345"));
        System.out.println(new Solution().multiply("1", "345"));
        System.out.println(new Solution().multiply("14000", "0"));
        System.out.println(new Solution().multiply("999", "999"));

        System.out.println(new Solution().multiply("498203498203948209384", "200002348293740923420384092834082034988203948209834"));
    }
}
