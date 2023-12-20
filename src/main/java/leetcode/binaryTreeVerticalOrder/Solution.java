package leetcode.binaryTreeVerticalOrder;

import java.io.*;
import java.util.*;

/*
Given the root of a binary tree, return the vertical order traversal of its nodes' values. (i.e., from top to bottom, column by column).

If two nodes are in the same row and column, the order should be from left to right.

 */

class Solution {
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

    List<List<Integer>> leftOfRoot = new ArrayList<>();
    List<List<Integer>> rightAndRoot = new ArrayList<>();

    static class NodeCol {
        TreeNode n;
        int col;

        NodeCol(TreeNode n, int col) {
            this.n = n;
            this.col = col;
        }
    }

    public List<List<Integer>> verticalOrder(TreeNode root) {
        // col is 0 based from the root
        // column decrement to the left and increment to the right

        // BFS, pre-order traversal
        Deque<NodeCol> q = new LinkedList<>();
        q.offerLast(new NodeCol(root, 0));
        while (!q.isEmpty()) {
            NodeCol nc = q.pollFirst();
            addNode(nc);

            if (nc.n.left != null) {
                NodeCol left = new NodeCol(nc.n.left, nc.col - 1);
                q.offerLast(left);
            }
            if (nc.n.right != null) {
                NodeCol right = new NodeCol(nc.n.right, nc.col + 1);
                q.offerLast(right);
            }
        }

        // concatenate results
        Collections.reverse(leftOfRoot);
        leftOfRoot.addAll(rightAndRoot);
        return leftOfRoot;
    }

    void addNode(NodeCol nc) {
        if (nc.col < 0) {
            int i = -nc.col - 1;
            while (leftOfRoot.size() < i + 1) {
                leftOfRoot.add(new ArrayList<>());
            }
            leftOfRoot.get(i).add(nc.n.val);
        } else {
            int i = nc.col;
            while (rightAndRoot.size() < i + 1) {
                rightAndRoot.add(new ArrayList<>());
            }
            rightAndRoot.get(i).add(nc.n.val);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("Hello, World!");
        strings.add("Welcome to CoderPad.");
        strings.add("This pad is running Java " + Runtime.version().feature());

        for (String string : strings) {
            System.out.println(string);
        }
    }
}
