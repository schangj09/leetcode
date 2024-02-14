package leetcode.flattenBinaryTree;

import leetcode.TreeNode;

/*
 * https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
 * Medium
 * 
 Given the root of a binary tree, flatten the tree into a "linked list":

    The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list 
    and the left child pointer is always null.
    The "linked list" should be in the same order as a pre-order traversal of the binary tree.

 */
public class Solution {
    /*
     * Given the root of a binary tree, flatten the tree into a "linked list":
     * 
     * The "linked list" should use the same TreeNode class where the right child
     * pointer points to the next node in the list and the left child pointer is
     * always null.
     * The "linked list" should be in the same order as a pre-order traversal of the
     * binary tree.
     * 
     * 
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        // we need a pre-order traversal and make a linked list
        //
        // algorithm is to flatten the left tree, and the right tree, then join them
        // together
        // recursive function returns the rightmost node of the flattened tree so we
        // can simplify the join
        flattenToList(root);
    }

    // return the rightmost node of the sub-tree after flattening
    TreeNode flattenToList(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode rightMostLeft = flattenToList(node.left);
        TreeNode rightMostRight = flattenToList(node.right);

        // set this node's right and left
        // if rightMostLeft is null, it means node.left is null, so we know that node.right doesn't change
        if (rightMostLeft != null) {
            rightMostLeft.right = node.right;
            node.right = node.left;
        }
        node.left = null;

        if (rightMostRight != null) {
            return rightMostRight;
        } else if (rightMostLeft != null) {
            return rightMostLeft;
        } else {
            return node;
        }
    }
}
