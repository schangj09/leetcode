package leetcode.permutations;

import java.util.ArrayList;
import java.util.List;

/*
https://leetcode.com/problems/permutations/
Medium

Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
 */
public class Solution {
    // Use recursion to get the permutations by cascading.
    //
    // Also, see {@class leetcode.getSubstrings.Solution} for String permutations.
    //
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
        return permute(list);
    }
    
    List<List<Integer>> permute(List<Integer> list) {
        List<List<Integer>> res = new ArrayList<>();
        if (list.isEmpty()) {
            res.add(new ArrayList<>());
        } else {
            // choose one of nums, remove it from the list, get permutations for the rest of the list
            int n = list.size();
            for (int i = 0; i < n; i++) {
                int v = list.remove(i);
                List<List<Integer>> perms = permute(list);
                list.add(i, v);

                // put the permutations into result
                for (List<Integer> l : perms) {
                    l.add(0, v);
                    res.add(l);
                }
            }
        }
        return res;
    }
}
