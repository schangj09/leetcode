package leetcode.stickersToSpellWord;

/*
We are given n different types of stickers. Each sticker has a lowercase English word on it.

You would like to spell out the given string target by cutting individual letters from your collection of stickers and rearranging them. You can use each sticker more than once if you want, and you have infinite quantities of each sticker.

Return the minimum number of stickers that you need to spell out target. If the task is impossible, return -1.

Note: In all test cases, all words were chosen randomly from the 1000 most common US English words, and target was chosen as a concatenation of two random words.

 */
public class Solution {
    int minCount = Integer.MAX_VALUE;
    public int minStickers(String[] stickers, String target) {
        // the idea is to use backtracking to find each possible solution and 
        // track the solution with the min number of stickers
        //
        // for each iteration, we find the possible stickers that will get us closer to finishing,
        // for each one of those, we try them out one at a time and backtrack after each one

        // represent words as frequency count
        backtrack(newFreqCounts(stickers), new FreqCount(target), 0);
        return minCount == Integer.MAX_VALUE ? -1 : minCount;
    }
    void backtrack(FreqCount[] stickers, FreqCount target, int count) {
        if (target.isEmpty()) {
            minCount = Math.min(minCount, count);
            return;
        }
        // get possible stickers that match
        boolean[] m = new boolean[stickers.length];
        for (int j = 0; j < stickers.length; j++) {
            m[j] = target.match(stickers[j]);
        }

        // for each sticker with matching characters in the remaining target, do a backtrack
        for (int i = 0; i < m.length; i++) {
            if (m[i]) {
                FreqCount subtracted = target.subtract(stickers[i]);
                backtrack(stickers, target, count + 1);
                target.add(subtracted);
            }
        }
    }

    FreqCount[] newFreqCounts(String[] s) {
        FreqCount[] a = new FreqCount[s.length];
        for (int i = 0; i < s.length; i++) {
            a[i] = new FreqCount(s[i]);
        }
        return a;
    }
    
    static class FreqCount {
        int[] freq = new int[26];
        FreqCount() {
        }
        FreqCount(String s) {
            for (int i = 0; i < s.length(); i++) {
                freq[s.charAt(i) - 'a'] += 1;
            }
        }
        boolean isEmpty() {
            for (int i = 0; i < 26; i++) {
                if (freq[i] != 0) {
                    return false;
                }
            }
            return true;
        }

        // subtract other frequency counts from this and return the subtracted counts
        FreqCount subtract(FreqCount other) {
            FreqCount f = new FreqCount();
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0 && other.freq[i] > 0) {
                    int count = Math.min(freq[i], other.freq[i]);
                    freq[i] -= count;
                    f.freq[i] = count;
                }
            }
            return f;
        }
        // add other frequency counts to this
        void add(FreqCount other) {
            for (int i = 0; i < 26; i++) {
                if (other.freq[i] > 0) {
                    freq[i] += other.freq[i];
                }
            }
        }
        // check if any of the other frequency counts overlap to this
        boolean match(FreqCount other) {
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0 && other.freq[i] > 0) {
                    return true;
                }
            }
            return false;
        }
    }
}