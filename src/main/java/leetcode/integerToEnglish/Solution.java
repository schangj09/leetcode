package leetcode.integerToEnglish;

import java.util.*;

// convert a non-negative integer to its english words
public class Solution {
    Map<Integer, String> dict10 = new HashMap<>();
    Map<Integer, String> dict90 = new HashMap<>();
    Map<Integer, String> dictPow = new HashMap<>();

    Solution() {
        int i;
        i=0;
        dict10.put(i++, "zero");
        dict10.put(i++, "one");
        dict10.put(i++, "two");
        dict10.put(i++, "three");
        dict10.put(i++, "four");
        dict10.put(i++, "five");
        dict10.put(i++, "six");
        dict10.put(i++, "seven");
        dict10.put(i++, "eight");
        dict10.put(i++, "nine");
        dict10.put(i++, "ten");
        dict10.put(i++, "eleven");
        dict10.put(i++, "twelve");
        dict10.put(i++, "thirteen");
        dict10.put(i++, "fourteen");
        dict10.put(i++, "fifteen");
        dict10.put(i++, "sixteen");
        dict10.put(i++, "seventeen");
        dict10.put(i++, "eighteen");
        dict10.put(i++, "nineteen");

        i=2;
        dict90.put(i++, "twenty");
        dict90.put(i++, "thirty");
        dict90.put(i++, "forty");
        dict90.put(i++, "fifty");
        dict90.put(i++, "sixty");
        dict90.put(i++, "seventy");
        dict90.put(i++, "eighty");
        dict90.put(i++, "ninety");

        dictPow.put(2, "hundred");
        dictPow.put(3, "thousand");
        dictPow.put(6, "million");
        dictPow.put(9, "billion");
    }

    public String numberToWords(int num) {
        StringBuffer sb = new StringBuffer();
        numberToWords(num, sb);
        return sb.toString();
    }
    public void numberToWords(int num, StringBuffer sb) {
        // sepcial case 0
        if (num == 0) {
            sb.append(dict10.get(num));
            return;
        }

        // reverse through the powers 10^9 = billion to 10^2 = hundred
        int rem = num;
        for (int i = 9; i >= 2 && rem != 0; i = (i > 3 ? i - 3 : i - 1)) {
            int p = rem / pow10(i);
            if (p > 0) {
                numberToWords(p, sb);
                sb.append(" ").append(dictPow.get(i));
                rem = rem % pow10(i);
                // add a space unless there is nothing remaining
                if (rem != 0) {
                    sb.append(" ");
                }
            }
        }
        // now for the value less than 100: rem < 100
        if (rem != 0) {
            if (rem < 20) {
                sb.append(dict10.get(rem));
            } else {
                int p = rem/10;
                sb.append(dict90.get(p));
                rem = rem%10;
                if (rem != 0) {
                    sb.append(" ").append(dict10.get(rem));
                }
            }
        }
    }

    int pow10(int i) {
        int v = 10;
        while (i > 1) {
            v *= 10;
            i--;
        }
        return v;
    }

    public static void main(String[] args) {

        System.out.println(new Solution().numberToWords(10));
        System.out.println(new Solution().numberToWords(11));
        System.out.println(new Solution().numberToWords(19));
        System.out.println(new Solution().numberToWords(20));
        System.out.println(new Solution().numberToWords(59));
        System.out.println(new Solution().numberToWords(601));
        System.out.println(new Solution().numberToWords(7999));
        System.out.println(new Solution().numberToWords(2111799912));
    }
}
