package com.zerobase.dividend.controller;

import com.zerobase.dividend.dto.Auth;
import com.zerobase.dividend.dto.MemberDto;
import com.zerobase.dividend.security.TokenProvider;
import com.zerobase.dividend.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Auth.SignUp request) {
        memberService.registerUser(request.getUsername(), request.getPassword(), request.getRoles());

        return ResponseEntity.ok().body("register " + request.getUsername() + " successfully") ;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody @Valid Auth.SignIn request) {
        MemberDto memberDto = memberService.login(request.getUsername(), request.getPassword());

        String jwt = tokenProvider.generateToken(memberDto.getUsername(), memberDto.getRoles());

        return ResponseEntity.ok().body(jwt);
    }
}
