package com.joel.rating.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserService {
    @GetMapping("users/exists/{userId}")
    ResponseEntity<Boolean> doesUserExist(@PathVariable String userId);
}
