package com.budgetfy.app.model;

import com.budgetfy.app.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

}