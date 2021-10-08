package org.zerock.sb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.sb.dto.BoardDTO;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.service.BoardService;

@Controller//스프링 부트 = 빈안해도 알아서 인식.루트패키지아래일때의경우!
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){//담아서 보내야 하므로 모델필요

        model.addAttribute("responseDTO", boardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register(){

    }


    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result",bno);

        return "redirect:/board/list";

    }


    @GetMapping("/read")
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){//pagerequestDTO는 나중에 목록으로 돌아가기위함.

        model.addAttribute("dto", boardService.read(bno));



    }


}
