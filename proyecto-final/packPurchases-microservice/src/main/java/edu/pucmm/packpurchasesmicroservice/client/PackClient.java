package edu.pucmm.packpurchasesmicroservice.client;

import edu.pucmm.packpurchasesmicroservice.dto.PackDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PACK-MICROSERVICE")
public interface PackClient {
    @GetMapping("/pack/read/{id}")
    PackDto findPackById(@PathVariable int id);
}
