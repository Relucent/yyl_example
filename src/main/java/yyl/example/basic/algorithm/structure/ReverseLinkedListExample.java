package yyl.example.basic.algorithm.structure;

/**
 * <h3>反转一个单链表</h3><br>
 */
public class ReverseLinkedListExample {

    public static void main(String[] args) {
        ListNode list = ListNode.create(1, 2, 3, 4, 5);
        System.out.println(list);
        list = reverse(list);
        System.out.println(list);
    }

    // 算法思路：先对原链表做头删操作，再对新链表做头插
    // 时间复杂度：O(N)
    // 空间复杂度：O(1)
    public static ListNode reverse(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            // 1. 对之前的链表做头删
            ListNode node = head;
            head = node.next;

            // 2. 对新链表做头插
            node.next = newHead;
            newHead = node;
        }
        return newHead;
    }
}
