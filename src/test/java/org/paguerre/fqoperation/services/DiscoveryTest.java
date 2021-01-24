package org.paguerre.fqoperation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.paguerre.fqoperation.models.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Discovery.class )
public class DiscoveryTest {

	@Autowired
	Discovery discovery;

	static Satellite rb1, rb2, rb3;

	@BeforeAll
	public static void init() {
		// "existent" means as written in ".pdf" in order to ensure that
		// distance calculation works:
		rb1 = new Satellite("kenoby", new double[] { -500.0, -200.0 }, 100.0);
		rb2 = new Satellite("skywalker", new double[] { 100.0, -100.0 }, 115.5);
		rb3 = new Satellite("sato", new double[] { 500, 100 }, 142.7);
	}

	@Test
	public void getLocationWithCoordinatesOneRebelSatellite() {
		double[][] coordinatesOfExistentSatellites = new double[][] { rb1.getPosition() };
		double[] distancesFromExitentSatellitesToSpaseshipSrc = new double[] { rb1.getDistance() };
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> discovery.getLocation(coordinatesOfExistentSatellites,
						distancesFromExitentSatellitesToSpaseshipSrc),
				"Expected getLocation() to throw IllegalArgumentException -> 'Need at least two positions', but it didn't");
		assertEquals("Need at least two positions.", thrown.getMessage());
	}

	@Test
	public void getLocationWithCoordinatesTwoRebelSatellites() {
		double[][] coordinatesOfExistentSatellites = new double[][] { rb1.getPosition(), rb2.getPosition() };
		double[] distancesFromExitentSatellitesToSpaseshipSrc = new double[] { rb1.getDistance(), rb2.getDistance() };
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
		double[][] coordinatesOfExistentSatellites = new double[][] { rb1.getPosition(), rb2.getPosition(),
				rb3.getPosition() };
		double[] distancesFromExitentSatellitesToSpaseshipSrc = new double[] { rb1.getDistance(), rb2.getDistance(),
				rb3.getDistance() };
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
