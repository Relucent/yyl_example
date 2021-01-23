package yyl.example.basic.algorithm.structure;

/**
 * 并查集是一种树型的数据结构，用于处理一些不相交集合的合并及查询问题。<br>
 * 并查集的时间主要浪费在查询操作上，原因在于当我们需要查询某个节点的根时，需要从该节点出发，不断的查询父亲元素直到找到根节点。<br>
 * 优化版的并查集有两种优化方式<br>
 * 按秩合并：按秩合并的基本思想是使包含较少结点的树德根指向包含较多结点的树的根，而这个树的大小可以抽象为树的高度，即高度小的树合并到高度大的树，这样资源利用更加合理。<br>
 * 路径压缩：是在FIND操作中，把查找路径上的每个结点都直接指向根结点。路径压缩并不改变结点的秩。<br>
 */
public class DisjointSetUnion {

    private int[] parent;
    private int[] rank;

    public DisjointSetUnion(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    /**
     * 查找元素所在的集合，即根节点。
     * @param x 元素
     * @return 根节点
     */
    private int find(int x) {
        // 路径压缩，每一个元素直接指向根节点
        return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }

    /**
     * 将两个元素所在的集合合并为一个集合。 合并之前，应先判断两个元素是否属于同一集合，这可用“查找”操作实现。
     * @param x 合并的元素
     * @param y 合并的元素
     */
    public void union(int x, int y) {
        // 合并之前先查找
        int xRoot = find(x);
        int yRoot = find(y);
        // 属于一个集合，不做操作
        if (yRoot == xRoot) {
            return;
        }
        // 元素rank少的，合并到元素多的
        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
        else if (rank[yRoot] < rank[xRoot])
            parent[yRoot] = xRoot;
        else {
            parent[yRoot] = xRoot;
            rank[xRoot] += 1;
        }
    }
}
