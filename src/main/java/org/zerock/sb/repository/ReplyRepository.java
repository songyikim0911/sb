package org.zerock.sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.sb.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findReplyByBoard_BnoOrderByRno(Long bno);

}


