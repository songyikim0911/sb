package org.zerock.sb.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.dto.PageRequestDTO;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testList(){
        Long bno = 198L;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().
                        page(-1)
                        .build();

        log.info(replyService.getListOfBoard(bno, pageRequestDTO));
    }


}
