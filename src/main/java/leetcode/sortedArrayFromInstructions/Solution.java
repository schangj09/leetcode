package leetcode.sortedArrayFromInstructions;

/*
https://leetcode.com/problems/create-sorted-array-through-instructions/
Hard

Given an integer array instructions, you are asked to create a sorted array from the elements in instructions. You start 
with an empty container nums. For each element from left to right in instructions, insert it into nums. The cost of each 
insertion is the minimum of the following:

    The number of elements currently in nums that are strictly less than instructions[i].
    The number of elements currently in nums that are strictly greater than instructions[i].

For example, if inserting element 3 into nums = [1,2,3,5], the cost of insertion is min(2, 1) (elements 1 and 2 are less 
than 3, element 5 is greater than 3) and nums will become [1,2,3,3,5].

Return the total cost to insert all elements from instructions into nums. Since the answer may be large, return it modulo 
109 + 7


 */
public class Solution {
    public int createSortedArray(int[] instructions) {
        // segment tree - relies on the input value space is 1 <= instructions[i] <= 1e5
        // - so we can make a segment tree of every possible value (size of segment tree
        // is exactly 2m where m is the original array - in this case 2e5)
        //
        // recall in the segment tree the left and right children of node i is 2i and 2i+1
        // so the parent of node i is i/2 (or i << 1)
        //
        // segment tree stores the sum of the range of numbers, so if we have frequency counts 
        // in the leaf values, then we can update and query the intervals in logM
        int m = (int) 1e5 + 1;
        int[] tree = new int[m * 2];

        long MOD = (int) 1e9 + 7;
        long cost = 0;

        for (int x : instructions) {
            cost += Math.min(query(0, x, tree, m), query(x + 1, m, tree, m));
            update(x, 1, tree, m);
        }
        return (int) (cost % MOD);
    }

    // implement Segment Tree
    private void update(int index, int value, int[] tree, int m) {
        index += m;
        tree[index] += value;
        for (index /= 2; index > 0; index /= 2)
            tree[index] = tree[index * 2] + tree[(index * 2) + 1];
    }

    private int query(int left, int right, int[] tree, int m) {
        int result = 0;
        for (left += m, right += m; left < right; left /= 2, right /= 2) {
            if ((left & 1) == 1)
                result += tree[left++];
            if ((right & 1) == 1)
                result += tree[--right];
        }
        return result;
    }
}
