package leetcode.insertSortedCircular;


/*
Given a Circular Linked List node, which is sorted in non-descending order, write a function to insert a 
value insertVal into the list such that it remains a sorted circular list. The given node can be a 
reference to any single node in the list and may not necessarily be the smallest value in the circular list.

If there are multiple suitable places for insertion, you may choose any place to insert the new value. After the insertion, 
the circular list should remain sorted.

If the list is empty (i.e., the given node is null), you should create a new single circular list and return the reference 
to that single node. Otherwise, you should return the originally given node.


*/

class Solution {
// Definition for a Node.
class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _next) {
        val = _val;
        next = _next;
    }
};

    public Node insert(Node head, int insertVal) {
        if (head == null) {
            head = new Node(insertVal);
            head.next = head;
            return head;
        }
        Node prev = findInsert(head, insertVal);
        Node n = new Node(insertVal);
        n.next = prev.next;
        prev.next = n;
        return head;
    }
    // find a node where the next node value is >= val and prev node is less than or equal (or reach 
    // end list)
    // Need to handle case where all nodes are less than val, in which case, we 
    // should add val after the greatest node.
    // Also, must detect a cycle in case all nodes have the same value and that value is less than target val.
    Node findInsert(Node n, int val) {
        // remember start pointer to detect the cycle
        Node end = n;
        do {
            if (n.val == val || (n.val < val && n.next.val >= val)) {
                break;
            }
            n = n.next;
        } while (n != end);
        return n;
    }

}
