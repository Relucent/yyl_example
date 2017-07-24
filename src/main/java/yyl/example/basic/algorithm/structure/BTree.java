package yyl.example.basic.algorithm.structure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * B-tree，Balanced Tree，平衡树 <br>
 * 特征：(m阶)<br>
 * 1. 树中每个结点最多含有m个孩子<br>
 * 2. 若根结点不是叶子结点，则至少两个孩子节点(特殊情况：没有孩子的根结点，即根结点为叶子结点，整棵树只有一个根节点)<br>
 * 3. 每个中间节点都包含 (k-1)个元素 和(k)个子节点，其中 (m/2) <= (k) <= (m) <br>
 * 4. 所有叶子节点都位于同一层 (具有相同的深度，等于树高h)<br>
 * 5. 每个节点中的元素从小到大排列，节点当中(k-1)个元素正好是 (k)个孩子包含的元素的值域分化 <br>
 */
// 每个结点所包含的关键的个数有上下界。用一个被称为最小度数的固定整数t(t≥2)来表示这些界
// 除了根结点以外的每个结点必须至少有t−1个关键字。因此，除了根结点外的每个内部结点至少有t个孩子
// 每个结点至多包含2t−1个关键字。因此，一个内部结点至多可能有2t个孩子。当一个结点恰好有2t−1个关键字时，称该结点为满的(full)
// t=ceil(M/2)  |  M=t*2   | {ceil向上取整}
// t=2时的B树是最简单的(此时树的每个内部结点只可能有2个、3个或4个孩子)，我们称它为2-3-4树
public class BTree<K, V> {

	/** 根节点 */
	private Node<K, V> root;
	/** (m = t * 2)， 每个非根节点的关键字数n满足 (t - 1) <= n <= (2t - 1) */
	private int t = 2; // m=t*2 
	/** 非根节点中最小的键值数 */
	private int minKeySize = t - 1;
	/** 非根节点中最大的键值数 */
	private int maxKeySize = 2 * t - 1;
	/** 键的比较函数对象 */
	private Comparator<K> keyComparator;

	/**
	 * 构造一颗B树，键值采用采用自然排序方式
	 */
	public BTree() {
		this(2, null);
	}

	/**
	 * 构造一颗B树，键值采用采用自然排序方式
	 * @param t 最小度数
	 * @param keyComparator 排序的比较函数
	 */
	public BTree(int t, Comparator<K> keyComparator) {
		this.t = t;
		this.minKeySize = t - 1;
		this.maxKeySize = 2 * t - 1;
		this.keyComparator = keyComparator;
		this.root = new Node<>();
		this.root.setLeaf(true);
		this.keyComparator = keyComparator;
	}

	/**
	 * 插入给定的键值对
	 * @param key 键
	 * @param value 值
	 * @param true 如果B树中不存在给定的键，否则false
	 */
	public boolean insert(K key, V value) {
		// 如果根节点满了，则B树长高  
		if (isFullNode(root)) {
			increaseHeight();
		}
		return insertNotFull(root, new Entry<K, V>(key, value));
	}

	/**
	 * 从B树中删除一个与给定键关联的项
	 * @param key 给定的键
	 * @return 返回给定键关联的值，如果不存在则返回null
	 */
	public V delete(K key) {
		Entry<K, V> entry = delete(root, key);
		return entry == null ? null : entry.value;
	}

	/**
	 * 如果B树中存在给定的键，则更新值，否则插入。
	 * @param key 键
	 * @param value 值
	 * @return 如果B树中存在给定的键，则返回之前的值，否则null
	 */
	public V put(K key, V value) {
		// 如果根节点满了 (则增高树)
		if (isFullNode(root)) {
			increaseHeight();
		}
		return putNotFull(root, new Entry<K, V>(key, value));
	}

	/**
	 * 搜索给定的键
	 * @param key 给定的键值
	 * @return 键关联的值，如果不存在则返回null
	 */
	public V search(K key) {
		return search(root, key);
	}

	/**
	 * 从给定的子树中，递归搜索给定的键
	 * @param node 子树的根节点
	 * @param key 给定的键值
	 * @return 键关联的值，如果不存在则返回null
	 */
	private V search(Node<K, V> node, K key) {
		SearchResult<V> result = node.search(key);
		if (result.exist) {
			return result.getValue();
		}
		if (node.leaf) {
			return null;
		}
		return search(node.children.get(result.index), key);
	}

	/** 增高树 */
	//# 对满的非根结点的分裂不会使B树的高度增加，导致B树高度增加的唯一方式是对根结点的分裂
	private void increaseHeight() {
		Node<K, V> newRoot = new Node<K, V>(keyComparator);
		newRoot.setLeaf(false);
		newRoot.addChild(root);
		splitNode(newRoot, 0);
		root = newRoot;
	}

	/**
	 * 分裂父节点下的一个满子节点
	 * @param parentNode 父节点(非满的内部结点)
	 * @param index 满子节点在父节点中的索引
	 */
	private void splitNode(Node<K, V> parentNode, int index) {

		Node<K, V> childNode = parentNode.childAt(index);

		//保证给定的子节点是满节点
		assertState(childNode.keySize() == maxKeySize);

		//将子节点一半数量的关键字元素分裂到新的其相邻右结点中，中间关键字元素上移到父结点中

		//新的邻右节点
		Node<K, V> siblingNode = new Node<K, V>(keyComparator);
		siblingNode.setLeaf(childNode.isLeaf());

		// 将满子节点中索引为[t, 2t - 1]的(t - 1)个项插入新的节点中  
		for (int i = t; i < maxKeySize; ++i) {
			siblingNode.addEntry(childNode.entryAt(i));
		}

		// 提取满子节点中的中间项，其索引为(t - 1)  
		Entry<K, V> entry = childNode.entryAt(t - 1);

		// 删除满子节点中索引为[t - 1, 2t - 2]的t个项  
		for (int i = maxKeySize - 1; i >= t - 1; --i) {
			childNode.removeEntry(i);
		}

		// 如果满子节点不是叶节点，则还需要处理其子节点  
		if (!childNode.isLeaf()) {
			// 将满子节点中索引为[t, 2t - 1]的t个子节点插入新的节点中  
			for (int i = 0; i < minKeySize + 1; ++i) {
				siblingNode.addChild(childNode.childAt(t + i));
			}
			// 删除满子节点中索引为[t, 2t - 1]的t个子节点  
			for (int i = maxKeySize; i >= t; --i) {
				childNode.removeChild(i);
			}
		}
		// 将entry插入父节点  
		parentNode.addEntry(index, entry);
		// 将新节点插入父节点  
		parentNode.addChild(index + 1, siblingNode);
	}

	/**
	 * 在一个非满节点中插入给定的项
	 * @param node 非满节点
	 * @param entry 给定的项
	 * @return true，如果B树中不存在给定的项，否则false
	 */
	private boolean insertNotFull(Node<K, V> node, Entry<K, V> entry) {

		//节点非满
		assertState(!isFullNode(node));

		//1.0 先根据关键字找到要插入的位置
		//2.0 判断结点是否是满的
		//3.1 若非满，那就直接插入；
		//3.2 否则将该结点一分为二，分裂为两个结点，而中间的关键字插入到其父结点中。

		//找到 键-值 定节点应该插入的位置
		SearchResult<V> result = node.search(entry.getKey());

		//该 键-值 已经存在，不允许更改(直接返回)
		if (result.isExist()) {
			return false;
		}

		// 如果是叶子节点直接插入
		if (node.isLeaf()) {
			//插入到指定位置
			node.addEntry(result.index, entry);
			return true;
		}

		//否则应该插入到子节点中
		Node<K, V> childNode = node.childAt(result.getIndex());

		//如果子节点是满节点  
		if (isFullNode(childNode)) {

			// 则先分裂  (分页后的子节点不再是满节点)
			splitNode(node, result.getIndex());

			//如果给定的键大于分裂之后新生成项的键，则需要插入该新项的右边(索引+1)
			if (compare(entry.getKey(), node.entryAt(result.getIndex()).getKey()) > 0) {
				childNode = node.childAt(result.getIndex() + 1);
			}
		}
		return insertNotFull(childNode, entry);
	}

	/**
	 * 如果存在给定的键，则更新键关联的值， 否则插入给定的项
	 * @param node 非满节点
	 * @param entry 给定的项
	 * @return 返回旧值(如果没有，则返回null)
	 */
	private V putNotFull(Node<K, V> node, Entry<K, V> entry) {

		//节点非满
		assertState(!isFullNode(node));

		//1.0 先根据关键字找到要插入的位置
		//2.0 判断结点是否是满的
		//3.1 若非满，那就直接插入；
		//3.2 否则将该结点一分为二，分裂为两个结点，而中间的关键字插入到其父结点中。

		// 如果是叶子节点直接插入(备注：这个叶子节点是非满的，所以可以直接插入)
		if (node.isLeaf()) {
			return node.putEntry(entry);
		}

		//找到给定项在给定节点应该插入的位置(如果是应该插入 该位置对应的子树中)
		SearchResult<V> result = node.search(entry.getKey());

		// 如果存在，则更新  
		if (result.isExist()) {
			return node.putEntry(entry);
		}

		//否则应该插入到子节点中
		Node<K, V> childNode = node.childAt(result.getIndex());

		//如果子节点是满节点  
		if (isFullNode(childNode)) {

			// 则先分裂  (分页后的子节点不再是满节点)
			splitNode(node, result.getIndex());

			//如果给定的键大于分裂之后新生成项的键，则需要插入该新项的右边(索引+1)
			if (compare(entry.getKey(), node.entryAt(result.getIndex()).getKey()) > 0) {
				childNode = node.childAt(result.getIndex() + 1);
			}
		}
		return putNotFull(childNode, entry);
	}

	/**
	 * 从子树中删除与给定键关联的项
	 * @param node 子树根节点
	 * @param key 给定的键
	 * @return 被删除的项，如果不存在给定键关联的项，则返回null
	 */
	private Entry<K, V> delete(Node<K, V> node, K key) {

		// 该过程需要保证，对非根节点执行删除操作时，其关键字个数至少为t。  
		assertState(node.keySize() >= t || node == root);

		SearchResult<V> result = node.search(key);
		/*
		 * 因为这是查找成功的情况，0 <= result.getIndex() <= (node.size() - 1)， 因此(result.getIndex() + 1)不会溢出。
		 */
		if (result.isExist()) {
			// 1.如果关键字在节点node中，并且是叶节点，则直接删除。  
			if (node.isLeaf())
				return node.removeEntry(result.getIndex());
			else {
				// 2.a 如果节点node中前于key的子节点包含至少t个项  
				Node<K, V> leftChildNode = node.childAt(result.getIndex());
				if (leftChildNode.keySize() >= t) {
					// 使用leftChildNode中的最后一个项代替node中需要删除的项  
					node.removeEntry(result.getIndex());
					node.addEntry(result.getIndex(), leftChildNode.entryAt(leftChildNode.keySize() - 1));
					// 递归删除左子节点中的最后一个项  
					return delete(leftChildNode, leftChildNode.entryAt(leftChildNode.keySize() - 1).getKey());
				} else {
					// 2.b 如果节点node中后于key的子节点包含至少t个关键字  
					Node<K, V> rightChildNode = node.childAt(result.getIndex() + 1);
					if (rightChildNode.keySize() >= t) {
						// 使用rightChildNode中的第一个项代替node中需要删除的项  
						node.removeEntry(result.getIndex());
						node.addEntry(result.getIndex(), rightChildNode.entryAt(0));
						// 递归删除右子节点中的第一个项  
						return delete(rightChildNode, rightChildNode.entryAt(0).getKey());
					} else // 2.c 前于key和后于key的子节点都只包含t-1个项  
					{
						Entry<K, V> deletedEntry = node.removeEntry(result.getIndex());
						node.removeChild(result.getIndex() + 1);
						// 将node中与key关联的项和rightChildNode中的项合并进leftChildNode  
						leftChildNode.addEntry(deletedEntry);
						for (int i = 0; i < rightChildNode.keySize(); ++i)
							leftChildNode.addEntry(rightChildNode.entryAt(i));
						// 将rightChildNode中的子节点合并进leftChildNode，如果有的话  
						if (!rightChildNode.isLeaf()) {
							for (int i = 0; i <= rightChildNode.keySize(); ++i)
								leftChildNode.addChild(rightChildNode.childAt(i));
						}
						return delete(leftChildNode, key);
					}
				}
			}
		} else {
			/*
			 * 因为这是查找失败的情况，0 <= result.getIndex() <= node.size()， 因此(result.getIndex() + 1)会溢出。
			 */
			if (node.isLeaf()) // 如果关键字不在节点node中，并且是叶节点，则什么都不做，因为该关键字不在该B树中  
			{
				return null;
			}
			Node<K, V> childNode = node.childAt(result.getIndex());
			if (childNode.keySize() >= t) // // 如果子节点有不少于t个项，则递归删除  
				return delete(childNode, key);
			else // 3  
			{
				// 先查找右边的兄弟节点  
				Node<K, V> siblingNode = null;
				int siblingIndex = -1;
				if (result.getIndex() < node.keySize()) // 存在右兄弟节点  
				{
					if (node.childAt(result.getIndex() + 1).keySize() >= t) {
						siblingNode = node.childAt(result.getIndex() + 1);
						siblingIndex = result.getIndex() + 1;
					}
				}
				// 如果右边的兄弟节点不符合条件，则试试左边的兄弟节点  
				if (siblingNode == null) {
					if (result.getIndex() > 0) // 存在左兄弟节点  
					{
						if (node.childAt(result.getIndex() - 1).keySize() >= t) {
							siblingNode = node.childAt(result.getIndex() - 1);
							siblingIndex = result.getIndex() - 1;
						}
					}
				}
				// 3.a 有一个相邻兄弟节点至少包含t个项  
				if (siblingNode != null) {
					if (siblingIndex < result.getIndex()) // 左兄弟节点满足条件  
					{
						childNode.addEntry(0, node.entryAt(siblingIndex));
						node.removeEntry(siblingIndex);
						node.addEntry(siblingIndex, siblingNode.entryAt(siblingNode.keySize() - 1));
						siblingNode.removeEntry(siblingNode.keySize() - 1);
						// 将左兄弟节点的最后一个孩子移到childNode  
						if (!siblingNode.isLeaf()) {
							childNode.addChild(0, siblingNode.childAt(siblingNode.keySize()));
							siblingNode.removeChild(siblingNode.keySize());
						}
					}
					// 右兄弟节点满足条件  
					else {
						childNode.addEntry(childNode.keySize() - 1, node.entryAt(result.getIndex()));
						node.removeEntry(result.getIndex());
						node.addEntry(result.getIndex(), siblingNode.entryAt(0));
						siblingNode.removeEntry(0);
						// 将右兄弟节点的第一个孩子移到childNode  
						// childNode.insertChild(siblingNode.childAt(0), childNode.size() + 1);  
						if (!siblingNode.isLeaf()) {
							childNode.addChild(siblingNode.childAt(0));
							siblingNode.removeChild(0);
						}
					}
					return delete(childNode, key);
				} else // 3.b 如果其相邻左右节点都包含t-1个项  
				{
					if (result.getIndex() < node.keySize()) // 存在右兄弟，直接在后面追加  
					{
						Node<K, V> rightSiblingNode = node.childAt(result.getIndex() + 1);
						childNode.addEntry(node.entryAt(result.getIndex()));
						node.removeEntry(result.getIndex());
						node.removeChild(result.getIndex() + 1);
						for (int i = 0; i < rightSiblingNode.keySize(); ++i)
							childNode.addEntry(rightSiblingNode.entryAt(i));
						if (!rightSiblingNode.isLeaf()) {
							for (int i = 0; i <= rightSiblingNode.keySize(); ++i)
								childNode.addChild(rightSiblingNode.childAt(i));
						}
					} else // 存在左节点，在前面插入  
					{
						Node<K, V> leftSiblingNode = node.childAt(result.getIndex() - 1);
						childNode.addEntry(0, node.entryAt(result.getIndex() - 1));
						node.removeEntry(result.getIndex() - 1);
						node.removeChild(result.getIndex() - 1);
						for (int i = leftSiblingNode.keySize() - 1; i >= 0; --i)
							childNode.addEntry(0, leftSiblingNode.entryAt(i));
						if (!leftSiblingNode.isLeaf()) {
							for (int i = leftSiblingNode.keySize(); i >= 0; --i)
								childNode.addChild(0, leftSiblingNode.childAt(i));
						}
					}
					// 如果node是root并且node不包含任何项了  
					if (node == root && node.keySize() == 0)
						root = childNode;
					return delete(childNode, key);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private int compare(K key1, K key2) {
		return keyComparator == null ? ((Comparable<K>) key1).compareTo(key2) : keyComparator.compare(key1, key2);
	}

	/**
	 * 节点是个满节点(key值已满)
	 * @param node 节点
	 * @return 满节点返回true,否则返回false
	 */
	private boolean isFullNode(Node<K, V> node) {
		return root.keySize() >= maxKeySize;
	}

	/**
	 * 一个简单的层次遍历B树实现，用于输出B树。
	 */
	public void output() {
		Queue<Node<K, V>> queue = new LinkedList<Node<K, V>>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node<K, V> node = queue.poll();
			for (int i = 0; i < node.keySize(); ++i)
				System.out.print(node.entryAt(i) + " ");
			System.out.println();
			if (!node.isLeaf()) {
				for (int i = 0; i <= node.keySize(); ++i)
					queue.offer(node.childAt(i));
			}
		}
	}

	/**
	 * B树节点中的键值对
	 */
	private static class Entry<K, V> {

		private K key;
		private V value;

		public Entry(K k, V v) {
			this.key = k;
			this.value = v;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return key + ":" + value;
		}
	}

	/** 搜索给定键值的返回结果 */
	private static class SearchResult<V> {
		private final boolean exist;//查找是否成功
		private final int index;//给定键值在节点中的位置 |给定键值应该插入的位置
		private final V value;//给定键对应的值

		public SearchResult(boolean exist, int index, V value) {
			this.exist = exist;
			this.index = index;
			this.value = value;
		}

		public boolean isExist() {
			return exist;
		}

		public int getIndex() {
			return index;
		}

		public V getValue() {
			return value;
		}
	}

	/**
	 * B树中的节点。
	 */
	private static class Node<K, V> {
		/** 节点的项，按键非降序存放 */
		private List<Entry<K, V>> entries;
		/** 内节点的子节点 */
		private List<Node<K, V>> children;
		/** 是否为叶子节点 */
		private boolean leaf;
		/** 键的比较函数对象 */
		private Comparator<K> keyComparator;

		private Node() {
			entries = new ArrayList<Entry<K, V>>();
			children = new ArrayList<Node<K, V>>();
			leaf = false;
		}

		public Node(Comparator<K> kComparator) {
			this();
			this.keyComparator = kComparator;
		}

		public boolean isLeaf() {
			return leaf;
		}

		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}

		/**
		 * 返回项的个数。(如果是非叶子节点，根据B树的定义， 该节点的子节点个数为({@link #size()} + 1))
		 * @return 关键字的个数
		 */
		public int keySize() {
			return entries.size();
		}

		/**
		 * 在节点中查找给定的键<br>
		 * @param key 给定的键值
		 * @return 查找结果
		 */
		public SearchResult<V> search(K key) {
			int low = 0;
			int high = entries.size() - 1;
			int mid = 0;
			//使用二分查找算法  时间复杂度为O(log(t))
			while (low <= high) {
				mid = (low + high) / 2;
				Entry<K, V> entry = entries.get(mid);
				int compare = compare(key, entry.key);
				// key == mid.key
				if (compare == 0) {
					break;
				}
				// key < mid.key 
				else if (compare < 0) {
					high = mid - 1;
				}
				// key > mid.key
				else {
					low = mid + 1;
				}
			}
			boolean result = false;
			int index = 0;
			V value = null;
			//查找成功  
			if (low <= high) {
				result = true;
				index = mid; //元素所在的位置  
				value = entries.get(index).value;
			}
			//说明查找失败
			else {
				result = false;
				index = low; //元素应该插入的位置 
			}
			return new SearchResult<V>(result, index, value);
		}

		/**
		 * 将给定的项追加到节点的末尾
		 * @param entry 给定的项
		 */
		public void addEntry(Entry<K, V> entry) {
			entries.add(entry);
		}

		/**
		 * 在该节点中给定索引的位置插入给定的项
		 * @param key 给定的键值
		 * @param index 给定的索引
		 */
		public void addEntry(int index, Entry<K, V> entry) {
			entries.add(index, entry);
		}

		/**
		 * 删除给定索引的项目
		 * @param index 给定的索引
		 * @param 给定索引处的项
		 */
		public Entry<K, V> removeEntry(int index) {
			return entries.remove(index);
		}

		/**
		 * 更新其关联的值，如果不存在则插入
		 * @param entry 给定的项
		 * @return 返回给定键之前关联的值(如果节点之前不存在给定的键，返回null)
		 */
		public V putEntry(Entry<K, V> entry) {
			SearchResult<V> result = search(entry.getKey());
			if (result.isExist()) {
				V oldValue = entries.get(result.getIndex()).getValue();
				entries.get(result.getIndex()).setValue(entry.getValue());
				return oldValue;
			} else {
				addEntry(result.getIndex(), entry);
				return null;
			}
		}

		/**
		 * 得到节点中给定索引的项
		 * @param index - 给定的索引
		 * @return 节点中给定索引的项
		 */
		public Entry<K, V> entryAt(int index) {
			return entries.get(index);
		}

		/**
		 * 将给定的子节点追加到该节点的末尾
		 * @param child 给定的子节点
		 */
		public void addChild(Node<K, V> child) {
			children.add(child);
		}

		/**
		 * 将给定的子节点插入到该节点中给定索引 的位置
		 * @param child - 给定的子节点
		 * @param index - 子节点带插入的位置
		 */
		public void addChild(int index, Node<K, V> child) {
			children.add(index, child);
		}

		/**
		 * 删除该节点中给定索引位置的子节点。
		 * @param index 给定的索引
		 */
		public void removeChild(int index) {
			children.remove(index);
		}

		/**
		 * 返回节点中给定索引的子节点
		 * @param index - 给定的索引
		 * @return 给定索引对应的子节点
		 */
		public Node<K, V> childAt(int index) {
			if (isLeaf()) {
				throw new UnsupportedOperationException("Leaf node doesn't have children");
			}
			return children.get(index);
		}

		@SuppressWarnings("unchecked")
		private int compare(K key1, K key2) {
			return keyComparator == null ? ((Comparable<K>) key1).compareTo(key2) : keyComparator.compare(key1, key2);
		}
	}

	public static void assertState(boolean expression) {
		if (!expression) {
			throw new IllegalStateException("[Assertion failed]");
		}
	}

	public static void main(String[] args) {
		BTree<Integer, String> tree = new BTree<Integer, String>();
		tree.put(1, "A");
		tree.put(2, "B");
		tree.put(3, "C");
		tree.put(5, "D");
		tree.put(6, "E");
		tree.put(8, "F");
		tree.put(9, "G");
		tree.put(11, "H");
		tree.put(12, "I");
		tree.put(13, "I");
		tree.put(15, "I");
		tree.output();
	}
}

//#　　　　　　　　　　　[9]
//#　　　　　　┏━━━━━┻━━━━━┓
//#　　　　　[2,6]　　　　　　　　　[12]
//#　　┏━━━╋━━━━┓　　　┏━━━┻━━┓
//#　[1]　　[3,5]　　[8]　[11]　　　[13,15]
