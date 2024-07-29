package com.juju.JUJU_project_backend.service;

import com.juju.JUJU_project_backend.dto.MemberDTO;
import com.juju.JUJU_project_backend.entity.MemberEntity;
import com.juju.JUJU_project_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }
}
