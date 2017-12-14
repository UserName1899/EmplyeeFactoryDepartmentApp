
package com.factory.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employee {

@Id
    @GeneratedValue(strategy = GenerationType.AUTO)

private Long id;

    private String employeeName;
    private String employeeEmail;
    private String employeeNo;

  @OneToMany
    private List<Department> department;


    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

}



