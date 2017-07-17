package yyl.example.demo.guava.collect;

import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

/**
 * 类似Map<String, Map<String, String>>的实现<br>
 * 有两个支持所有类型的键：”行”和”列” <br>
 */
public class TableTest {
	public static void main(String[] args) {
		Table<String, String, String> table = HashBasedTable.create();

		// put(行,列,值)

		// # 0 1 2
		// 0 A B C
		// 1 D E F
		// 2 G H I

		table.put("0", "0", "A");
		table.put("0", "1", "B");
		table.put("0", "2", "C");

		table.put("1", "0", "D");
		table.put("1", "1", "E");
		table.put("1", "2", "F");

		table.put("2", "0", "G");
		table.put("2", "1", "H");
		table.put("2", "2", "I");

		//根据行，获得 列->值
		Map<String, String> row = table.row("1"); // D E F
		System.out.println(row);

		//根据列，获得 行->值
		Map<String, String> column = table.column("2"); //C F I
		System.out.println(column);

		System.out.print("\n|\\|");
		for (String columnKey : table.columnKeySet()) {
			System.out.print(columnKey + "|");
		}
		System.out.println();
		for (String rowKey : table.rowKeySet()) {
			System.out.print("|" + rowKey + "|");
			for (String columnKey : table.columnKeySet()) {
				System.out.print(table.get(rowKey, columnKey) + "|");
			}
			System.out.println();
		}
		System.out.println();

		for (Cell<String, String, String> cell : table.cellSet()) {
			System.out.println(cell.getRowKey() + "$" + cell.getColumnKey() + "=" + cell.getValue());
		}

	}
}
