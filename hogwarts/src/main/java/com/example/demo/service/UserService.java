package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private MemberRepository memberRepository;

    public Member getUserDetails(String username) {
        return memberRepository.findByUsername(username);
    }

    public List<Member> getAllUsers() {
        return memberRepository.findByRole("USER");
    }

    @Transactional
    public void updateUser(Member user) {
         memberRepository.saveAndFlush(user);
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        Optional<Member> user = memberRepository.findByEmail(email);

        if (user.isPresent()) {
            memberRepository.delete(user.get());  // 사용자 삭제
        } else {
            throw new UserNotFoundException("해당 이메일을 가진 사용자가 없습니다.");
        }
    }
}
