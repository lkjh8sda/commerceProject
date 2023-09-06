package com.zerobase.commerceproject.domain.emtity;

import com.zerobase.commerceproject.domain.SignUpForm;
import com.zerobase.commerceproject.domain.UserType;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private String phone;

    // seller or customer or all
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String verificationCode;
    private boolean verify;

    @Column(columnDefinition ="int default 0")
    private Integer balance;

    public static User from(SignUpForm form){
        return User.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .phone(form.getPhone())
                .userType(UserType.valueOf(form.getUserType()))
                .verificationCode(form.getVerificationCode())
                .verify(false)
                .build();
    }

}
