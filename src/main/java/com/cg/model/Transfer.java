package com.cg.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;


@Entity
@Table(name = "transfers")
public class Transfer extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer = 12, fraction = 0)
    @Column(name = "transfer_amount", nullable = false)
    private BigDecimal transferAmount;

    @Column(name = "fees", nullable = false)
    private long fees;

    @Digits(integer = 12, fraction = 0)
    @Column(name = "fees_amount", nullable = false)
    private BigDecimal feesAmount;

    @Digits(integer = 12, fraction = 0)
    @Column(name = "transaction_amount", nullable = false)
    private BigDecimal transactionAmount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Customer recipient;

    public Transfer() {
    }

    public Transfer(Long id, @Digits(integer = 12, fraction = 0) BigDecimal transferAmount, long fees, @Digits(integer = 12, fraction = 0) BigDecimal feesAmount, @Digits(integer = 12, fraction = 0) BigDecimal transactionAmount) {
        this.id = id;
        this.transferAmount = transferAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.transactionAmount = transactionAmount;
    }

    public Transfer(Long id, @Digits(integer = 12, fraction = 0) BigDecimal transferAmount, long fees, @Digits(integer = 12, fraction = 0) BigDecimal feesAmount, @Digits(integer = 12, fraction = 0) BigDecimal transactionAmount, Customer sender, Customer recipient) {
        this.id = id;
        this.transferAmount = transferAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.transactionAmount = transactionAmount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public long getFees() {
        return fees;
    }

    public void setFees(long fees) {
        this.fees = fees;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(BigDecimal feesAmount) {
        this.feesAmount = feesAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }
}
