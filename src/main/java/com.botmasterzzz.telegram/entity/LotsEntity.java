package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "pim_lots", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class LotsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //todo проверить - надо ли здесь @Column?
    private Long id;

    @Column(name = "creator", nullable = false)
    private long creator;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "invited")
    private int invited;

    @Column(name = "ready")
    private double ready;

    @Column(name = "customer")
    private long customer;

    @Column(name = "archive")
    private boolean archive;

    @Column(name = "channel")
    private String channel;

    @Column(name = "validate")
    private boolean validate;

    @Column(name = "payed")
    private boolean payed;

    @Column(name = "isdel")
    private boolean isdel;

    @Column(name = "iscreate")
    private boolean iscreate;

    @Column(name = "islock")
    private boolean islock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotsEntity that = (LotsEntity) o;
        return cost == that.cost &&
                quantity == that.quantity &&
                invited == that.invited &&
                Double.compare(that.ready, ready) == 0 &&
                archive == that.archive &&
                validate == that.validate &&
                payed == that.payed &&
                isdel == that.isdel &&
                iscreate == that.iscreate &&
                islock == that.islock &&
                Objects.equals(id, that.id) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator, cost, quantity, invited, ready, customer, archive, channel, validate, payed, isdel, iscreate, islock);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInvited() {
        return invited;
    }

    public void setInvited(int invited) {
        this.invited = invited;
    }

    public double getReady() {
        return ready;
    }

    public void setReady(double ready) {
        this.ready = ready;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public boolean isIsdel() {
        return isdel;
    }

    public void setIsdel(boolean isdel) {
        this.isdel = isdel;
    }

    public boolean isIscreate() {
        return iscreate;
    }

    public void setIscreate(boolean iscreate) {
        this.iscreate = iscreate;
    }

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    @Override
    public String toString() {

                return "LotsEntity{" +
                                "id=" + id +
                                ", creator='" + creator + '\'' +
                                ", cost=" + cost +
                                ", quantity=" + quantity +
                                ", invited=" + invited +
                                ", ready=" + ready +
                                ", customer='" + customer + '\'' +
                                ", archive=" + archive +
                                ", channel='" + channel + '\'' +
                                ", validate=" + validate +
                                ", payed=" + payed +
                                ", isdel=" + isdel +
                                ", iscreate=" + iscreate +
                                ", islock=" + islock +
                                '}';
    }

    public String active() {
        return "[" + id + "]-[" + ready + "%]-[" + customer + "]";
    }

    public String inWork() {
        return "[" + id + "]-[" + quantity + "]-[" + invited + "]-[" + ready + "%]";
    }

    public String choose() {
        return "Заявка #"+id+"\n"+
                " по привлечению [" + quantity + "] подписчиков\n" +
                "на ["+channel+"] канал \n"+
                "за [" + cost + "] рублей";
    }

}
