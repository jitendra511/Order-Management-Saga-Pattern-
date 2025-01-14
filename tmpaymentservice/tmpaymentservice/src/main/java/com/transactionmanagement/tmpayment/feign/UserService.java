package com.transactionmanagement.tmpayment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name="tmuserservice",url = "http://localhost:8086/tm")
public interface UserService {
    @GetMapping("/getBalance")
    public Integer getBalance(@RequestHeader("Authorization") String token);
    @PutMapping("/updateBalance")
    public Integer updateBalance(@RequestHeader("Authorization") String token,@RequestParam Integer balance);
}
