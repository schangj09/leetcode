package leetcode.copyListWithRandom;

public class Solution {
    // Definition for a Node.
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

    }
    public String toString(Node p) {
        return "[" + p.val + "," + p.random != null ? Integer.toString(p.random.val) : null + "]";
    }

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        // make a holding copy of the list. each node.random is copied from
        // the original list.

        // make a result copy of the list. each node.random is copied (points back
        // to original list nodes), but we also point original node.random to the
        // in order node of copy list.

        // fix pass 1 - we update the random pointers of copy list to node.random.random
        // (the first pointer is to the original list and that node points back to its
        // copy node in the
        // copy list)

        // then fix pass 2 to restore the original list random pointers back from the
        // holding copy

        // holding copy
        Node holdingHead = new Node(head.val);
        Node p = holdingHead;
        p.random = head.random;
        Node next = head.next;
        while (next != null) {
            p.next = new Node(next.val);
            p.next.random = next.random;
            p = p.next;
            next = next.next;
        }

        // now make the result copy of the list and swap random pointers in the original list
        Node copyHead = new Node(head.val);
        p = copyHead;
        p.random = head.random;
        head.random = p; // updating original list to point to copy
        next = head.next;
        while (next != null) {
            p.next = new Node(next.val);
            p = p.next;
            p.random = next.random;
            next.random = p; // updating original list to point to copy
            next = next.next;
        }

        // update the random pointers in the copyHead list to point to copied nodes
        copyHead.random = (copyHead.random != null ? copyHead.random.random : null);
        next = copyHead.next;
        while (next != null) {
            next.random = (next.random != null ? next.random.random : null);
            next = next.next;
        }

        // restore the original random pointers
        p = holdingHead;
        next = head;
        while (next != null) {
            next.random = p.random;
            p = p.next;
            next = next.next;
        }

        return copyHead;
    }
}
