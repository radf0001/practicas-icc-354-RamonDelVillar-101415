package edu.pucmm.packpurchasesmicroservice.client;

import edu.pucmm.packpurchasesmicroservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@FeignClient(name = "USERS-MICROSERVICE")
public interface UserClient {
    @GetMapping("/user/read/{id}")
    UserDto findUserById(@PathVariable int id);

    @GetMapping("/user/readByRole/{role}")
    String[] readAllByRole(@PathVariable String role);
}
