package yyl.example.basic.algorithm.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>字典树</h3><br>
 * 字典树又称单词查找树，Trie树，是一种树形结构，是一种哈希树的变种。<br>
 * 典型应用是用于统计，排序和保存大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。<br>
 * 它的优点是：利用字符串的公共前缀来减少查询时间，最大限度地减少无谓的字符串比较，查询效率比哈希树高。<br>
 * 基本性质：<br>
 * ①根节点不包含字符，除根节点外的每一个子节点都包含一个字符。<br>
 * ②从根节点到某一个节点，路径上经过的字符连接起来，为该节点对应的字符串。<br>
 * ③每个节点的所有子节点包含的字符互不相同。<br>
 */
public class TrieNode {

	private char value;
	private List<TrieNode> children = new ArrayList<>();
	private boolean end = false;

	public void addWord(CharSequence string) {
		if (string == null || string.length() == 0) {
			return;
		}
		TrieNode node = this;
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			TrieNode next = node.next(c);
			if (next == null) {
				node.children.add(next = new TrieNode());
			}
			node = next;
		}
		node.end = true;
	}

	public TrieNode next(char c) {
		for (TrieNode node : children) {
			if (node.value == c) {
				return node;
			}
		}
		return null;
	}

	public boolean isEnd() {
		return end;
	}
}
