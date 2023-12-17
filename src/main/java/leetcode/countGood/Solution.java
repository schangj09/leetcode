package leetcode.countGood;

import java.util.*;

public class Solution {
    public int goodNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // DFS stack implementation - 2 stacks, push the node and the max value
        Stack<TreeNode> nodes = new Stack<>();
        Stack<Integer> maxVals = new Stack<>();
        nodes.push(root);
        maxVals.push(root.val);
        int count = 0;
        while (!nodes.isEmpty()) {
            TreeNode p = nodes.pop();
            int max = maxVals.pop();
            // node is good if val is >= maxVal
            if (p.val >= max) {
                count++;
            }
            max = Math.max(max, p.val);
            if (p.left != null) {
                nodes.push(p.left);
                maxVals.push(max);
            }
            if (p.right != null) {
                nodes.push(p.right);
                maxVals.push(max);
            }
        }
        return count;
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
