package leetcode.oddEven;

public class Solution {
    public ListNode oddEvenList(ListNode head) {
        // odd first, followed by even nodes
        // if list is size 0, 1 or 2, we can just return as is
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        // keep a pointer to the head of the odd list and the head of the even list
        ListNode odd = head;
        ListNode even = head.next;
        
        // keep a pointer to the end of the odd list and to the end of the even list
        ListNode oddend = odd;
        ListNode evenend = even;
        // once odd or even has no next pointer we are done - note: evenend will be null for odd size lists
        // but for even size lists, evenend.next will be null
        while (oddend.next != null && (evenend != null && evenend.next != null)) {
            oddend.next = evenend.next;
            oddend = oddend.next;
            // after moving oddend forward, then update evenend.next and evenend
            evenend.next = oddend.next;
            evenend = oddend.next;
        }
        // oddend is guaranteed to be non-null so we connect the lists
        oddend.next = even;
        return odd;
    }
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
   
}
