package org.zerock.sb.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replyer;

    //이제 외래키인 BNO를 거는데, 여기에서는 객체-객체간의 관계를 건다. 다르다!
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;//에러의 이유? JPA이기떄문에, 보드와의 관계를 서술해줘야한다.! ->@ManyToOne을 걸면 에러 사라짐

    @CreationTimestamp
    private LocalDateTime replyDate;


}
