package com.cg.repository;

import com.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.beans.Customizer;
import java.math.BigDecimal;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query("" +
            "UPDATE Customer AS c " +
            "SET c.balance = c.balance + :transactionAmount " +
            "WHERE c.id = :id"
    )
    void incrementBalance(@Param("id") Long id, @Param("transactionAmount") BigDecimal transactionAmount);


    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query("" +
            "UPDATE Customer AS c " +
            "SET c.balance = c.balance - :transactionAmount " +
            "WHERE c.id = :id"
    )
    void reduceBalance(@Param("id") Long id, @Param("transactionAmount") BigDecimal transactionAmount);


    List<Customer> findAllByIdNot(Long id);
}
