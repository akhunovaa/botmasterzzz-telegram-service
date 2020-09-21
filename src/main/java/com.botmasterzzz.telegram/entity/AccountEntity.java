package com.botmasterzzz.telegram.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "pim_account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "accountid")
})
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountid")
    private Long accountid;

    @Column(name = "userid")
    private int userid;

    @Column(name = "income")
    private double income;

    @Column(name = "outcome")
    private double outcome;

    @Column(name = "total")
    private double total;

    @Column(name = "isactive")
    private boolean isactive;

    @Column(name = "islock")
    private boolean islock;

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getOutcome() {
        return outcome;
    }

    public void setOutcome(double outcome) {
        this.outcome = outcome;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return userid == that.userid &&
                Double.compare(that.income, income) == 0 &&
                Double.compare(that.outcome, outcome) == 0 &&
                Double.compare(that.total, total) == 0 &&
                isactive == that.isactive &&
                islock == that.islock &&
                Objects.equals(accountid, that.accountid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountid, userid, income, outcome, total, isactive, islock);
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "accountid=" + accountid +
                ", userid=" + userid +
                ", income=" + income +
                ", outcome=" + outcome +
                ", total=" + total +
                ", isactive=" + isactive +
                ", islock=" + islock +
                '}';
    }


}
