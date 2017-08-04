package yyl.example.basic.algorithm.exercises;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 题目:<br>
 * 反转一个栈<br>
 */
public class StackReverse {

	public static void main(String[] args) {
		Stack<String> stack = new Stack<>();
		stack.push("1");
		stack.push("2");
		stack.push("3");
		stack.push("4");
		stack.push("5");

		System.out.println(stack);

		reverse(stack);

		System.out.println(stack);
	}

	private static void reverse(Stack<String> stack) {
		Queue<String> queue = new LinkedList<>();
		while (stack.size() > 0) {
			queue.offer(stack.pop());
		}
		while (queue.size() > 0) {
			stack.push(queue.poll());
		}
	}
}
