package pl.kmiecik.holistek.fixture.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kmiecik.holistek.fixture.domain.Fixture;
import pl.kmiecik.holistek.fixture.domain.FixtureHistory;

@Repository
public interface FixtureHistoryRepository extends JpaRepository<FixtureHistory, Long> {
}
