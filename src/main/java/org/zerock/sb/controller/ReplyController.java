package org.zerock.sb.controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.dto.ReplyDTO;
import org.zerock.sb.service.ReplyService;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;


    @GetMapping("/list/{bno}")
    public PageResponseDTO<ReplyDTO> getReplyList(@PathVariable("bno")Long bno, PageRequestDTO pageRequestDTO){

        return replyService.getListOfBoard(bno, pageRequestDTO);

    }


    @PostMapping("")
    public PageResponseDTO<ReplyDTO> register(@RequestBody ReplyDTO replyDTO){//마지막 페이지를 보여주기위해 pageresponseDTO리턴
        //JSON DATA를 받기위해 RequestBody 사용 필요

        replyService.register(replyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(-1).build();//마지막페이지로 가기위해, page에 -1을넣으면
        //service단에서 if문으로 마지막 페이지 로딩 로직 해줌

        return replyService.getListOfBoard(replyDTO.getBno(), pageRequestDTO);

    }

    @DeleteMapping("/{bno}/{rno}")
    public PageResponseDTO<ReplyDTO> remove(
            @PathVariable("bno")Long bno,
            @PathVariable("rno")Long rno, PageRequestDTO requestDTO){

        return replyService.remove(bno, rno, requestDTO);


    }

    @PutMapping("/{bno}/{rno}")
    public PageResponseDTO<ReplyDTO> modify(
            @PathVariable("bno")Long bno,
            @PathVariable("rno")Long rno,
            @RequestBody ReplyDTO replyDTO,
            PageRequestDTO requestDTO){

        log.info("bno: " + bno);

        log.info("rno: "+ rno);

        log.info("replyDTO" + replyDTO);

        return replyService.modify(replyDTO, requestDTO);
    }

}
