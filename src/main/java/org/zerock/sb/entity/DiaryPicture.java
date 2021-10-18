package org.zerock.sb.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable//얘가 걸리는애들만 ElementCollection이 될 수 있다. 이걸 걸고나면 ElementCollection 처리가 가능해짐
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DiaryPicture implements Comparable<DiaryPicture>{

    private String uuid;

    private String fileName;

    private String savePath;

    private int idx;

    @Override
    public int compareTo(DiaryPicture o) {
        return this.idx - o.idx;//정렬
    }
}
