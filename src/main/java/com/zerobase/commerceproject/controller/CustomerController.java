package com.zerobase.commerceproject.controller;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.ChangeBalanceForm;
import com.zerobase.commerceproject.domain.user.UserVo;
import com.zerobase.commerceproject.domain.user.UserDTO;
import com.zerobase.commerceproject.domain.emtity.User;
import com.zerobase.commerceproject.exception.CustomException;
import com.zerobase.commerceproject.service.customer.BalanceService;
import com.zerobase.commerceproject.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zerobase.commerceproject.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequestMapping(value = "/customer")
@AllArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final BalanceService balanceService;
    @GetMapping("/getInfo")
    public ResponseEntity<UserDTO> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token){
        UserVo userVo = provider.getUserVo(token);
        User user = customerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return ResponseEntity.ok(UserDTO.from(user));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody ChangeBalanceForm form) {
        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(balanceService.changeBalance(vo.getId(), form).getCurrentMoney());
    }

}
