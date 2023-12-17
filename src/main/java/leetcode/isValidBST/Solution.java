package leetcode.isValidBST;

class Solution {
    /**
     * Definition for a binary tree node.
     **/
    public static class TreeNode {
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

    private TreeNode prev = null;

    public boolean isValidBST(TreeNode root) {
        // recurse throught the left tree, the prev member variable will contain the rightmost node of the left
        // subtree, so we then can compare it to the root.val
        if (root == null) {
            return true;
        }
        if (!isValidBST(root.left)) {
            return false;
        }
        if (prev != null && root.val <= prev.val) {
            return false;
        }
        prev = root;
        return isValidBST(root.right);
    }

    public static void main(String[] arg) {
        TreeNode t = new TreeNode(5, new TreeNode(6), null);
        System.out.println(new Solution().isValidBST(t));
    }
}