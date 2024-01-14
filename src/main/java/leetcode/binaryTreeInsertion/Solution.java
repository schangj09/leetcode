package leetcode.binaryTreeInsertion;

/*
 * Hackerrank problem
 * https://www.hackerrank.com/challenges/binary-search-tree-insertion/problem
 * 

 You are given a pointer to the root of a binary search tree and values to be inserted into the tree. Insert the values into 
 their appropriate position in the binary search tree and return the root of the updated binary tree. You just have to 
 complete the function.


 */
public class Solution {

    class Node {
        int data;
        Node left;
        Node right;
        Node(int data) {
            this.data = data;
        }
    }

/*
   12
  5  7
1
  4
insert 4

  12
    13
insert 13

 */


    Node insert(Node root, int data) {
        if (root != null) {
            Node insert = find(root, data);
            Node n = new Node(data);
            if (data > insert.data) {
                n.right = insert.right;
                insert.right = n;
            } else {
                n.left = insert.left;
                insert.left = n;
            }
        } else {
            return new Node(data);
        }
        return root;
    }

    Node find(Node node, int v) {
        if (node.data == v) {
            return node;
        } else if (v < node.data) {
            if (node.left == null) {
                return node;
            } else {
                return find(node.left, v);
            }
        } else {
            if (node.right == null) {
                return node;
            } else {
                return find(node.right, v);
            }
        }
    }
}
