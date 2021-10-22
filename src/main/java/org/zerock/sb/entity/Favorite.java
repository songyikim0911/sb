package org.zerock.sb.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity//처음에 엔티티를 만들면 오류가 남 -> id가 없어서 그렇다. entity는 무조건 id를줘야함.
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter//엔티티는 가능하면 불변, 게터
@ToString(exclude={"diary","member"})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long fno;

    @ManyToOne(fetch=FetchType.LAZY)
    private Diary diary;

    @ManyToOne(fetch=FetchType.LAZY)
    private Member member;

    private int score;

    @CreationTimestamp
    private LocalDateTime regDate;


}
