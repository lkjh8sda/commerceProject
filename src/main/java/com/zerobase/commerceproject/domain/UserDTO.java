package com.zerobase.commerceproject.domain;

import com.zerobase.commerceproject.domain.emtity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
   // private Integer balance;

    public static UserDTO from(User user){
        return new UserDTO(user.getId(), user.getEmail());
    }
}
