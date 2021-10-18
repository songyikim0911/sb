package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.entity.Diary;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DiaryRepositoryTests {

    @Autowired
    DiaryRepository diaryRepository;

    @Test
    public void testInsert(){

        IntStream.rangeClosed(1,100).forEach( i -> {
            Set<String> tags = IntStream.rangeClosed(1,3).mapToObj(j -> i+"_tag"+j).collect(Collectors.toSet());
            Diary diary = Diary.builder()
                    .title("sample..."+i)
                    .content("sampl....."+i)
                    .writer("user00")
                    .tags(tags)
                    .build();

            diaryRepository.save(diary);

        });

}

}
