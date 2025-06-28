package com.vikul.lic.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    private String errorMessage;
    private Integer errorCode;
    private Date errorDate;

}
