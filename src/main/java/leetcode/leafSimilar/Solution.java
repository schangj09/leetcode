package leetcode.leafSimilar;
import java.util.*;

class Solution {
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        return leafNodes(root1).equals(leafNodes(root2));
    }

    List<Integer> leafNodes(TreeNode root) {
        List<Integer> s = new ArrayList<>();
        leafNodes(root, s);
        return s;
    }
    void leafNodes(TreeNode root, List<Integer> s) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            s.add(root.val);
        } else {
            leafNodes(root.left, s);
            leafNodes(root.right, s);
        }
    }



 static class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode() {}
     TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
    }
}
