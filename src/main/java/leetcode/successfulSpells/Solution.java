package leetcode.successfulSpells;

import java.util.*;

/*
https://leetcode.com/problems/successful-pairs-of-spells-and-potions/
Medium

You are given two positive integer arrays spells and potions, of length n and m respectively, where spells[i] represents the 
strength of the ith spell and potions[j] represents the strength of the jth potion.

You are also given an integer success. A spell and potion pair is considered successful if the product of their strengths is 
at least success.

Return an integer array pairs of length n where pairs[i] is the number of potions that will form a successful pair with the 
ith spell.

 */
public class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int n = spells.length;
        int m = potions.length;
        // true if product of strength is >= success

        // algorithm to solve, we first sort potions and for each spell i, do a binary search for the 
        // index j of the max potion that is not successful (might be -1) - the count is the number of potions 
        // greater than that (m-1-j)

        // running time sort: mLogm, search n logm -> O((m+n)(logm))
        Arrays.sort(potions);
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = m - 1 - findPotion(potions, spells[i], success);
        }
        return res;
    }

    // returns -1 if potions[0] is successful
    int findPotion(int[] potions, long spellStrength, long success) {
        int m = potions.length;
        int i = 0;
        int j = m-1;
        while (i < j && potions[i]*spellStrength < success) {
            int mid = (j-i)+1;
            if (potions[mid]*spellStrength < success) {
                i = mid+1;
            } else {
                j = mid-1;
            }
        }

        if (potions[i]*spellStrength == success) {
            return i-1;
        } else {
            return i;
        }
    }
}
