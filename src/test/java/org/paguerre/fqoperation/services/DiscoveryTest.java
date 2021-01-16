package org.paguerre.fqoperation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.paguerre.fqoperation.models.RebelSatellite;
import org.paguerre.fqoperation.models.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DiscoveryTest {

	@Autowired
	Discovery discovery;

	static Satellite rb1, rb2, rb3;

	@BeforeAll
	public static void init() {
		// "existent" means as written in ".pdf" in order to ensure that
		// distance calculation works:
		rb1 = new RebelSatellite("kenoby", new double[] { -500.0, -200.0 }, 100.0);
		rb2 = new RebelSatellite("skywalker", new double[] { 100.0, -100.0 }, 115.5);
		rb3 = new RebelSatellite("sato", new double[] { 500, 100 }, 142.7);
	}

	@Test
	public void getLocationWithCoordinatesOneRebelSatellite() {
		double[][] coordinatesOfExistentSatellites = new double[][] { rb1.getCoordinates() };
		double[] distancesFromExitentSatellitesToSpaseshipSrc = new double[] { rb1.getSourceDistance() };
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> discovery.getLocation(coordinatesOfExistentSatellites,
						distancesFromExitentSatellitesToSpaseshipSrc),
				"Expected getLocation() to throw IllegalArgumentException -> 'Need at least two positions', but it didn't");
		assertEquals("Need at least two positions.", thrown.getMessage());
	}

	@Test
	public void getLocationWithCoordinatesTwoRebelSatellites() {
		double[][] coordinatesOfExistentSatellites = new double[][] { rb1.getCoordinates(), rb2.getCoordinates() };
		double[] distancesFromExitentSatellitesToSpaseshipSrc = new double[] { rb1.getSourceDistance(),
				rb2.getSourceDistance() };
		double[] expectedCoordinate = new double[] { -215.02609047716473, -152.50354034129128 };
		double[] calculatedCoordinate = discovery.getLocation(coordinatesOfExistentSatellites,
				distancesFromExitentSatellitesToSpaseshipSrc);
		assertEquals(2, calculatedCoordinate.length);
		for (int i = 0; i < calculatedCoordinate.length; i++) {
			assertEquals(expectedCoordinate[i], calculatedCoordinate[i]);
		}
	}

	@Test
	public void getLocationWithCoordinatesFromFQOperation() {
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
