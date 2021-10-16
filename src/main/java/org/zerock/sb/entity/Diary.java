package org.zerock.sb.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity//처음에 엔티티를 만들면 오류가 남 -> id가 없어서 그렇다. entity는 무조건 id를줘야함.
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter//엔티티는 가능하면 불변, 게터
@ToString
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

    @ElementCollection
    private List<String> tags;
//보통은 set을쓰지만,list가 중복적인 데이터를 발생시킬수있어서 list로

}
