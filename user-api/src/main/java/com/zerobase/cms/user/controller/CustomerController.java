package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.domain.customer.CustomerDto;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.CustomerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.config.domain.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final CustomerService customerService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

        UserVo vo = jwtAuthenticationProvider.getUserVo(token);

        Customer c = customerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return ResponseEntity.ok(CustomerDto.from(c));
    }

}
