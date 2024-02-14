package leetcode.longestZigzag;

import leetcode.TreeNode;

/**
 * https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/
 * Medium
 * 
You are given the root of a binary tree.

A ZigZag path for a binary tree is defined as follow:

    Choose any node in the binary tree and a direction (right or left).
    If the current direction is right, move to the right child of the current node; otherwise, move to the left child.
    Change the direction from right to left or from left to right.
    Repeat the second and third steps until you can't move in the tree.

Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).

Return the longest ZigZag path contained in that tree.
 */
public class Solution {

    public int longestZigZag(TreeNode root) {
        // depth first recursion - if we pass in the current zz length from the
        // and direction we came from - can be -1 when starting a new zz
        // return the max of the recursive calls to each child and the current zz length
        // (direction is -1 for left and +1 for right or 0 for the root node of the tree)
        return zzLen(root, -1, 0);
    }

    int zzLen(TreeNode node, int curLen, int direction) {
        if (node == null) {
            return curLen;
        }
        int left = zzLen(node.left, direction >= 0 ? curLen+1 : 0, -1);
        int right = zzLen(node.right, direction <= 0 ? curLen+1 : 0, 1);

        return Math.max(Math.max(left, right), curLen);
    }


}
