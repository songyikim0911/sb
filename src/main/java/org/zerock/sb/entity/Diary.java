package org.zerock.sb.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity//처음에 엔티티를 만들면 오류가 남 -> id가 없어서 그렇다. entity는 무조건 id를줘야함.
@Table(name="tbl_diary")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter//엔티티는 가능하면 불변, 게터
@ToString(exclude ={"tags", "pictures"})
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dno;

    private String title;

    private String content;

    private String writer;

    @CreationTimestamp//hibernate의 어노테이션임,  자동으로 등록시간 관리
    private LocalDateTime regDate;

    @UpdateTimestamp//자동으로 수정시간
    private LocalDateTime modDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="tbl_diary_tag")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
    @Builder.Default
    private Set<String> tags = new HashSet<>();
//보통은 set을쓰지만,list가 중복적인 데이터를 발생시킬수있어서 list로
//collection을 넣어놓으면 list나 set 들어갈 수 있고
    //일반적으로 연관관계에서는 list보다는 set에 많이 들어간다.

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="tbl_diary_picture")
    @Fetch(value = FetchMode.JOIN)
    @BatchSize(size = 50)
   private Set<DiaryPicture> pictures;

}
