package org.paguerre.fqoperation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.paguerre.fqoperation.models.RebelSatellite;
import org.paguerre.fqoperation.models.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiscoveryTest {

	@Autowired
	private Discovery discovery;

	@Test
	public void getLocationWithCoordinatesFromFQOperation() {
		Satellite rb1 = new RebelSatellite("kenoby", new double[] { -500.0, -200.0 }, 100.0);
		Satellite rb2 = new RebelSatellite("skywalker", new double[] { 100.0, -100.0 }, 115.5);
		Satellite rb3 = new RebelSatellite("sato", new double[] { 500, 100 }, 142.7);
		// "existent" means as written in ".pdf" in order to ensure that
		// distance calculation works:
		double[][] coordinatesOfExistentSatellites = new double[][] { rb1.getCoordinates(), rb2.getCoordinates(),
				rb3.getCoordinates() };
		double[] distancesFromExitentSatellitesToSpaseshipSrc = new double[] { rb1.getSourceDistance(),
				rb2.getSourceDistance(), rb3.getSourceDistance() };
		// source spaceship coordinate
		double[] expectedCoordinate = new double[] { -58.315252587138595, -69.55141837312165 };
		double[] calculatedCoordinate = discovery.getLocation(coordinatesOfExistentSatellites,
				distancesFromExitentSatellitesToSpaseshipSrc);
		assertEquals(2, calculatedCoordinate.length);
		for (int i = 0; i < calculatedCoordinate.length; i++) {
			assertEquals(expectedCoordinate[i], calculatedCoordinate[i]);
		}
	}
}
