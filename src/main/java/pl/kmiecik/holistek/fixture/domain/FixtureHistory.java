package pl.kmiecik.holistek.fixture.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="fixture_histories")
public class FixtureHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="modification_date_time")
    private LocalDateTime modificationDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name ="modification_reason" )
    private ModificationReason modificationReason;

    @Column(name="description")
    private String descriptionOfChange;

    @Column(name="change_owner")
    private String changeOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fixture_id")
    private Fixture fixture;

}
