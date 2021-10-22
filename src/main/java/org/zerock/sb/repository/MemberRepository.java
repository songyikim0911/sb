package org.zerock.sb.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sb.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    //독특한점, ID가 String인것!

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Member m where m.mid = :mid")
    Optional<Member> getMemberEager(String mid);
}
