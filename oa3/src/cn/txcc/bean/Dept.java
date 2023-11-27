package cn.txcc.bean;

import java.util.Objects;

/**
 * @author May
 * @version 1.0
 */
public class Dept {
    private String dno;
    private String dname;
    private String loc;

    public Dept(String dno, String dname, String loc) {
        this.dno = dno;
        this.dname = dname;
        this.loc = loc;
    }

    public Dept() {
    }

    public String getDeptno() {
        return dno;
    }

    public Dept setDeptno(String dno) {
        this.dno = dno;
        return this;
    }

    public String getDname() {
        return dname;
    }

    public Dept setDname(String dname) {
        this.dname = dname;
        return this;
    }

    public String getLoc() {
        return loc;
    }

    public Dept setLoc(String loc) {
        this.loc = loc;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dept dept = (Dept) o;
        return Objects.equals(dno, dept.dno) && Objects.equals(dname, dept.dname) && Objects.equals(loc, dept.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dno, dname, loc);
    }

    @Override
    public String toString() {
        return "Dept{" +
                "dno='" + dno + '\'' +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
