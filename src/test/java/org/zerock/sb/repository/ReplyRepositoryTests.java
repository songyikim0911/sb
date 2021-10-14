package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    @Test
    public void testRead(){

        Long rn = 1L;
        Reply reply = replyRepository.findById(rn).get();

        log.info(reply);

        log.info(reply.getBoard());

    }

    @Test
    public void testByBno(){
        Long bno = 200L;
        List<Reply> replyList
                = replyRepository.findReplyByBoard_BnoOrderByRno(bno);

   replyList.forEach (reply-> log.info(reply));
    }

    @Test
    public void testListOfBoard(){
        Pageable pageable =
                PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.getListByBno(197L,pageable);
        log.info(result.getTotalElements());
        result.get().forEach(reply->log.info(reply));

    }

    @Test
    public void testCountOfBoard(){

        Long bno = 190L;

        //ex)120일 경우
        int count = replyRepository.getReplyCountOfBoard(bno);

        int lastPage = (int)(Math.ceil(count/10.0));
        if(lastPage == 0){
            lastPage = 1;
        }
        //3가지 입력 : 0부터 시작하는 페이지번호(마지막 페이지가 12인경우-> 11) , 사이즈, 소트
        Pageable pageable = PageRequest.of(lastPage -1, 10);//sort.by제외->asc로자동처리
        Page<Reply> result = replyRepository.getListByBno(bno, pageable);//해당 게시물의 댓글 목록 가져오기

        log.info("total:"+result.getTotalElements());
        log.info("..." + result.getTotalPages());

        result.get().forEach(reply->{
            log.info(reply);
        });
    }
    //121/10.0 ==>12 * 10 120 limit 110,120
//        int lastPageNum = (int)(Math.ceil(count / (double)10)); //double로 한페이지당 10개씩 뿌리기
//
//        int lastEnd = lastPageNum * 10;//120
//        int lastStart = lastEnd - 10;
//
//        log.info(lastStart + " : " + lastEnd);
//

}
