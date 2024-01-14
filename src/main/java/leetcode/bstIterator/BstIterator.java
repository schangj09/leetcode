package leetcode.bstIterator;

import java.util.*;

/*
https://leetcode.com/problems/binary-search-tree-iterator/description/
Medium

Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):

    BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part of the 
    constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
    
    boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns 
    false.
    
    int next() Moves the pointer to the right, then returns the number at the pointer.

Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return the smallest 
element in the BST.

You may assume that next() calls will always be valid. That is, there will be at least a next number in the in-order 
traversal when next() is called. 

 */
public class BstIterator {
    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
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

    /*
test case:
  3
1   4
 2

last = 4
st = [1 3]
-> 1
st = [2 1 3]
-> 2
st = [1 3] --- bug
     */

    // To do inorder traversal, we represent the current position as a stack of tree nodes.
    // To know if we're at the end, we can store the greatest node of the tree (rightmost node of the tree);
    TreeNode last; // greatest value node
    boolean gotLast = false;
    Stack<TreeNode> st; // top of stack points to the current node of the iteration
    public BstIterator(TreeNode root) {
        last = root;
        while (last.right != null) {
            last = last.right;
        }

        st = new Stack<>();
        TreeNode first = root;
        st.push(first);
        while (first.left != null) {
            first = first.left;
            st.push(first);
        }
    }

    public int next() {
        TreeNode cur = st.peek();
        int val = cur.val;
        // the next greater node is the left most node of the right tree, or the parent node if the right tree is null
        if (cur.right == null) {
            if (cur != last) {
                st.pop();
                // Note: fixed bug where I forgot to pop up the stack
                while (st.peek().val < val) {
                    st.pop();
                }
            } else {
                gotLast = true;
            }
        } else {
            // go to the leftmost node of the right subtree
            cur = cur.right;
            st.push(cur);
            while (cur.left != null) {
                cur = cur.left;
                st.push(cur);
            }
        }
        return val;
    }

    public boolean hasNext() {
        return !gotLast;
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
