package com.zerobase.commerceproject.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangeBalanceForm {
    private String from;
    private String message;
    private Integer money;
}
