package org.zerock.sb.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.QBoard;
import org.zerock.sb.entity.QReply;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword,Pageable pageable) {

        QBoard qBoard = QBoard.board;//qBoard는 쿼리가 되는것임.
        QReply qReply = QReply.reply;//Qreply는 reply의 쿼리타입이다.
        //쿼리를 만들때는 Q도메인 이용 <-> 값을 뽑을때는 entity 값 타입을 이용한다.
        //Q도메인을 이용해서 쿼리를 만드는것 중요!
        //Q도메인은 쿼리를 만들긷위한 객체이다. 즉, from(qBoard)
        JPQLQuery<Board> query = from(qBoard);//? qBoard의 있는 jpql쿼리를 반환해서,, jqplquery로 만드는건가..
        query.leftJoin(qReply).on(qReply.board.eq(qBoard));
        query.groupBy(qBoard);

//검색조건이 있다면
        if(typeArr != null && typeArr.length > 0){//검색 조건이 없을 경우, condition을 만들어서
            //jpqa에 keyword검색을 추가 할 필요가 없어진다.
            //따라서 검색조건이 있다면, 아래의 조건문을 실행하도록 하면 된다.
            BooleanBuilder condition = new BooleanBuilder();

            for(char type:typeArr){
                if(type == 'T'){//쿼리문 변경하기
                    condition.or(qBoard.title.contains(keyword));//이렇게 원래 코드에 살을 붙이는것!
                } else if(type =='C'){
                    condition.or(qBoard.content.contains(keyword));
                } else if (type == 'W') {
                    condition.or(qBoard.writer.contains(keyword));
                }
            }
            // char type ='C';//ex) type = 'C'; 제목으로 검색한다면,
            query.where(condition);

        }
        query.where(qBoard.bno.gt(0L));//bno > 0

//        query.select(qBoard.bno, qBoard.title, qBoard.writer,
//                qBoard.regDate, qReply.count());->이렇게하면, reply도 필요하니까 한계가 있어서, 아래 구문작성
        //원하는것 뽑기 위하여 tuple을 활용함
        JPQLQuery<Tuple> selectQuery = query.select(qBoard.bno, qBoard.title, qBoard.writer,
                qBoard.regDate, qReply.count());

        this.getQuerydsl().applyPagination(pageable,selectQuery);


        List<Tuple> tupleList = selectQuery.fetch();//문제 이 쿼리문을 수많은 댓글이 있는게시물에서 실행할경우
        //부하와 비효율, limit가 필요하다.
        long totalCount = selectQuery.fetchCount();
        //이제 페이지 타입 만들어야 하는데 pageimpl은 인터페이스이므로 주의해주기,

        log.info(selectQuery);

        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());


        return new PageImpl<>(arr,pageable,totalCount);
    }
}
