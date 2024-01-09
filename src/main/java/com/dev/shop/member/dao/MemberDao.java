package com.dev.shop.member.dao;

import com.dev.shop.member.dto.MemberDetailsDto;
import com.dev.shop.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MemberDao {

    /**
     *
     * @param memberId - 로그인 멤버의 id
     * @return 멤버의 정보
     */
    MemberDetailsDto selectMemberById(String memberId);
    void insertMemberRegister(MemberDto memberDto);
    MemberDto selectMemberInfoById(String authId);
    void updateMemberInformation(MemberDto memberDto);


    String selectMemberPw(Long memberNo);
}
