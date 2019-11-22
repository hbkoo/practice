package com.example.acer.practice6ofsqlite.com.example.acer.bean;

/**
 * Created by 19216 on 2019/1/13.
 */

public class Staff {

    private int id;
    private String sname;
    private String dname;
    private String sjob;
    private float salary;

    public Staff(int id, String sname, String dname, String sjob, float salary) {
        this.id = id;
        this.sname = sname;
        this.dname = dname;
        this.sjob = sjob;
        this.salary = salary;
    }

    public Staff(String sname, String dname, String sjob, float salary) {
        this.sname = sname;
        this.dname = dname;
        this.sjob = sjob;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", sname='" + sname + '\'' +
                ", dname='" + dname + '\'' +
                ", sjob='" + sjob + '\'' +
                ", salary=" + salary +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getSjob() {
        return sjob;
    }

    public void setSjob(String sjob) {
        this.sjob = sjob;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
