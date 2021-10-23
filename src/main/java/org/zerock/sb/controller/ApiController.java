package org.zerock.sb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.sb.dto.BoardDTO;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.service.BoardService;

@RestController//JSON이목적이라 RestController.. reply와 비슷해질예정
@RequestMapping("/api/")//api 서버는 여기서 다 다룰예정
@RequiredArgsConstructor
@Log4j2
public class ApiController {

    private final BoardService boardService;//컨트롤러 계층은 무조건 서비스만 바라보도록, 주입! 중요!
    //그리고 조합,연산 가공은 서비스 계층에서 해야한다!!!
    //서비스계층에서는 무엇을하고, 영속계층(DAO)계층에서는 무엇을하는지 고민이 필요하다.

    @GetMapping("/board/list")
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO){//공통 유틸인 pageRequestDTO

        log.info(pageRequestDTO);//로그로 페이지 리퀘스트 디티오 어떻게 들어오는지 확인해보기

        return boardService.getList(pageRequestDTO);
    };




}
