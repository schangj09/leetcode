package leetcode.serDeserTree;

/*
https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
Hard

Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a 
file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another 
computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/
deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this 
string can be deserialized to the original tree structure.

Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to 
follow this format, so please be creative and come up with different approaches yourself.
 */
public class Codec {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     */

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

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
     * test cases
     * 
     * single node: 5
     * right only: 5(,10)
     * left only: 5(2,)
     * negative cases: -5(-3,-200)
     * subtree cases: 5((3,),(200(,300)))
     * 
     * fail: 1(2,3(4,5))
     */

    // Strategy:
    // use a codec like this: val(<left>,<right>) where left and right are recursive
    // and can be empty, parens can be omitted for leaf node

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuffer sb = new StringBuffer();
        serialize(root, sb);
        return sb.toString();
    }

    public void serialize(TreeNode node, StringBuffer sb) {
        if (node == null) {
            return;
        }
        sb.append(node.val);
        if (node.left != null || node.right != null) {
            sb.append("(");
            serialize(node.left, sb);
            sb.append(",");
            serialize(node.right, sb);
            sb.append(")");
        }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        TreeNode root = new Deser(0).exec(data);
        // throw exception if Deser.i is not == data.length()
        return root;
    }

    static class Deser {
        int i;

        Deser(int i) {
            this.i = i;
        }

        public TreeNode exec(String data) {
            int n = data.length();
            if (i == n) {
                return null;
            }
            char next = data.charAt(i);
            // empty left or empty right node
            if (next == ',' || next == ')') {
                return null;
            }
            // will throw an error if not an integer
            int end = i+1;
            while (end < n && (data.charAt(end) == '-' || Character.isDigit(data.charAt(end)))) {
                end++;
            }
            // throws if unexpected not a number
            int val = Integer.parseInt(data, i, end, 10);

            // advance the index and deserialize the subtrees
            i = end;
            TreeNode p = new TreeNode(val);
            if (i < n && data.charAt(i) == '(') {
                i++;
                p.left = exec(data);
                i++;
                p.right = exec(data);
                i++;
            }
            return p;
        }
    }

    public static void main (String[] args) {
        String s;
        TreeNode node;

        node = new TreeNode(1, new TreeNode(2), new TreeNode(3, new TreeNode(4), new TreeNode(5)));
        s = new Codec().serialize(node);
        System.out.println(s);

        node = new Codec().deserialize(s);
        System.out.println(node);

        node = new TreeNode(1, null, new TreeNode(2));
        s = new Codec().serialize(node);
        System.out.println(s);

        node = new Codec().deserialize(s);
        System.out.println(node);
    }

    // Your Codec object will be instantiated and called as such:
    // Codec ser = new Codec();
    // Codec deser = new Codec();
    // TreeNode ans = deser.deserialize(ser.serialize(root));
}
