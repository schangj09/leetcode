package leetcode.convertBSTToList;

import java.util.*;
/*
 Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.

You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.

We want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list. 
 */

public class Solution {
    /* Definition for a Node. */
    static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };

    // keep track of smallest and largest node in the tree during the dfs traversal
    static Node smallest = null;
    static Node largest = null;

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        // in place transformation of BST to double-linked list

        // the leftmost node is the smallest - need to return the smallest node
        // the next largest is the parent, next is the leftmost node of right subtree or
        // the next parent

        dfs(root);

        // link the smallest and largest together for the cycle
        smallest.left = largest;
        largest.right = smallest;
        return smallest;
    }

    void dfs(Node node) {
        // in order traversal, and link the nodes as we go
        if (node.left != null) {
            dfs(node.left);
        }
        if (largest != null) {
            largest.right = node;
            node.left = largest;
        } else {
            smallest = node;
        }
        largest = node;
        
        if (node.right != null) {
            dfs(node.right);
        }
    }

    public static void main(String[] args) {
        Node node;
        System.out.println("[30,13,null,-28,null,-44,null,null,-35]");
        node = new Node(2, new Node(1), new Node(3));
        test(node);

        System.out.println("[2,1,3]");
        node = new Node(2, new Node(1), new Node(3));
        test(node);

        System.out.println("[2,null,3,null,null,null,6]");
        node = new Node(4, new Node(2, null, new Node(3)), new Node(6));
        test(node);

        System.out.println("[4,2,5,1,3]");
        node = new Node(4, new Node(2, new Node(1), new Node(3)), new Node(5));
        test(node);
    }

    public static void test(Node node) {
        int count = 0;
        Node smallest = new Solution().treeToDoublyList(node);
        for (Node n = smallest; count < 10; count++) {
            System.out.println(String.format("%d, %s, %s", n.val, n.left, n.right));
            n = n.right;
        }
    }

}
