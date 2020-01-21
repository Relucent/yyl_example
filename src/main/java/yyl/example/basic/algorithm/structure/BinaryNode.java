package yyl.example.basic.algorithm.structure;

import java.util.function.Consumer;

/**
 * Definition for a binary tree node.
 */
public class BinaryNode {

    public int val;
    public BinaryNode left;
    public BinaryNode right;

    BinaryNode(int x) {
        val = x;
    }

    /**
     * 创建二叉树
     * @param values 二叉树节点数据
     * @return 二叉树跟节点
     */
    public static BinaryNode create(int... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        BinaryNode[] nodes = new BinaryNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new BinaryNode(values[i]);
        }
        // 第一个数为根节点
        BinaryNode root = nodes[0];
        // 建立二叉树
        for (int i = 0; i < nodes.length / 2; i++) {
            // 左子节点
            nodes[i].left = nodes[i * 2 + 1];
            // 右子节点，偶数的时候可能为null
            if (i * 2 + 2 < nodes.length) {
                nodes[i].right = nodes[i * 2 + 2];
            }
        }
        return root;
    }


    /**
     * 前序遍历，根左右(DLR)
     * @param root 根节点
     * @param action 回调动作
     */
    public static void preorder(BinaryNode root, Consumer<BinaryNode> action) {
        if (root != null) {
            action.accept(root);
            preorder(root.left, action);
            preorder(root.right, action);
        }
    }

    /**
     * 中序遍历，左根右(LDR)
     * @param root 根节点
     * @param action 回调动作
     */
    public static void inorder(BinaryNode root, Consumer<BinaryNode> action) {
        if (root != null) {
            preorder(root.left, action);
            action.accept(root);
            preorder(root.right, action);
        }
    }

    /**
     * 后序遍历，左右根(LRD)
     * @param root 根节点
     * @param action 回调动作
     */
    public static void postorder(BinaryNode root, Consumer<BinaryNode> action) {
        if (root != null) {
            preorder(root.left, action);
            preorder(root.right, action);
            action.accept(root);
        }
    }
}
