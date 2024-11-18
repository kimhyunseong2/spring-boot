package du.ac.kr.sb1029.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Notice")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardIdx;

    private String title;

    private String contents;

    @ColumnDefault("0") //default 0
    private Integer hitCnt;

    private String creatorId;

    private String createdDatetime;

    private String updaterId;

    private Date updatedDatetime;

    @Column(columnDefinition = "varchar(2) default 'N'")
    private String deletedYn;


}
