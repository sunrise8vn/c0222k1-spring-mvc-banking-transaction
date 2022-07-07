package com.cg.model;


import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {

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

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;


    @OneToMany(mappedBy = "customer")
    private Set<Deposit> deposits;

    @OneToMany(mappedBy = "customer")
    private Set<Withdraw> withdraws;

    @OneToMany(mappedBy = "sender")
    private Set<Transfer> sender;

    @OneToMany(mappedBy = "recipient")
    private Set<Transfer> recipient;

}
