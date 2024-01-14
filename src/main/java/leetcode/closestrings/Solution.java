package leetcode.closestrings;
import java.util.*;

/*
 * https://leetcode.com/problems/determine-if-two-strings-are-close/description/
 * Medium
 * 
Two strings are considered close if you can attain one from the other using the following operations:

    Operation 1: Swap any two existing characters.
        For example, abcde -> aecdb
    Operation 2: Transform every occurrence of one existing character into another existing character, and do the same with the other character.
        For example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)

You can use the operations on either string as many times as necessary.

Given two strings, word1 and word2, return true if word1 and word2 are close, and false otherwise.


 */
class Solution {

  /**
   * This method checks if two words have the same number of characters.
   *
   * @param word1 The first word to compare.
   * @param word2 The second word to compare.
   * @return true if the words have the same number of characters and the same frequency counts for each character,
   *         false if the words have different lengths or different sets of characters.
   */
  public boolean closeStrings(String word1, String word2) {
    // if you don't have the same number of characters then, no
  
    // if the counts of each unique character are the same, then you can always
    // match them with operation 1
    //
    // for each word, get the count of each symbol, e.g. symbol i has a count c_i
    // for each unique count value, there must be the same number of symbols in each word
    if (word1.length() != word2.length()) {
      return false;
    }
    // get frequency counts of the symbols in the words
    Map<Character, Integer> c1 = new HashMap<>();
    Map<Character, Integer> c2 = new HashMap<>();
    for (int i = 0; i < word1.length(); i++) {
      char c = word1.charAt(i);
      int count = c1.getOrDefault(c, 0);
      c1.put(c, count + 1);
    }
    for (int i = 0; i < word2.length(); i++) {
      char c = word2.charAt(i);
      int count = c2.getOrDefault(c, 0);
      c2.put(c, count + 1);
    }
    // if the sets of symbols don't match, then we're done
    if (!c1.keySet().equals(c2.keySet())) {
      return false;
    }

    // finally we need the number of symbols for each unique count value
    // this is just the reverse maps of the 2 hashmaps
    // the words are close iff these maps are equal
    Map<Integer, Integer> r1 = new HashMap<>();
    Map<Integer, Integer> r2 = new HashMap<>();
    for (int n : c1.values()) {
      int count = r1.getOrDefault(n, 0);
      r1.put(n, count + 1);
    }
    for (int n : c2.values()) {
      int count = r2.getOrDefault(n, 0);
      r2.put(n, count + 1);
    }
    return r2.equals(r1);
  }
}
