package pl.kmiecik.holistek.fis.application.port;

import pl.kmiecik.holistek.fixture.domain.Fixture;

public interface FisService {


    void sendAndReceiveIPMessage(String msg, String ip, Integer port);

    String createADDVARIANT(String stationNetName, String variant, String status);

    String createADDFIXTURE(String processName, String fixture, String status);

    void sendFixtureStatusToFis(Fixture fixture);
}
