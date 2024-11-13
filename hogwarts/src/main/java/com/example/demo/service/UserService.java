package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;


    public long getTotalUsers() {
        return memberRepository.count();
    }

    public Member getUserDetails(String username) {
        return memberRepository.findByUsername(username);
    }

    public List<Member> getAllUsers() {
        return memberRepository.findByRole("USER");
    }

    @Transactional
    public void updateUser(@ModelAttribute Member member) {
        Member updateMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            updateMember.setPassword(passwordEncoder().encode(member.getPassword()));  // 새 비밀번호로 업데이트
        }
            updateMember.setEmail(member.getEmail());
            memberRepository.save(updateMember);
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        Optional<Member> user = memberRepository.findByEmail(email);

        if (user.isPresent()) {
            memberRepository.delete(user.get());  // 사용자 삭제
            boardRepository.deleteByUsername(user.get().getUsername());
        } else {
            throw new UserNotFoundException("해당 이메일을 가진 사용자가 없습니다.");
        }

    }


}
