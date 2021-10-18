package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb.dto.DiaryDTO;
import org.zerock.sb.entity.Diary;
import org.zerock.sb.entity.DiaryPicture;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class DiaryRepositoryTests {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void testInsert(){

        IntStream.rangeClosed(1,100).forEach( i -> {

            Set<String> tags = IntStream.rangeClosed(1,3).mapToObj(j -> i+"_tag"+j).collect(Collectors.toSet());

            Set<DiaryPicture> pictures = IntStream.rangeClosed(1, 3).mapToObj(j ->{
                DiaryPicture picture = DiaryPicture.builder()
                        .uuid(UUID.randomUUID().toString())
                        .savePath("20021/10/18")
                        .fileName("img"+j+".jpg")
                        .idx(j)
                        .build();
                return picture;
            }).collect(Collectors.toSet());

            Diary diary = Diary.builder()
                    .title("sample..."+i)
                    .content("sampl....."+i)
                    .writer("user00")
                    .tags(tags)
                    .pictures(pictures)
                    .build();

            diaryRepository.save(diary);

        });

}


@Test
    public void testSelectOne(){

        Long dno = 1L;

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

       log.info(diary);

       log.info(diary.getTags());

       log.info(diary.getPictures());

}

@Transactional
@Test
    public void testPaging1(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("dno").descending());

        Page<Diary> result = diaryRepository.findAll(pageable);

        result.get().forEach(diary ->{
            log.info(diary);
            log.info(diary.getTags());
            log.info(diary.getPictures());
            log.info("---------");
        });


}


    @Test
    public void testSelectOne2(){

        Long dno = 1L;

        Optional<Diary> optionalDiary = diaryRepository.findById(dno);

        Diary diary = optionalDiary.orElseThrow();

        DiaryDTO dto = modelMapper.map(diary,DiaryDTO.class);

        log.info(dto);

    }

}
