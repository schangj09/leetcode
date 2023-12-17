package leetcode.rightSideView;

import java.util.*;

class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> view = new ArrayList<>();
        if (root == null) {
            return view;
        }
        // breadth first search, where we need to keep track of the end of the level and
        // insert the
        // last element of each level
        // with LinkedList we can use null to mark the end of the level
        Deque<TreeNode> nodes = new LinkedList<>();
        nodes.offer(root);
        nodes.offer(null);
        TreeNode p;
        // stack of first node at each depth
        while (!nodes.isEmpty()) {
            // at each iteration we are at the first item in the level (rightmost)
            p = nodes.poll();
            view.add(p.val);
            // iterate through all the items on this level, adding the next level in
            // right-to-left order
            while (p != null) {
                if (p.right != null) {
                    nodes.offer(p.right);
                }
                if (p.left != null) {
                    nodes.offer(p.left);
                }
                p = nodes.poll();
            }
            // reached the end of the level, so we add a null marker
            if (!nodes.isEmpty()) {
                nodes.offer(null);
            }
        }
        return view;
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
