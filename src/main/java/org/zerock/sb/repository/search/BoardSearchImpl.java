package org.zerock.sb.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.QBoard;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);//동적 쿼리를 쓰고 싶은 엔티티를 지정
    }

    @Override
    public Page<Board> search1(char[] typeArr, String keyword, Pageable pageable) {
        log.info("-------search1");
        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board);

//        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
//        String keyword="10";//키워드 가정
//        char[] typeArr = {'T', 'C', 'W'};

        if(typeArr != null && typeArr.length > 0){//검색 조건이 없을 경우, condition을 만들어서
            //jpqa에 keyword검색을 추가 할 필요가 없어진다.
            //따라서 검색조건이 있다면, 아래의 조건문을 실행하도록 하면 된다.
            BooleanBuilder condition = new BooleanBuilder();

            for(char type:typeArr){
                if(type == 'T'){//쿼리문 변경하기
                    condition.or(board.title.contains(keyword));//이렇게 원래 코드에 살을 붙이는것!
                } else if(type =='C'){
                    condition.or(board.content.contains(keyword));
                } else if (type == 'W') {
                    condition.or(board.writer.contains(keyword));
                }
            }
            // char type ='C';//ex) type = 'C'; 제목으로 검색한다면,
            jpqlQuery.where(condition);

        }


        jpqlQuery.where(board.bno.gt(0L));//bno > 0

        JPQLQuery<Board> pagingQuery =
                this.getQuerydsl().applyPagination(pageable, jpqlQuery);


        List<Board> boardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();//이걸 만드면 이것이 카운트 쿼리를 날려줌

       // jpqlQuery.fetch();//가장 정석적인방식..


        return new PageImpl<>(boardList, pageable, totalCount);

    }
}
