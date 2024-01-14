package leetcode.reverseVowels;

/*
https://leetcode.com/problems/reverse-vowels-of-a-string/editorial/?envType=study-plan-v2&envId=leetcode-75
Easy

Given a string s, reverse only all the vowels in the string and return it.

The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both lower and upper cases, more than once.

 
 */
/**
 * NOTES: use "char[] c = s.toCharArray" and "return new String(c)"
 * Its faster to work with char array rather than StringBuffer.
 * @author Jeffrey Schang
 */
public class Solution {
    public String reverseVowels(String s) {
        if (s.isEmpty()) {
            return s;
        }
        // normal reverse is swap characters from start and end until
        // we get to the middle.
        // For this we can use 2 pointers and advance them both on each
        // iteration to the next vowel
        int n = s.length();
        int i = 0;
        int j = n-1;
        StringBuffer sb = new StringBuffer(s);
        while (i < j) {
            while (i != j && !isVowel(sb.charAt(i))) {
                // leave the non-vowel character in place in sb
                i++;
            }
            while (i != j && !isVowel(sb.charAt(j))) {
                // leave the non-vowel character in place in sb
                j--;
            }

            // swap the vowels
            if (i != j) {
                char tmp = sb.charAt(j);
                sb.setCharAt(j, sb.charAt(i));
                sb.setCharAt(i, tmp);
                i++;
                j--;
            }
        }
        return sb.toString();
    }

    boolean isVowel(char a) {
        return Character.isAlphabetic(a) &&
            switch (Character.toLowerCase(a)) {
                case 'a', 'e', 'i', 'o', 'u'  -> true;
                default -> false;
            };
    }

}
