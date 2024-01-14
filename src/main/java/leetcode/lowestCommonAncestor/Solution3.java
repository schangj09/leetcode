package leetcode.lowestCommonAncestor;

/*
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/
 * Medium
 * 
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the 
lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 */
public class Solution3 {

    // Definition for a Node.
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    };

    public Node lowestCommonAncestor(Node p, Node q) {
        // one approach
        // can make a stack for going up parents of p, and a another stack for q
        // then pop both stacks until we get a non-matching node

        // is there a way to use less space?
        // we don't need a stack, but really just a set of all parent nodes (including p)
        // then find the first parent of q in the set
        
        // alternative without extra space - recognize that we have 2 overlapping linked lists
        // if their lengths are the same we can get to the matching node by iterating them 
        // together
        // to make the same length, can count length of each and reduce the longer one
        int l1 = height(p);
        int l2 = height(q);
        while (l1 > l2) {
            p = p.parent;
            l1--;
        }
        while (l2 > l1) {
            q = q.parent;
            l2--;
        }
        while (p != q && p != null) {
            p = p.parent;
            q = q.parent;
        }
        return p;
    }
    int height(Node p) {
        int c = 0;
        while (p != null) {
            c++;
            p = p.parent;
        }
        return c;
    }
}
