package leetcode.addTwoNumbers;

import leetcode.ListNode;

/**
Add 2 numbers.
Numbers given as a list of digits already in reverse order

 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // addition algorithm:
        // add digits x,y: v = x+y, if v > 10, then put the v%10 and carry over v/10

        ListNode result = null;
        ListNode end = null;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int v = carry;
            if (l1 != null) {
                v += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                v += l2.val;
                l2 = l2.next;
            }

            carry = v/10;
            ListNode d = new ListNode(v%10);
            if (end == null) {
                result = d;
                end = d;
            } else {
                end.next = d;
                end = d;
            }
        }
        return result;
    }
}