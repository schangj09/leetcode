/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package leetcode.longestZigzag;

/**
 *
 * @author Jeffrey Schang
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



    static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
