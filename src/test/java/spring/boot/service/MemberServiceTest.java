package spring.boot.service;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import spring.boot.model.Member;
import spring.boot.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("lee");

        // when
        Long savedId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복확인() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("lee");
        Member member2 = new Member();
        member2.setName("lee");

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}