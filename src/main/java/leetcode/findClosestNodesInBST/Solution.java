package leetcode.findClosestNodesInBST;

/*
https://leetcode.com/problems/closest-nodes-queries-in-a-binary-search-tree/
Medium

You are given the root of a binary search tree and an array queries of size n consisting of positive integers.

Find a 2D array answer of size n where answer[i] = [mini, maxi]:

    mini is the largest value in the tree that is smaller than or equal to queries[i]. If a such value does not exist, 
    add -1 instead.
    
    maxi is the smallest value in the tree that is greater than or equal to queries[i]. If a such value does not 
    exist, add -1 instead.

Return the array answer.

Constraints:

    The number of nodes in the tree is in the range [2, 105].
    1 <= Node.val <= 106
    n == queries.length
    1 <= n <= 105
    1 <= queries[i] <= 106

*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // tests:
    // q = [1,2,3,9,11] t = [5, null, 10] -> [[-1,5][-1,5][-1,5][5,10][10,-1]]
    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        // idea is to sort the queries, then do an in-order traversal of the tree
        // to build the answer list
        int n = queries.size();
        int[][] sortlist = new int[n][2];
        for (int j =  0; j < n; j++) {
            sortlist[j] = new int[] {queries.get(j), j};
        }
        Arrays.sort(sortlist, (a, b) -> a[0] - b[0]);

        int[] q = new int[n];
        for (int j =  0; j < n; j++) {
            q[j] = sortlist[j][0];
        }
       

        // maintain the previous value and when we get a next value greater than the next 
        // next query val, then we have a pair
        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        int prev = -1; // min tree val is 1
        int i = 0;
        List<List<Integer>> result = new ArrayList<>();
        while (!s.isEmpty() && i < n) {
            TreeNode node = s.peek();
            if (node.left != null && prev < node.left.val) {
                s.push(node.left);
                continue;
            }
            node = s.pop();
            while (i < n && q[i] > prev && q[i] <= node.val) {
                int mini = (q[i] == node.val) ? node.val : prev;
                result.add(List.of(mini, node.val));
                i++;
            }
            prev = node.val;
            if (node.right != null) {
                s.push(node.right);
            }
        }
        while (i < n) {
            int maxi = (q[i] == prev) ? prev : -1;
            result.add(List.of(prev, maxi));
            i++;
        }

        List<List<Integer>> sortedresult = new ArrayList<>(result);
        for (int j = 0; j < n; j++) {
            sortedresult.add(sortlist[j][1], result.get(j));
        }
        return sortedresult;
    }
}
