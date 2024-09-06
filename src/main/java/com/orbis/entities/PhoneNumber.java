package com.orbis.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Entity
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(length = 3, name = "phone_ddi")
    private String phoneDDI;

    @Column(length = 3, name = "phone_ddd")
    private String phoneDDD;

    @Column(length = 10, name = "phone_number")
    private String phoneNumber;

    @Column(length = 2, name = "phone_type")
    private String phoneType;

    @Column(columnDefinition = "CHAR(1) default 'T'", length = 1)
    private String status = "T";

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP", name = "insert_timestamp")
    @CreationTimestamp
    private LocalDateTime insertTimestamp;
}