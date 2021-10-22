package org.zerock.sb.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sb.entity.Diary;
import org.zerock.sb.entity.QDiary;
import org.zerock.sb.entity.QDiaryPicture;
import org.zerock.sb.entity.QFavorite;

import java.util.List;

@Log4j2
public class DiarySearchImpl extends QuerydslRepositorySupport implements DiarySearch{

    public DiarySearchImpl() {
        super(Diary.class);
    }

    @Override
    public Page<Object[]> getListSearchList(Pageable pageable) {

        log.info("getSearchList.......................");

        QDiary qDiary = QDiary.diary;
        QFavorite qFavorite = QFavorite.favorite;
        QDiaryPicture qDiaryPicture = new QDiaryPicture("pic");



        JPQLQuery<Diary> query = from(qDiary);
        query.leftJoin(qDiary.tags); //tag도 조인처리..
        query.leftJoin(qDiary.pictures, qDiaryPicture);
        query.leftJoin(qFavorite).on(qFavorite.diary.eq(qDiary)); //이상태에서 그룹바이 걸어줘야 함
        query.groupBy(qDiary);

        query.select(qDiary.dno,qDiary.title,qDiaryPicture, qDiary.tags.any(), qFavorite.score.countDistinct()); //n+1의 문제를 해결할 수 있는 방법중에 하나이다.

        getQuerydsl().applyPagination(pageable, query); //페이징처리해주는 쿼리

        log.info("query: "+ query  );

        List<Diary> diaryList = query.fetch();

        return null;
    }
}
