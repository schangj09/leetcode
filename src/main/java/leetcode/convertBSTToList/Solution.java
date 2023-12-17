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

    static boolean LEFT = true;
    static boolean RIGHT = false;

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        // in place transformation of BST to double-linked list

        // the leftmost node is the smallest - need to return the smallest node
        // the next largest is the parent, next is the leftmost node of right subtree or
        // the next parent

        // in order traversal, and swap pointers as we go
        Deque<Node> parents = new LinkedList<Node>();
        parents.offerFirst(root);
        Node smallest = dfs(LEFT, parents, root.left);
        if (smallest == root && root.right == null) {
            return smallest;
        } else if (smallest == null) {
            smallest = root;
        } else {
            Node end = rightmost(smallest);
            if (end.val < root.val) {
                end.right = root;
                root.left = end;
            }
        }
        Node next = dfs(RIGHT, parents, root.right);
        root.right = next;
        if (next != null) {
            next.left = root;
        }
        Node largest = rightmost(root);
        largest.right = smallest;
        smallest.left = largest;
        return smallest;
    }

    Node rightmost(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return rightmost(node.right);
        }
    }

    Node dfs(boolean isLeft, Deque<Node> parents, Node node) {
        // we return the smallest node in the subtree, after making the subtree into a
        // double-linked list
        if (node == null) {
            return null;
        }
        Node parent = parents.peekFirst();
        // leaf node, update the parent
        if (node.left == null && node.right == null) {
            // if left, then set node.right to the parent (parent.left already points to
            // this node)
            if (isLeft) {
                node.right = parent;
            }
            // for right leaf node, set the node.left
            else {
                node.left = parent;
            }
            return node;
        }

        parents.offerFirst(node);
        Node leftSmallest = dfs(LEFT, parents, node.left);
        Node rightSmallest = dfs(RIGHT, parents, node.right);
        parents.pollFirst();

        // for non-null left, the return the smallest
        if (leftSmallest != null) {
            return leftSmallest;
        }
        // for null left, this node is the smallest, so return node after updating the
        // right pointer
        // to the smallest node in the right subtree
        if (rightSmallest != null) {
            node.right = rightSmallest;
            rightSmallest.left = node;
        }
        return node;
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
