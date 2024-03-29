package leetcode.leafSimilar;
import java.util.*;

import leetcode.TreeNode;

/*
 * https://leetcode.com/problems/leaf-similar-trees/
 * Easy
 * 
Consider all the leaves of a binary tree, from left to right order, the values of those leaves form a leaf value sequence.

For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).

Two binary trees are considered leaf-similar if their leaf value sequence is the same.

Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.

 */
class Solution {
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        return leafNodes(root1).equals(leafNodes(root2));
    }

    List<Integer> leafNodes(TreeNode root) {
        List<Integer> s = new ArrayList<>();
        leafNodes(root, s);
        return s;
    }
    void leafNodes(TreeNode root, List<Integer> s) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            s.add(root.val);
        } else {
            leafNodes(root.left, s);
            leafNodes(root.right, s);
        }
    }

}
