package com.vikul.lic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDto {

    private Integer policyNo;
    private String name;
    private String startDate;
    private PremMod mod;
    //private String nextDue;
    private Float amount;

}
