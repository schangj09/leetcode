package leetcode.findKClosestInBST;

import java.util.*;

/*
https://leetcode.com/problems/closest-binary-search-tree-value-ii/description/
Hard

Given the root of a binary search tree, a target value, and an integer k, return the k values in the BST that 
are closest to the target. You may return the answer in any order.

You are guaranteed to have only one unique set of k values in the BST that are closest to the target.

 */
public class Solution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        // find closest node to start, then look at predecessor and successor until we add k nodes
        TreeNode closest = root;
        double minDist = Math.abs(target - root.val);
        TreeNode node = root;
        while (node != null) {
            double dist = Math.abs(target - node.val);
            if (dist < minDist) {
                closest = node;
                minDist = dist;
            }
            if ((double) node.val == target) {
                break;
            }
            if (target < node.val) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        Iter iterLeft = new Iter(root, closest.val);
        Iter iterRight = new Iter(root, closest.val);

        // add start, and then previous or next based on which is closer to target
        List<Integer> res = new ArrayList<>();
        res.add(closest.val);

        TreeNode left = iterLeft.getPrevious();
        TreeNode right = iterRight.getNext();
        while (res.size() < k) {
            if (left != null && right != null) {
                if (Math.abs(target - left.val) < Math.abs(target - right.val)) {
                    res.add(left.val);
                    left = iterLeft.getPrevious();
                } else {
                    res.add(right.val);
                    right = iterRight.getNext();
                }
            } else if (left != null) {
                res.add(left.val);
                left = iterLeft.getPrevious();
            } else {
                res.add(right.val);
                right = iterRight.getNext();
            }
        }
        return res;
    }

    static class Iter {
        Stack<TreeNode> parents = new Stack<>();
        TreeNode left;
        TreeNode right;
        TreeNode node;
        public Iter(TreeNode root, int start) {
            left = root;
            while (left.left != null) {
                left = left.left;
            }
            right = root;
            while (right.right != null) {
                right = right.right;
            }

            node = root;
            do {
                parents.push(node);
                if (node.val < start) {
                    node = node.right;
                } else if (node.val > start) {
                    node = node.left;
                }
            } while (node != null && node.val != start);
        }

        TreeNode getPrevious() {
            // null if we are at the beginning
            if (node == null || node == left) {
                return null;
            }

            // go to the rightmost node of left subtree
            if (node.left != null) {
                parents.push(node);
                node = node.left;
                while (node.right != null) {
                    parents.push(node);
                    node = node.right;
                }
                return node;
            }

            // no left subtree, so pop the tree up until we find one less than current
            int curVal = node.val;
            while (node != left && node.val >= curVal && !parents.isEmpty()) {
                node = parents.pop();
            }
            return node;
        }

        TreeNode getNext() {
            // null if we are at the end
            if (node == null || node == right) {
                return null;
            }

            // go to the leftmost node of right subtree
            if (node.right != null) {
                parents.push(node);
                node = node.right;
                while (node.left != null) {
                    parents.push(node);
                    node = node.left;
                }
                return node;
            }

            // no right subtree, so pop the tree up until we find one greater than current
            int curVal = node.val;
            while (node != right && node.val <= curVal && !parents.isEmpty()) {
                node = parents.pop();
            }
            return node;
        }
    }
}
