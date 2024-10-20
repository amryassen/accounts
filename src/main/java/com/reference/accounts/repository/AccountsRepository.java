package com.reference.accounts.repository;

import com.reference.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(Long CustomerId);

    @Query("SELECT a.customerId FROM Accounts a WHERE a.accountNumber = :accountNumber")
    Optional<Long> findCustomerIdByAccountNumber(@Param("accountNumber") Long AccountNumber);

    @Modifying
    void deleteByCustomerId(Long customerId);
}
