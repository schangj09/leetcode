package leetcode.findClosestInBST;

import leetcode.TreeNode;

/*
https://leetcode.com/problems/closest-binary-search-tree-value/description/
Easy

Given the root of a binary search tree and a target value, return the value in the BST that is closest to the target. If 
there are multiple answers, print the smallest.

For more see:
Medium https://leetcode.com/problems/closest-nodes-queries-in-a-binary-search-tree/
Hard https://leetcode.com/problems/closest-binary-search-tree-value-ii/


Given the root of a binary search tree and a target value, return the value in the BST that is closest to the target. If 
there are multiple answers, print the smallest.

*/
public class Solution {


    public int closestValue(TreeNode root, double target) {
        return find(root, target, Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    // Note: I used a simpler algorithm for this in the problem findKClosestInBST -
    // just need to pass through the closest value seen and see if the next node is closer
    int find(TreeNode node, double target, int minAncestor, int maxAncestor) {
        if (node == null) {
            if (minAncestor == Integer.MAX_VALUE) {
                return maxAncestor;
            }
            if (maxAncestor == Integer.MIN_VALUE) {
                return minAncestor;
            }
            return (Math.abs(minAncestor - target) < Math.abs(maxAncestor - target))
                    ? minAncestor
                    : maxAncestor;
        }
        if ((double) node.val == target) {
            return node.val;
        }
        if (node.val > target) {
            minAncestor = Math.min(node.val, minAncestor);
            return find(node.left, target, minAncestor, maxAncestor);
        } else {
            maxAncestor = Math.max(node.val, maxAncestor);
            return find(node.right, target, minAncestor, maxAncestor);
        }
    }
}