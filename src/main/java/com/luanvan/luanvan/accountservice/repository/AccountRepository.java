package com.luanvan.luanvan.accountservice.repository;

import com.luanvan.luanvan.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByEmail(String email);
    @Query("SELECT userId FROM Account WHERE email = :email")
    int findUserIdByEmail(@Param("email") String email);
}
