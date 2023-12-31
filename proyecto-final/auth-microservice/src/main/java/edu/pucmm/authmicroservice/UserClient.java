package edu.pucmm.authmicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USERS-MICROSERVICE")
public interface UserClient {
    @GetMapping("/user/read/email")
    UserDto findUserByEmail(@RequestBody AuthenticationRequest authenticationRequest);
}
