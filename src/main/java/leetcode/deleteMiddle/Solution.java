package leetcode.deleteMiddle;

 /*
  * https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/
  * Medium
You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.

The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing, where ⌊x⌋ denotes 
the largest integer less than or equal to x.

    For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.


  */
class Solution {
    // need to iterate to the end, so what is the middle. keep an index of the current node.
    // so, the desired node is at index/2 once we get to the last node. We can get floor(index/2)
    // by walking a second pointer once for every 2 movements of the first pointer.
    public ListNode deleteMiddle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fast = head;
        ListNode prev = null; // previous to slow
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // prev is null if there is only one or 2 elements in the list
        if (prev == null) {
            // if there is only one element, we return null, if there are 2 elements we delete the first one and return the second as the new list
            return head.next;
        }
        // otherwise we have prev and prev.next == slow
        prev.next = slow.next;
        return head;
    }
    /**
     * Definition for singly-linked list.
     */
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}