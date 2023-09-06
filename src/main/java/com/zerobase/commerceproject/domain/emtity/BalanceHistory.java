package com.zerobase.commerceproject.domain.emtity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceHistory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    //변경된 된
    private Integer changeMoney;

    //해당 시점 잔액
    private Integer currentMoney;

    private String fromMessage;

    private String description;
}
