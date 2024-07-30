package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor@ToString
@NoArgsConstructor
@Table(name="Accounts")
@Entity
public class Accounts extends BaseEntity {


    @Column(name = "customer_id")
    private long customerId;

    @Id
    @Column(name = "account_number")
    private long accountNumber;

    @NonNull
    @Column(name = "account_type")
    private String accountType;

    @NonNull
    @Column(name = "branch_address")
    private String branchAddress;
}
