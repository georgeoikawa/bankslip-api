package com.bankslip.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BANKSLIP")
public class Bankslip implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "total_in_cents")
    private Integer totalInCents;

    @Column(name = "customer")
    private String customer;

    @Column(name = "status")
    private String status;

    @Transient
    private int assessment;

    public Bankslip() {}

    public Bankslip(String customer, LocalDate dueDate, String status, Integer totalInCents) {
        this.customer = customer;
        this.dueDate = dueDate;
        this.status = status;
        this.totalInCents = totalInCents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(Integer totalInCents) {
        this.totalInCents = totalInCents;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAssessment() {
        return assessment;
    }

    public void setAssessment(Integer assessment) {
        this.assessment = assessment;
    }
}