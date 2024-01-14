package leetcode.deleteNodeBst;

import java.util.Stack;

/*
 * https://leetcode.com/problems/delete-node-in-a-bst/
 * Medium (Hard)
 * 
Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:

    Search for a node to remove.
    If the node is found, delete the node.

 */

public class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        // DFS to find the node. if it has a child, then we can put either child in its place,
        // - to move the right node up, we move the left node to a child of the right tree's leftmost node
        Stack<TreeNode> nodes = new Stack<>();
        Stack<TreeNode> parents = new Stack<>();
        nodes.push(root);
        parents.push(null);
        while (!nodes.isEmpty()) {
            TreeNode n = nodes.pop();
            TreeNode parent = parents.pop();
            // if we find the one to remove, then remove it and return now
            if (n.val == key) {
                if (n.left == null && n.right == null) {
                    root = replaceInParent(n, null, parent, root);
                } else if (n.left != null) {
                    if (n.right == null) {
                        root = replaceInParent(n, n.left, parent, root);
                    } else {
                        // otherwise both are non-null, so we need to move one of them to the other subtree before replacing in parent
                        moveToLeftMost(n.left, n.right);
                        root = replaceInParent(n, n.right, parent, root);
                    }
                } else if (n.right != null) {
                    // we know n.left is null, so just replaceInParent
                    root = replaceInParent(n, n.right, parent, root);
                }
                return root;
            }
            // otherwise search the subtree
            if (n.val < key) {
                if (n.left != null) {
                    nodes.push(n.left);
                    parents.push(n);
                }
            } else {
                if (n.right != null) {
                    nodes.push(n.right);
                    parents.push(n);
                }
            }
        }
        // if we get here, we didn't find the node, so the tree is unchanged
        return root;
    }

    private void moveToLeftMost(TreeNode left, TreeNode right) {
        if (right.left == null) {
            right.left = left;
        } else {
            moveToLeftMost(left, right.left);
        }
    }

    private TreeNode replaceInParent(TreeNode node, TreeNode replacementNode, TreeNode parent, TreeNode root) {
        // if parent is null, then we are replacing the root node
        if (parent == null) {
            return replacementNode;
        }
        // otherwise replace in parent node and the root doesn't change
        if (node == parent.left) {
            parent.left = replacementNode;
        } else {
            parent.right = replacementNode;
        }
        return root;
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
