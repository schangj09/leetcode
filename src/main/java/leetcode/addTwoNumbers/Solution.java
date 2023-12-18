package leetcode.addTwoNumbers;

/**
 * Definition for singly-linked list.
 * }
 */
class Solution {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // addition algorithm:
        // add digits x,y: v = x+y, if v > 10, then put the v%10 and carry over v/10

        ListNode result = null;
        ListNode end = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int v = 0;
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
    }
}