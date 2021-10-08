package org.zerock.sb.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity//처음에 엔티티를 만들면 오류가 남 -> id가 없어서 그렇다. entity는 무조건 id를줘야함.
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter//엔티티는 가능하면 불변, 게터
@ToString
public class Board {//우선 테이블 이름 지정안함 -> Board로 테이블 생성될 예정

    @Id//엔티티 id를 기준으로 모든것을 식별하기 때문에 id는 수정되면안된다.
    @GeneratedValue(strategy =  GenerationType.IDENTITY)//키생성 전략 선택  자신없으면 identity
    private Long bno;//ID 만들고나면 오류 사라짐!

    private String title;

    private String content;

    private String writer;

    @CreationTimestamp//hibernate의 어노테이션임,  자동으로 등록시간 관리
    private LocalDateTime regDate;

    @UpdateTimestamp//자동으로 수정시간
    private LocalDateTime modDate;

    public void change(String title, String content){//수정 할 때 쓰는 메서드,
        this.title =title;
        this.content = content;
    }



}
