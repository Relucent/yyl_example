package yyl.example.basic.algorithm.structure;

/**
 * Definition for singly-linked list.
 */
public class ListNode {

    public int value;
    public ListNode next;

    public ListNode(int x) {
        value = x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        ListNode l = this;
        for (int i = 0;; i++) {
            sb.append(l.value);
            l = l.next;
            if (l == null) {
                sb.append(']');
                break;
            } else if (i == 31) {
                sb.append(", ...]");
                break;
            }
            sb.append(',').append(' ');
        }
        return sb.toString();
    }

    public static ListNode create(int... values) {
        ListNode dummyHead = new ListNode(0);
        ListNode previous = dummyHead;
        for (int value : values) {
            ListNode temp = new ListNode(value);
            previous.next = temp;
            previous = temp;
        }
        return dummyHead.next == null ? dummyHead : dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode head = create(1, 2, 3, 4, 5);
        System.out.println(head);
    }
}
