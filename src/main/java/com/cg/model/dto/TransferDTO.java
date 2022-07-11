package com.cg.model.dto;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

public class TransferDTO implements Validator {

    @NotBlank(message = "Sender is required")
    @Pattern(regexp = "^[0-9]$", message = "Sender ID only digit")
    private String senderId;

    private String email;
    private String senderName;

    @NotBlank(message = "Recipient is required")
    @Pattern(regexp = "^[0-9]$", message = "Recipient ID only digit")
    private String recipientId;

    private String balance;

    @NotBlank(message = "Transfer Amount is required")
    @Size(min = 1, max = 7, message = "length of transfer amount in between 50 to 100.000")
    @DecimalMin(value = "50", message = "Min is 50")
    @DecimalMax(value = "100000", message = "Min is 100.000")
    private String transferAmount;

    public TransferDTO() {
    }

    public TransferDTO(String senderId, String email, String senderName, String recipientId, String balance, String transferAmount) {
        this.senderId = senderId;
        this.email = email;
        this.senderName = senderName;
        this.recipientId = recipientId;
        this.balance = balance;
        this.transferAmount = transferAmount;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
