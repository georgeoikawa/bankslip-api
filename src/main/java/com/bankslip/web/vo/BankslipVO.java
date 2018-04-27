package com.bankslip.web.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.bankslip.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankslipVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank(message = Constants.MSG_DUEDATE_REQUIRED)
    @JsonProperty("due_date")
    private String dueDate;

    @NotBlank(message = Constants.MSG_TOTAL_REQUIRED)
    @JsonProperty("total_in_cents")
    private String totalInCents;

    @NotBlank(message = Constants.MSG_CUSTOMER_REQUIRED)
    private String customer;

    @NotBlank(message = Constants.MSG_STATUS_REQUIRED)
    private String status;

    private String assessment;

    public BankslipVO() {}

    public BankslipVO(String id, String customer, String dueDate, String status, String totalInCents, String assessment) {
        this.id = id;
        this.customer = customer;
        this.dueDate = dueDate;
        this.status = status;
        this.totalInCents = totalInCents;
        this.assessment = assessment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(String totalInCents) {
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

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    @Override
    public String toString() {
        return "BankSlipVO [id=" + id + ", dueDate=" + dueDate + ", totalInCents=" + totalInCents + ", customer="
                + customer + ", status=" + status + ", assessment=" + assessment + "]";
    }
}