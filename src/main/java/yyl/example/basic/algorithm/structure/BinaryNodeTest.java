package yyl.example.basic.algorithm.structure;

/**
 * <h3>测试二叉树</h3><br>
 * 
 * <pre>
 * ...................................<br>
 * ................{0}................<br>
 * .........┌───────┴───────┐.........<br>
 * ........{1}.............{2}........<br>
 * .....┌───┴───┐.......┌───┴───┐.....<br>
 * ....{3}.....{4}.....{5}.....{6}....<br>
 * ...┌─┴─┐...┌─┘.....................<br>
 * ..{7}.{8}.{9}......................<br>
 * ...................................<br>
 * </pre>
 */
public class BinaryNodeTest {

    public static void main(String[] args) {
        BinaryNode root = BinaryNode.create(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        BinaryNode.preorderTraversal(root, n -> System.out.print(n.val));
        System.out.println();

        BinaryNode.inorderTraversal(root, n -> System.out.print(n.val));
        System.out.println();

        BinaryNode.postorderTraversal(root, n -> System.out.print(n.val));
        System.out.println();
    }
}
