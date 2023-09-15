package com.zerobase.commerceproject.domain.user;

import com.zerobase.commerceproject.domain.emtity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private Integer balance;

    public static UserDTO from(User user){
        return new UserDTO(user.getId(), user.getEmail(), user.getBalance());
    }
}
