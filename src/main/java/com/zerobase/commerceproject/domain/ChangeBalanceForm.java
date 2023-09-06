package com.zerobase.commerceproject.domain;

import lombok.Getter;

@Getter
public class ChangeBalanceForm {
    private String from;
    private String message;
    private Integer money;
}
