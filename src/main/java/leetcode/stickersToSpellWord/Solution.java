package leetcode.stickersToSpellWord;

import java.util.*;

/*
We are given n different types of stickers. Each sticker has a lowercase English word on it.

You would like to spell out the given string target by cutting individual letters from your collection of stickers and rearranging them. You can use each sticker more than once if you want, and you have infinite quantities of each sticker.

Return the minimum number of stickers that you need to spell out target. If the task is impossible, return -1.

Note: In all test cases, all words were chosen randomly from the 1000 most common US English words, and target was chosen as a concatenation of two random words.

 */
public class Solution {
    Map<Word, Integer> memo = new HashMap<>();
    int hit = 0;
    int call = 0;

    public int minStickers(String[] stickers, String target) {
        // the idea is to use backtracking to find each possible solution and
        // track the solution with the min number of stickers
        //
        // for each iteration, we find the possible stickers that will get us closer to
        // finishing,
        // for each one of those, we try them out one at a time and backtrack after each
        // one

        // add memoization - map of target Word to minStickers

        // represent words as frequency count
        int minCount = backtrack(newFreqCounts(stickers), new Word(target), 0);
        System.out.println(String.format("cache hit: %d, miss: %d", hit, call - hit));
        return minCount == Integer.MAX_VALUE ? -1 : minCount;
    }

    int backtrack(Word[] stickers, Word target, int count) {
        if (target.isEmpty()) {
            return count;
        }
        call++;
        if (memo.containsKey(target)) {
            hit++;
            return memo.get(target);
        }
         // get possible stickers that match
        boolean[] m = new boolean[stickers.length];
        for (int j = 0; j < stickers.length; j++) {
            m[j] = target.match(stickers[j]);
        }

        // for each sticker with matching characters in the remaining target, do a
        // backtrack
        int minCount = Integer.MAX_VALUE;
        for (int i = 0; i < m.length; i++) {
            if (m[i]) {
                Word subtarget = target.minus(stickers[i]);
                int subcount = backtrack(stickers, subtarget, count + 1);
                minCount = Math.min(minCount, subcount);
            }
        }
        memo.put(target, minCount);
        return minCount;
    }

    Word[] newFreqCounts(String[] s) {
        Word[] a = new Word[s.length];
        for (int i = 0; i < s.length; i++) {
            a[i] = new Word(s[i]);
        }
        return a;
    }

    static class Word {
        final String s;
        int[] freq = new int[26];

        Word(String s) {
            for (int i = 0; i < s.length(); i++) {
                freq[s.charAt(i) - 'a'] += 1;
            }
            this.s = toNormalString(freq);
        }

        boolean isEmpty() {
            return s.isEmpty();
        }

        // subtract other frequency counts from this and return the resulting Word
        Word minus(Word other) {
            int[] f = new int[26];
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0 && other.freq[i] > 0) {
                    int count = Math.min(freq[i], other.freq[i]);
                    f[i] = freq[i] - count;
                } else {
                    f[i] = freq[i];
                }
            }
            return new Word(toNormalString(f));
        }

        static String toNormalString(int[] f) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < f[i]; j++) {
                    sb.appendCodePoint('a' + i);
                }
            }
            return sb.toString();
        }

        // check if any of the other frequency counts overlap to this
        boolean match(Word other) {
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0 && other.freq[i] > 0) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return s;
        }

        @Override
        public int hashCode() {
            return s.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            return s.equals(((Word)other).s);
        }
    }

    public static void main(String[] args) {
        String[][] s = new String[][] {
                { "with", "example", "science" },
                { "notice", "possible" },
                { "write","their","read","quiet","against","down","process","check"}

        };
        String[] t = new String[] {
                "thethat",
                "basicbasic",
                "togetherhand"
        };

        for (int i = 0; i < s.length; i++) {
            System.out.println(new Solution().minStickers(s[i], t[i]));
        }
    }
}