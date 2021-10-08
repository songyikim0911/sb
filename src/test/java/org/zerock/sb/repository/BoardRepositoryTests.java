package org.zerock.sb.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.sb.dto.BoardDTO;
import org.zerock.sb.entity.Board;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testSearch1(){
        char[] typeArr = null;
        String keyword = null;
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Board> result =boardRepository.search1(typeArr, keyword, pageable);

        result.get().forEach(board->
        {
            log.info(board);
            log.info("------");

            BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

            log.info(boardDTO);

            //이제 보드가 보드디티오로 다 알아서 바뀌어져있을것

        });

    }


    @Test
    public void testEx1(){

        Pageable pageable = PageRequest.of(1,10,Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.ex1(pageable);

        result.get().forEach(element -> {//루프로 돌리면 배열인데
            Object[] arr = (Object[])element;//배열안에 또 배열 내용이나오는것,
            log.info(Arrays.toString(arr));//배열안에 내용을 나오게하는것은, arrays.toString
        });//result get하면 스트림이나오고~ 그다음 forEach문!

    }


}
