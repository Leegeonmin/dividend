package com.zerobase.dividend.service;

import com.zerobase.dividend.domain.MemberEntity;
import com.zerobase.dividend.dto.MemberDto;
import com.zerobase.dividend.error.CustomException;
import com.zerobase.dividend.error.ErrorCode;
import com.zerobase.dividend.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, List<String> roles) {
        if (memberRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.ALREADY_USERNAME_EXISTED);
        }
        memberRepository.save(MemberEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build());


    }

    public MemberDto login(String username, String password) {
        MemberEntity memberEntity = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if(!passwordEncoder.matches(password, memberEntity.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_WRONG);
        }
        return MemberDto.builder()
                .username(memberEntity.getUsername())
                .roles(memberEntity.getRoles())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
