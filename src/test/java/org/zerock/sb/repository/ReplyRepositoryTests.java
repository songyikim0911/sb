package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.Reply;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insert200(){

        IntStream.rangeClosed(1,200).forEach(i->{

            Long bno = (long)(200 - (i % 5)); //나오는 값은 200,199,198,196,196.. i를 5로 나눈 나머지값이니깐.

            int replyCount = (i % 5);//0,1,2,3,4... 이것만큼 댓글 추가 예정

            IntStream.rangeClosed(0,replyCount).forEach(j->{//0부터 replyCount까지...

                Board board = Board.builder().bno(bno).build();
                //보드 객체를 만들어서 이 보드 객체를 reply에 넣어주기~ board객체는 id가 중요한것, id만 있으면 식별이 가능하고
                //id가 같으면 같은 객체로 생각하기떄문에 id만 넣어주면된다.
                //reply에서 board를 무는 단방향참조이기떄문에 이게 가능한것임.!!
                Reply reply = Reply.builder()
                        .replyText("Reply....")
                        .replyer("replyer...")
                        .board(board)
                        .build();
                replyRepository.save(reply);
            });//inner loop

        });//outer loop

    }

    @Test
    public void testRead(){

        Long rn = 1L;
        Reply reply = replyRepository.findById(rn).get();
        log.info(reply);

    }

    @Test
    public void testByBno(){
        Long bno = 200L;
        List<Reply> replyList
                = replyRepository.findReplyByBoard_BnoOrderByRno(bno);

   replyList.forEach (reply-> log.info(reply));
    }

}
