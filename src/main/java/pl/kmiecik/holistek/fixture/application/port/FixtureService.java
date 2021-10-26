package pl.kmiecik.holistek.fixture.application.port;

import pl.kmiecik.holistek.fixture.domain.*;

import java.util.List;
import java.util.Optional;

public interface FixtureService {

    List<Fixture> findAllFixtures();

    void saveFixture(Fixture fixture, FixtureHistory fixtureHistory);

    Fixture setStrainStatus(String id, Status ok);

    void sendEmail(Optional<Fixture> myFixture);

    FixtureHistory getFixtureHistory(Fixture fixture, String descriptionOfChange, ModificationReason modificationReason);

    void deleteFixture(Long id);

    void setMyDefaultStrainStatus(Fixture fixture);

    void setMyExpiredStrainDate(Fixture fixture);

    Optional<Fixture> findFixtureById(Long valueOf);


}
