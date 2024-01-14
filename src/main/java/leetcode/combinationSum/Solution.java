package leetcode.combinationSum;
import java.util.*;
/*
 * https://leetcode.com/problems/combination-sum-iii/description
 * Medium
 * 
Find all valid combinations of k numbers that sum up to n such that the following conditions are true:

    Only numbers 1 through 9 are used.
    Each number is used at most once.

Return a list of all possible valid combinations. The list must not contain the same combination twice, and the combinations 
may be returned in any order.

 */
/**
 * NOTE: some inefficiency could be avoided - I am computing all the
 * combinations, but if we process the numbers in increasing order, we could
 * skip combinations that we already tried
 */
public class Solution {
    Set<Integer> remaining = new HashSet<>();
    Set<Set<Integer>> found = new HashSet<>();
    Solution() {
        for (int i = 0; i < 9; i++) {
            remaining.add(i+1);
        }
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        // we use backtracking by exploring each of 9 numbers while
        // tracking the Set of numbers used so far
        List<Integer> track = new ArrayList<>();
        backtrack(track, k, n);

        //build the result
        List<List<Integer>> res = new ArrayList<>();
        for (Set<Integer> r : found) {
            res.add(new ArrayList<>(r));
        }
        return res;
    }

    void backtrack(List<Integer> accumulated, int k, int n) {
        if (accumulated.size() == k) {
            if (sum(accumulated) == n) {
                Set<Integer> s = new HashSet<>(accumulated);
                found.add(s);
            }
            return;
        }

        List<Integer> tryList = new ArrayList<>(remaining);
        int curSum = sum(accumulated);
        for (int j : tryList) {
            // only keep checking the branch if it is not already too big
            if (curSum + j <= n) {
                remaining.remove(j);
                accumulated.add(j);
                backtrack(accumulated, k, n);
                accumulated.remove(accumulated.size()-1);
                remaining.add(j);
            }
        }
    }
    int sum(List<Integer> s) {
        int sum = 0;
        for (int i : s) {
            sum += i;
        }
        return sum;
    }
}
