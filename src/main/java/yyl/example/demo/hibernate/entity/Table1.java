package yyl.example.demo.hibernate.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "table_1")
public class Table1 {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private Date time;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "table1_id", referencedColumnName = "id")
    private Set<Table2> table2Set;

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

    public Set<Table2> getTable2Set() {
        return table2Set;
    }

    public void setTable2Set(Set<Table2> table2Set) {
        this.table2Set = table2Set;
    }

    @Override
    public String toString() {
        return "Table1 [id=" + id + ", name=" + name + ", time=" + time + "]";
    }
}
