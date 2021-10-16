package org.zerock.sb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.dto.ReplyDTO;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.Reply;
import org.zerock.sb.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ModelMapper modelMapper;
    private final ReplyRepository replyRepository;


    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = null;

        //pageRequestDTO안의 page값이 -1이라면, 우리가 test에서 만든 로직을 사용하여 값을 변경해줘야함.
        if(pageRequestDTO.getPage() == -1){
            int lastPage = calcLastPage(bno, pageRequestDTO.getSize()); // -1 : 댓글이 없는경우, 숫자 마지막 댓글 페이지
            if(lastPage <= 0){
                lastPage = 1;
            }
            pageRequestDTO.setPage(lastPage);
        }

        pageable =  PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize());


        Page<Reply> result = replyRepository.getListByBno(bno,pageable);

        List<ReplyDTO> dtoList = result.get().map(reply->
                modelMapper.map(reply,ReplyDTO.class))
                .collect(Collectors.toList());

      //  dtoList.forEach(replyDTO->log.info(replyDTO));

        return new PageResponseDTO<>( pageRequestDTO, (int)result.getTotalElements(),dtoList);
    }

    @Override
    public Long register(ReplyDTO replyDTO) {

        //Board board = Board.builder().bno(replyDTO.getBno()).build();//id값을통해 board찾기, reply안에 board객체필요

        Reply reply = modelMapper.map(replyDTO, Reply.class);//소스코드는 replyDTO안에 있고 우리는 REPLY타입으로 이것을 바꿔줘야함

        replyRepository.save(reply);

        return reply.getRno();
    }

    @Override
    public PageResponseDTO<ReplyDTO> remove(Long bno, Long rno,PageRequestDTO pageRequestDTO) {
        replyRepository.deleteById(rno);

        return getListOfBoard(bno, pageRequestDTO);//우리가 만들어놓은 메소드를 활용하여 값 리턴 가능.

    }

    @Override
    public PageResponseDTO<ReplyDTO> modify(ReplyDTO replyDTO, PageRequestDTO pageRequestDTO) {

        //우선 원본 가져오기, findById
        Reply reply = replyRepository.findById(replyDTO.getRno()).orElseThrow();

        reply.setText(replyDTO.getReplyText());

        replyRepository.save(reply);

        return getListOfBoard(replyDTO.getBno(), pageRequestDTO);//목록 리턴하기

    }

    private int calcLastPage(Long bno, double size) {//마지막 페이지 계산 메서드

        int count = replyRepository.getReplyCountOfBoard(bno);
        int lastPage = (int)(Math.ceil(count/size));

        return lastPage;
    }
}
