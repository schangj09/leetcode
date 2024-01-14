package leetcode.factorCombinations;

import java.util.*;

/*
https://leetcode.com/problems/factor-combinations/
Medium

Numbers can be regarded as the product of their factors.

    For example, 8 = 2 x 2 x 2 = 2 x 4.

Given an integer n, return all possible combinations of its factors. You may return the answer in any order.

Note that the factors should be in the range [2, n - 1].

 */

public class Solution {
    public List<List<Integer>> getFactors(int n) {
        // intuition: the solutions can form a tree if we consider each solution as 
        // node in the tree, which is one of the sorted list of factors. There is a directed edge 
        // from a to b iff list b is one longer than a and the last 2 elements of list b are
        // factors of last element of a - in other words, to get b from a, we need to find 2 factors
        // of the last element of b which are greater than or equal to the second to last element of a

        // 2 options: one is backtracking solution (pass modified list to recursion)
        // the other is DFS traversal (push copy of list on to a stack)

        // given list with last 2 elements (sorted) p, q. we search numbers from p to the sqrt(q)
        // each one that is a factor represents a descendent node of the tree

        return new ArrayList<>();
    }
}
