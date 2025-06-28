package com.vikul.lic.entity;


import com.vikul.lic.dto.PremMod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private Integer policyNo;
    private String name;
    private LocalDate startDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "premium_mode")
    private PremMod mod;
    private LocalDate nextDue;
    private Float amount;

}
