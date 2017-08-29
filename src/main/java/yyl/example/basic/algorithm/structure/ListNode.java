package yyl.example.basic.algorithm.structure;

public class ListNode {

	int value;
	ListNode next;

	ListNode(int x) {
		value = x;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		ListNode l = this;
		for (;;) {
			sb.append(l.value);
			l = l.next;
			if (l == null) {
				sb.append(']');
				break;
			}
			sb.append(',').append(' ');
		}
		return sb.toString();
	}

	public static ListNode create(int... values) {
		ListNode dummyHead = new ListNode(0);
		ListNode pre = dummyHead;
		for (int value : values) {
			ListNode temp = new ListNode(value);
			pre.next = temp;
			pre = temp;
		}
		return dummyHead.next == null ? dummyHead : dummyHead.next;
	}

	public static void main(String[] args) {
		ListNode l = create(1, 2, 3, 4, 5);
		System.out.println(l);
	}

}