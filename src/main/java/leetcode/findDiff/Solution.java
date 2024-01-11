package leetcode.findDiff;
import java.util.*;

class Solution {

  public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
    Set<Integer> set1 = new HashSet<>();
    Set<Integer> set2 = new HashSet<>();

    List<Integer> a1 = new ArrayList<>();
    List<Integer> a2 = new ArrayList<>();
    for (int i = 0; i < nums1.length; i++) {
      set1.add(nums1[i]);
    }
    for (int i = 0; i < nums2.length; i++) {
      set2.add(nums2[i]);
    }
    for (int n : set1) {
      if (!set2.contains(n)) {
        a1.add(n);
      }
    }
    for (int n : set2) {
      if (!set1.contains(n)) {
        a2.add(n);
      }
    }

    return Arrays.asList(a1, a2);
  }
}
