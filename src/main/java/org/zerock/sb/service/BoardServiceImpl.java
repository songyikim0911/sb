package org.zerock.sb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.sb.dto.BoardDTO;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.entity.Board;
import org.zerock.sb.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;


    @Override
    public Long register(BoardDTO boardDTO) {

        //1.DTO->entity
        Board board = modelMapper.map(boardDTO, Board.class);
        //2.repository save( ) - > Long 이제 lastinsertid추출필요없음
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {

        char[] typeArr = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1,//pageable은 페이지가 0부터 시작하기 때문에,,-1필요
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Page<Board> result = boardRepository.search1(typeArr, keyword,pageable);

        List<BoardDTO> dtoList = result.get().map(//get이 stream과 같은역할
                board-> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        long totalCount = result.getTotalElements();

        return new PageResponseDTO<>(pageRequestDTO, (int)totalCount, dtoList);

    }

    @Override
    public BoardDTO read(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);

        if(result.isEmpty()){
            throw new RuntimeException("NOT FOUND");

        }

        return modelMapper.map(result.get(), BoardDTO.class);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        //findbyid로 가져온다음에 업데이트하는게 편함, 시간값등 추가적 정보떄문에
        //그렇게 하는게좋음.

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        if (result.isEmpty()) {
            throw new RuntimeException("NOT FOUND");//존재하지않는다. return보다는 예외선택.

        }
        Board board = result.get();
        board.change(boardDTO.getTitle(), boardDTO.getContent());////게시판에서 수정할 내용은 제목,내용 밖에 없고. 한번에 이루어짐., 변경후..
      //바뀐내용은 DTO에 있으므로 DTO에서 값 가져와서 수정하는 구문 만들어줘야함.
        boardRepository.save(board);//세이브해주면됨!


    }

    @Override
    public void remove(Long bno) {

        boardRepository.deleteById(bno);
    }


}
