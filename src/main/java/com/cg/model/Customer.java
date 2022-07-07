package com.cg.model;


import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    private String address;

    @Digits(integer = 12, fraction = 0)
    @Column(updatable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "customer")
    private Set<Deposit> deposits;

    @OneToMany(mappedBy = "customer")
    private Set<Withdraw> withdraws;

    @OneToMany(mappedBy = "sender")
    private Set<Transfer> sender;

    @OneToMany(mappedBy = "recipient")
    private Set<Transfer> recipient;

    public Customer() {
    }

    public Customer(Long id, String fullName, String email, String phone, String address, @Digits(integer = 12, fraction = 0) BigDecimal balance) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public Customer(Long id, String fullName, String email, String phone, String address, @Digits(integer = 12, fraction = 0) BigDecimal balance, Set<Deposit> deposits, Set<Withdraw> withdraws, Set<Transfer> sender, Set<Transfer> recipient) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.deposits = deposits;
        this.withdraws = withdraws;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }

    public Set<Withdraw> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(Set<Withdraw> withdraws) {
        this.withdraws = withdraws;
    }

    public Set<Transfer> getSender() {
        return sender;
    }

    public void setSender(Set<Transfer> sender) {
        this.sender = sender;
    }

    public Set<Transfer> getRecipient() {
        return recipient;
    }

    public void setRecipient(Set<Transfer> recipient) {
        this.recipient = recipient;
    }
}
