package leetcode.sortedArrayFromInstructions;

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
