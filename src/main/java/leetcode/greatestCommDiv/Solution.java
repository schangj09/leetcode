/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package leetcode.greatestCommDiv;

/**
 *
 * @author Jeffrey Schang
 */
public class Solution {

    public String gcdOfStrings(String str1, String str2) {
        // find a common prefix that is a divisor of both strings
        // if they are both the same lenght, then there can only be a
        // divisor if they are equal.
        // a shorter substring can only be a divisor if the length is a
        // divisor of both string length - so we can skip anything else
        // then for each candidate, check if it divides both
        int m = str1.length();
        int n = str2.length();
        int v = Math.min(m, n);

        String shorter = str2.length() == v ? str2 : str1;
        String longer = str2.length() == v ? str1 : str2;

        for (int i = v; i > 0; i--) {
            String s1 = shorter.substring(0,i);
            if (isDivisor(s1, longer)) {
                return s1;
            }
        }
        return "";
    }

    boolean isDivisor(String d, String target) {
        int m = d.length();
        int n = target.length();
        if (n%m == 0) {
            for (int i = 0; i < n/m; i++) {
                if (!d.equals(target.substring(i*m, (i*m)+m))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
