package org.zerock.sb.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb.dto.BoardDTO;
import org.zerock.sb.dto.BoardListDTO;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;

@Transactional
public interface BoardService {

//새로운 게시물이 등록되면 해당 게시물의 번호 보여주기

    Long register(BoardDTO boardDTO);

    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListDTO> getListWithReply(PageRequestDTO pageRequestDTO);


    BoardDTO read(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

}
