package leetcode.copyListWithRandom;
/*
 * https://leetcode.com/problems/copy-list-with-random-pointer/description/
 * Medium
 * 
A linked list of length n is given such that each node contains an additional random pointer, which could point to any node 
in the list, or null.

Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its 
value set to the value of its corresponding original node. Both the next and random pointer of the new nodes should point to 
new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None 
of the pointers in the new list should point to nodes in the original list.

For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes 
x and y in the copied list, x.random --> y.

Return the head of the copied linked list.

The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, 
random_index] where:

    val: an integer representing Node.val
    random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it does not 
    point to any node.

Your code will only be given the head of the original linked list.
 */
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

        // From editorial ---- the approach with O(1) is basically the same, but instead of a holding copy,
        // we insert each new node in between the 2 old nodes, then set the random pointers after creating all
        // new nodes. Finally unzip the 2 lists and return the new list.

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
