package du.ac.kr.sb1101.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_board")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardIdx;

    private String title;

    private String contents;

    private Integer hitCnt;


    private String creatorId;

    private Date createdDatetime;

    private String updaterId;

    private Date updatedDatetime;

    private String deletedYn;


}
