package org.zerock.sb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sb.entity.Board;
import org.zerock.sb.repository.search.BoardSearch;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {


    //이 코드의 문제는...검색이안된다는것..!
    //count는 집합 함수이므로 group by 로 집합을 묶어줘야함.
    //게시글 가져오면서 reply count도 가져올수있는데, groupby도 시켜줘야함.
    @Query("select b.bno, b.title, b.writer, b.regDate, count(r) from Board b left join Reply r on r.board = b group by b")//left outerjoin, 댓글갯수를 가져와야하기떄문!
    Page<Object[]> ex1(Pageable pageable);//Page<Object[]>는, 배열안에 배열이 있기때문, b와 r이 배열로 들어가있어야한다.
    //inner join을 쓸 수 없고, 없는쪽을 채워서 써야하는 outer join을 써야함,
    //board를 기준으로 모든것을 압축시켜야함. 그래서 groupby가 필요함.

//    @Query("select b, count(r) from Board b left join Reply r on r.board = b group by b")
//    Page<List> ex1(Pageable pageable);

}
