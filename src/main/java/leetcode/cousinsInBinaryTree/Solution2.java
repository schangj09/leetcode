package leetcode.cousinsInBinaryTree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import leetcode.TreeNode;

/*
https://leetcode.com/problems/cousins-in-binary-tree-ii/
Medium

Given the root of a binary tree, replace the value of each node in the tree with the sum of all its cousins' values.

Two nodes of a binary tree are cousins if they have the same depth with different parents.

Return the root of the modified tree.

Note that the depth of a node is the number of edges in the path from the root node to it.


 */
public class Solution2 {
// Doing a level by level traversal of the tree - we have all the nodes of a given level.
// We can track the total sum of the level and we need to subtract the sum of sibling nodes (1 or 2 nodes).
// The trick is to replace each node value with the sibling sum, so we can easily subtract during 
// the processing of a level.
    public TreeNode replaceValueInTree(TreeNode root) {
        Deque<TreeNode> q = new LinkedList<>();
        q.offerFirst(root);
        int levelSum = root.val;
        while (!q.isEmpty()) {
            int n = q.size();
            // make a first pass of the level nodes and replace each value with the cousin level
            // sum (iterate without polling)
            //  - each node value has been already replaced with its sibling sum, so we can just subtract its value
            Iterator<TreeNode> iter = q.iterator();
            for (int i = 0; i < n; i++) {
                TreeNode p = iter.next();
                p.val = levelSum - p.val;
            }
            // now poll each node of this level and simultaneously calculate the sum of the next levelSum 
            // and reset nodes with their sibling sum
            levelSum = 0;
            for (int i = 0; i < n; i++) {
                TreeNode p = q.pollFirst();
                int sibSum = p.left != null ? p.left.val : 0;
                sibSum += p.right != null ? p.right.val : 0;
                levelSum += sibSum;
                if (p.left != null) {
                    p.left.val = sibSum;
                    q.offerLast(p.left);
                }
                if (p.right != null) {
                    p.right.val = sibSum;
                    q.offerLast(p.right);
                }
            }
        }
        return root;
    }
}
