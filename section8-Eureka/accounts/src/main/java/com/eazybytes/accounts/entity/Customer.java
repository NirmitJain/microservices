package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter@Setter @AllArgsConstructor@ToString@NoArgsConstructor
@Table(name="Customer")
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name= "email")
    private String email;

    @Column(name= "mobile_number")
    private String mobileNumber;

}
