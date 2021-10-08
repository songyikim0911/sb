package org.zerock.sb.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.dto.BoardDTO;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder().bno(200L).title("한글제목")
                .content("한글 내용").build();//이렇게만하면 수정 할떄 필요한 모든정보가 다있는것!
        //참고, 원래랑 내용이 같으면 update가 이뤄지지않는다.

        boardService.modify(boardDTO);
    }


    @Test
    public void testRegister(){

        IntStream.rangeClosed(1,200).forEach(i->{
            BoardDTO boardDTO = BoardDTO.builder()
                    .title("Title..."+i)
                    .content("Content..."+i)
                    .writer("user..."+(i%10))
                    .build();

           Long bno = boardService.register(boardDTO);
           log.info("BNO: " +bno);
        });

    }

}
