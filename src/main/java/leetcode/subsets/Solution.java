package leetcode.subsets;

import java.util.*;
/*
 Given an integer array nums of unique elements, return all possible
subsets (the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.
 */
public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        // elements are unique
        // all subsets will be subsests of each length up to n
        // to generate subsets of size k, use each subset of size k-1
        // there must be (n-k) sets for each subset of size k-1 - yields O(n^n) sets
        // k=1 -> n
        // k=2 -> (n-1)n
        // k=3 -> (n-2)(n-1)n
        // ... O(n^n)

        // cascading solution
        // get all solutions with i=0 (empty set), then for i+1
        // copy current solutions (solutions of i) with addition of nums[i+1]
        // repeat to i = n-1

        // Knuth's algorithm of lexicographic sort
        // recognize that bitmask represent set - if i == 1 then nums[i] in set
        // then just count from 0 to 2^n-1 (leftpadded with 0s) 

        return new ArrayList<>();
    }
}