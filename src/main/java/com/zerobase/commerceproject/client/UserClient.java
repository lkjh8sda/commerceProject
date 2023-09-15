package com.zerobase.commerceproject.client;

import com.zerobase.commerceproject.domain.ChangeBalanceForm;
import com.zerobase.commerceproject.domain.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user", url = "${feign.client.url.user}")
public interface UserClient {

    @GetMapping("/customer/getInfo")
    ResponseEntity<UserDTO> getCustomerInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token);

    @PostMapping("/customer/balance")
    ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody ChangeBalanceForm form);
}
