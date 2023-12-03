package com.atlisheng.servlet.bean;

import java.util.Objects;

/**
 *创建部门对象,可以用来封装部门数据,代表一个部门对象
 */

public class Dept {
    private String deptno;
    private String dname;
    private String loc;

    public Dept() {
    }

    public String getDeptno() {
        return deptno;
    }

    public String getDname() {
        return dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dept dept = (Dept) o;
        return Objects.equals(deptno, dept.deptno) && Objects.equals(dname, dept.dname) && Objects.equals(loc, dept.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptno, dname, loc);
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptno='" + deptno + '\'' +
                ", dname='" + dname + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
