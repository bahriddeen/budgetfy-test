package com.budgetfy.app.model;

import com.budgetfy.app.enums.PaymentType;
import com.budgetfy.app.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "template")
public class Template extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String payee;

    @Column
    private String note;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private boolean isExpense;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}

