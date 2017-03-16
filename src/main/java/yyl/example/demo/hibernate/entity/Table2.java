package yyl.example.demo.hibernate.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "table2")
public class Table2 {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "time")
	private Date time;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "table1_id", referencedColumnName = "id")
	private Table1 table1;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Table1 getTable1() {
		return table1;
	}

	public void setTable1(Table1 table1) {
		this.table1 = table1;
	}

	@Override
	public String toString() {
		return "Table2 [id=" + id + ", name=" + name + ", time=" + time + ", table1=" + (table1 == null ? null : table1.getId()) + "]";
	}
}
